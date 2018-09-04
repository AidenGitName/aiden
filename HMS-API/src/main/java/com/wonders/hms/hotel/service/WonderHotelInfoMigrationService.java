package com.wonders.hms.hotel.service;

import com.wonders.hms.agoda.persistence.AgodaHotelInfoMapper;
import com.wonders.hms.agoda.vo.AgodaHotel;
import com.wonders.hms.autocomplete.vo.Result;
import com.wonders.hms.bookingDotCom.cites.vo.CitesRS;
import com.wonders.hms.bookingDotCom.cites.vo.Translation;
import com.wonders.hms.bookingDotCom.hotelAvailability.domain.AutocompleteRS;
import com.wonders.hms.bookingDotCom.service.BookingDotComService;
import com.wonders.hms.bookingDotCom.vo.AutocompleteVO;
import com.wonders.hms.bookingDotCom.vo.CitesVO;
import com.wonders.hms.expedia.vo.searchapi.ExpediaHotelInfo;
import com.wonders.hms.expedia.persistence.ExpediaHotelInfoMapper;
import com.wonders.hms.hotel.domain.HotelVendorIndex;
import com.wonders.hms.hotel.persistence.HotelVendorMapper;
import com.wonders.hms.wonder.persistence.WonderHotelInfoMapper;
import com.wonders.hms.wonder.vo.WonderHotel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WonderHotelInfoMigrationService {
    @Autowired
    AgodaHotelInfoMapper agodaHotelInfoMapper;

    @Autowired
    ExpediaHotelInfoMapper expediaHotelInfoMapper;

    @Autowired
    WonderHotelInfoMapper wonderHotelInfoMapper;

    @Autowired
    HotelVendorMapper hotelVendorMapper;

    @Autowired
    BookingDotComService bookingDotComService;

    private static final String AUTOCOMPLETE_SEARCH_LANGUAGE = "en";


    public void migrationExpediaToBooking() {
        List<ExpediaHotelInfo> expediaHotelInfos = expediaHotelInfoMapper.getAllHotels();
        System.out.println("select all expedia hotel!!!!!");

        expediaHotelInfos.parallelStream().forEach(expediaHotelInfo -> {
            try {
                if (hotelVendorMapper.getCountHotelVendorIndexWithExpedia(expediaHotelInfo.getPropertyId()) == 1) {
                    System.out.println(expediaHotelInfo.getPropertyId() + "exist");
                    return;
                }
                Result matchingResult = searchAutocomplete(expediaHotelInfo.getName(), expediaHotelInfo.getCountry());

                HotelVendorIndex hotelVendorIndex = new HotelVendorIndex();
                hotelVendorIndex.setExpediaId(expediaHotelInfo.getPropertyId());
                if (matchingResult != null) {
                    hotelVendorIndex.setBookingId(Long.parseLong(matchingResult.getId()));
                }

                hotelVendorMapper.insertHotelVendorIndex(hotelVendorIndex);

                WonderHotel wonderHotel = new WonderHotel();
                wonderHotel.setLongitude(expediaHotelInfo.getLongitude());
                wonderHotel.setLatitude(expediaHotelInfo.getLatitude());
                wonderHotel.setHotelVendorIndex(hotelVendorIndex.getId());
                wonderHotel.setName(expediaHotelInfo.getName());
                wonderHotel.setAddress(expediaHotelInfo.getAddress());
                wonderHotel.setRating(expediaHotelInfo.getStarRating());
//                wonderHotel.setImageUrls(expediaHotelInfo.getImages());
                wonderHotel.setCheckin(expediaHotelInfo.getCheckin());
                wonderHotel.setCheckout(expediaHotelInfo.getCheckout());
//                wonderHotelInfo.setStar(expediaHotelInfo.getStar);

                wonderHotelInfoMapper.insertWonderHotel(wonderHotel);

            } catch (Exception e) {
                System.out.println(e);
                System.out.println(expediaHotelInfo.getName() + "," + expediaHotelInfo.getPropertyId());
                System.out.println("----------------------------");
            }

        });
    }

    public Long getBookingId(String hotelName, String countryCode) throws IOException{
        Result matchingResult = searchAutocomplete(hotelName, countryCode);

        if (matchingResult != null) {
            return Long.parseLong(matchingResult.getId());
        }
        return null;
    }

    public void migrationAgodaToBooking() {
        List<AgodaHotel> agodaAllHotels = agodaHotelInfoMapper.getAllHotels();
        System.out.println("select all agoda hotel!!!!!");

        agodaAllHotels.parallelStream().forEach(agodaHotel -> {
            try {
                Result matchingResult = searchAutocomplete(agodaHotel.getHotelName(), agodaHotel.getCountryIsoCode());

                HotelVendorIndex hotelVendorIndex = new HotelVendorIndex();
                hotelVendorIndex.setAgodaId(agodaHotel.getHotelId());
                if (matchingResult != null) {
                    hotelVendorIndex.setBookingId(Long.parseLong(matchingResult.getId()));
                }

                hotelVendorMapper.insertHotelVendorIndex(hotelVendorIndex);

                WonderHotel wonderHotel = new WonderHotel();
                wonderHotel.setLatitude(agodaHotel.getLatitude());
                wonderHotel.setLongitude(agodaHotel.getLongitude());
                wonderHotel.setName(agodaHotel.getHotelName());
                wonderHotel.setHotelVendorIndex(hotelVendorIndex.getId());
                wonderHotel.setAddress(agodaHotel.getAddress());
                wonderHotel.setRating(agodaHotel.getRatingAverage());
                // TODO image column 통합 체크
//                wonderHotel.setImage(agodaHotel.getImageUrls().get(0));
                wonderHotel.setCheckin(agodaHotel.getCheckin());
                wonderHotel.setCheckout(agodaHotel.getCheckout());
                wonderHotel.setStar(agodaHotel.getStarRating());

                wonderHotelInfoMapper.insertWonderHotel(wonderHotel);

            } catch (Exception e) {
                System.out.println(e);
                System.out.println(agodaHotel.getHotelName());
                System.out.println("----------------------------");
            }

        });
    }

    private Result searchAutocomplete(String hotelName, String countryCode) throws IOException {

        AutocompleteRS autocompleteRS = getAutocompleteRS(hotelName);

        Result matchingResult = null;

        for(Result autocompleteResult: autocompleteRS.getResult()) {
            HotelNameComparer hotelNameComparer = new HotelNameComparer();
            if (!autocompleteResult.getType().equals("hotel")) {
                continue;
            }
            if (!autocompleteResult.getCountry().toUpperCase().equals(countryCode)) {
                continue;
            }
            if (
                    !( autocompleteResult.getName().equals(hotelName)  ||
                            hotelNameComparer.isSimilarHotelName(autocompleteResult.getName(), hotelName) )
                    ) {
                continue;
            }

            matchingResult = autocompleteResult;
            break;
        }

        return matchingResult;
    }

    private AutocompleteRS getAutocompleteRS(String searchName) throws IOException {
        AutocompleteVO autocompleteVO = new AutocompleteVO();

        AutocompleteRS autocompleteRS;
        try {

            autocompleteVO.setText(searchName);
            autocompleteVO.setLanguage(AUTOCOMPLETE_SEARCH_LANGUAGE);

            autocompleteRS = bookingDotComService.autocomplete(autocompleteVO);
        }
        catch (HttpClientErrorException h) {

            String encodeQueryString = URLEncoder.encode(searchName, "UTF-8").replaceAll("\\+", "%20");
            autocompleteVO.setText(encodeQueryString);
            autocompleteVO.setLanguage(AUTOCOMPLETE_SEARCH_LANGUAGE);

            autocompleteRS = bookingDotComService.autocomplete(autocompleteVO);
        }

        return autocompleteRS;
    }

    public void cityNameCompareAutoComplete() {
        List<Map> cities = wonderHotelInfoMapper.getAllCityEngAndCountry();

        cities .forEach(city -> {
            try {
                AutocompleteRS autocompleteRS = getAutocompleteRS(city.get("city_eng").toString());

                Long matchCityId = null;

                for(Result autocompleteResult: autocompleteRS.getResult()) {
                    if (
                            autocompleteResult.getType().equals("city")
                                    && autocompleteResult.getName().equals(city.get("city_eng").toString())
                                    && autocompleteResult.getCountry().toUpperCase().equals(city.get("country").toString())
                            ) {
                        matchCityId = Long.valueOf(autocompleteResult.getId());
                        break;
                    }
                }
                if (matchCityId == null) {
                    return;
                }

                CitesVO citesVO = new CitesVO();

                ArrayList<Long> cityIds = new ArrayList<>();
                cityIds.add(matchCityId);
                citesVO.setCityIds(cityIds);

                ArrayList<String> languages = new ArrayList<>();
                languages.add("ko");
                citesVO.setLanguages(languages);

                CitesRS citesRS = bookingDotComService.cities(citesVO);

                if (citesRS.getResult().size() == 0) {
                    return;
                }

                for(Translation translation: citesRS.getResult().get(0).getTranslations()) {
                    if (translation.getLanguage().equals("ko")) {
                        wonderHotelInfoMapper.updateCity(translation.getName(), city.get("city_eng").toString());
                    }
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
        });
    }
}
