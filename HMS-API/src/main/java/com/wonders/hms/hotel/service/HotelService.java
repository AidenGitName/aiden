package com.wonders.hms.hotel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.hotel.type.HotelSortTypeKind;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.hotel.vo.HotelSearchRS;
import com.wonders.hms.util.AES256Cipher;
import com.wonders.hms.util.MD5;
import com.wonders.hms.wonder.vo.WonderHotel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class HotelService {
    private static final Integer HOTEL_RESULT_EXPIRE_SECOND = 60 * 30;

    @Autowired
    private RedisTemplate<String, Object> hotelSearchRedisTemplate;

    @Autowired
    private TotalHotelSearcher totalHotelSearch;

    @Autowired
    private ObjectMapper objectMapper;

    public HotelSearchRS search(HotelSearch hotelSearch) throws Exception {
        String searchResponseJson = getHotelSearchResponseInCache(hotelSearch, false);

        HotelSearchRS hotelSearchRS = null;
        if (searchResponseJson != null) {
            hotelSearchRS = cacheHitDataMappingAndUuidReset(searchResponseJson);
        }

        if (hotelSearchRS == null) {

            List<WonderHotel> searchedCommonHotels = totalHotelSearch.search(hotelSearch);

            searchedCommonHotels = totalHotelSearch.filter(searchedCommonHotels, hotelSearch.getHotelSearchFilter());

            if (hotelSearch.getHotelSortTypeKind().equals(HotelSortTypeKind.PRICE_DESC.toString())) {
                searchedCommonHotels = totalHotelSearch.sortWithRealPrice(searchedCommonHotels, true);
            }
            else if (hotelSearch.getHotelSortTypeKind().equals(HotelSortTypeKind.PRICE_ASC.toString())) {
                searchedCommonHotels = totalHotelSearch.sortWithRealPrice(searchedCommonHotels, false);
            }

            hotelSearchRS = setHotelSearchRS(searchedCommonHotels, hotelSearch);

            setHotelSearchResponseInCache(hotelSearch, hotelSearchRS, false);
        }

        return hotelSearchRS;
    }

    public WonderHotel search(Long hotelId, HotelSearch hotelSearch) throws Exception {
        String searchResponseJson = getHotelSearchResponseInCache(hotelSearch, false);

        WonderHotel wonderHotel = null;
        if (searchResponseJson != null) {
            wonderHotel = this.objectMapper.readValue(searchResponseJson, WonderHotel.class);

            wonderHotel.getRooms().forEach(wonderRoom -> wonderRoom.resetUuid());
        }

        if (wonderHotel == null) {
            wonderHotel = totalHotelSearch.search(hotelId, hotelSearch);
            setHotelSearchResponseInCache(hotelSearch, wonderHotel, false);
        }

        return wonderHotel;
    }

    public HotelSearchRS searchSpecialPrice(HotelSearch hotelSearch) throws Exception {

        String searchResponseJson = getHotelSearchResponseInCache(hotelSearch, true);

        HotelSearchRS hotelSearchRS = null;
        if (searchResponseJson != null) {
            hotelSearchRS = cacheHitDataMappingAndUuidReset(searchResponseJson);
        }

        if (hotelSearchRS == null) {

            List<WonderHotel> searchedCommonHotels = totalHotelSearch.searchSpecial(hotelSearch);

            searchedCommonHotels = totalHotelSearch.filter(searchedCommonHotels, hotelSearch.getHotelSearchFilter());

            if (hotelSearch.getHotelSortTypeKind().equals(HotelSortTypeKind.PRICE_DESC)) {
                searchedCommonHotels = totalHotelSearch.sortWithRealPrice(searchedCommonHotels, true);
            }
            else if (hotelSearch.getHotelSortTypeKind().equals(HotelSortTypeKind.PRICE_ASC)) {
                searchedCommonHotels = totalHotelSearch.sortWithRealPrice(searchedCommonHotels, false);
            }

            hotelSearchRS = setHotelSearchRS(searchedCommonHotels, hotelSearch);

            setHotelSearchResponseInCache(hotelSearch, hotelSearchRS, true);
        }


        return hotelSearchRS;
    }

    public WonderHotel searchSpecialPrice(Long hotelId, HotelSearch hotelSearch) throws Exception {

        String searchResponseJson = getHotelSearchResponseInCache(hotelSearch, true);

        WonderHotel wonderHotel = null;

        if (searchResponseJson != null) {
            wonderHotel = this.objectMapper.readValue(searchResponseJson, WonderHotel.class);

            wonderHotel.getRooms().forEach(wonderRoom -> wonderRoom.resetUuid());
        }

        if (wonderHotel == null) {
            wonderHotel = totalHotelSearch.searchSpecial(hotelId, hotelSearch);
            setHotelSearchResponseInCache(hotelSearch, wonderHotel, true);
        }

        return wonderHotel;
    }

    private String getHotelSearchResponseInCache(HotelSearch hotelSearch, boolean isSpecial) {
        String searchResponseJson = null;
        try {
            HashOperations<String, String, String> hashOperations = hotelSearchRedisTemplate.opsForHash();

            String redisHashOpsKey =
                    isSpecial ? "Special:" : ""
                    + hotelSearch.getPlace().getCountryCode()
                    + ":"
                    + hotelSearch.getPlace().getCityName();
            String hashMapKey = MD5.getMD5Str(objectMapper.writeValueAsString(hotelSearch));
            log.debug(hashMapKey);

            Map<String, String> entries = hashOperations.entries(redisHashOpsKey);
            searchResponseJson = entries.get(hashMapKey);

        } catch (RedisConnectionFailureException exception) {
            log.error(exception.toString());
        } catch (IOException exception) {
            log.warn(exception.toString() + ", redis get hotel search");
        }catch (Exception e) {
            log.warn(e.toString() + ", set hotel search response in cache");
        }

        return searchResponseJson;
    }

    private void setHotelSearchResponseInCache(HotelSearch hotelSearch, Object value, boolean isSpecial) {
        try {
            HashOperations<String, String, String> hashOperations = hotelSearchRedisTemplate.opsForHash();

            String redisHashOpsKey =
                    isSpecial ? "Special:" : ""
                            + hotelSearch.getPlace().getCountryCode()
                            + ":"
                            + hotelSearch.getPlace().getCityName();
            String hashMapKey = MD5.getMD5Str(objectMapper.writeValueAsString(hotelSearch));

            hashOperations.put(redisHashOpsKey, hashMapKey, this.objectMapper.writeValueAsString(value));
            hotelSearchRedisTemplate.expire(redisHashOpsKey, this.HOTEL_RESULT_EXPIRE_SECOND, TimeUnit.SECONDS);
        }catch (RedisConnectionFailureException exception) {
        }catch (IOException exception) {
            log.error(exception.toString());
        }catch (Exception e) {
            log.warn(e.toString() + ", set hotel search response in cache");
        }

    }

    private HotelSearchRS setHotelSearchRS(List<WonderHotel> searchedCommonHotels, HotelSearch hotelSearch) {
        HotelSearchRS hotelSearchRS = new HotelSearchRS();

        hotelSearchRS.setSearchedCommonHotels(searchedCommonHotels);
        hotelSearchRS.setTotalCount(totalHotelSearch.getTotalCount(hotelSearch));

        return hotelSearchRS;
    }

    private HotelSearchRS cacheHitDataMappingAndUuidReset(String searchResponseJson) throws IOException {
        HotelSearchRS hotelSearchRS = this.objectMapper.readValue(searchResponseJson, HotelSearchRS.class);

        hotelSearchRS.getSearchedCommonHotels().forEach(searchedCommonHotel -> {
            searchedCommonHotel.getRooms().forEach(wonderRoom -> {
                wonderRoom.resetUuid();
            });
        });

        return hotelSearchRS;
    }
}
