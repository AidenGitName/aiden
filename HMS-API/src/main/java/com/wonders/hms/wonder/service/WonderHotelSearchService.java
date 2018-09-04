package com.wonders.hms.wonder.service;

import com.wonders.hms.expedia.persistence.ExpediaHotelAmenityMapper;
import com.wonders.hms.expedia.persistence.ExpediaHotelImageMapper;
import com.wonders.hms.expedia.vo.ExpediaHotelAmenity;
import com.wonders.hms.expedia.vo.ExpediaHotelImage;
import com.wonders.hms.hotel.persistence.HotelVendorMapper;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.persistence.WonderHotelInfoMapper;
import com.wonders.hms.wonder.translateMap.HotelAmenityMap;
import com.wonders.hms.wonder.vo.WonderHotel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WonderHotelSearchService {

    @Autowired
    private WonderHotelInfoMapper wonderHotelInfoMapper;

    @Autowired
    private ExpediaHotelImageMapper expediaHotelImageMapper;

    @Autowired
    private HotelVendorMapper hotelVendorMapper;

    @Autowired
    private ExpediaHotelAmenityMapper expediaHotelAmenityMapper;

    @Transactional(readOnly = true)
    public List<WonderHotel> getHotels(HotelSearch hotelSearch) {
        List<WonderHotel> wonderHotels;
        // city로 먼저 검색했으나. 8.10일 위메프 요청으로 위경도 기준으로만 변경
//        if (hotelSearch.getPlace().getCityName() == null) {
//            wonderHotels = wonderHotelInfoMapper.getHotels(hotelSearch);
//        } else {
//            wonderHotels = wonderHotelInfoMapper.getHotelsByCity(hotelSearch);
//        }
//
//        // 도시 이름으로 호텔 정보 검색 시 위경도로만 다시 검색
//        if (wonderHotels.size() == 0) {
//            if (hotelSearch.getPlace().getCityName() == null ) {
//                return wonderHotels;
//            }
//            Place place = new Place();
//            place.setLatitude(hotelSearch.getPlace().getLatitude());
//            place.setLongitude(hotelSearch.getPlace().getLongitude());
//
//            hotelSearch.setPlace(place);
//
//            wonderHotels = wonderHotelInfoMapper.getHotels(hotelSearch);
//
//        }
        if (hotelSearch.getPlace().getPlaceType() != null
                && hotelSearch.getPlace().getPlaceType().equals("country") ) {
            wonderHotels = wonderHotelInfoMapper.getHotelByCountryCode(hotelSearch);
        } else {
            wonderHotels = wonderHotelInfoMapper.getHotels(hotelSearch);
        }

        if (hotelSearch.getPlace().getPlaceType() != null
                && hotelSearch.getPlace().getPlaceType().equals("hotel")
                && hotelSearch.getRow().compareTo(0) == 0 ) {
            Long hotelVendorIndexId = hotelVendorMapper.getHotelVendorIdWithBookingId(hotelSearch.getPlace().getPlaceId());
            Long hotelId = wonderHotelInfoMapper.getHotelIdByHotelVendorIndexId(hotelVendorIndexId);

            if (hotelId != null
                    && !wonderHotels.stream().map(wonderHotel -> wonderHotel.getId()).collect(Collectors.toList()).contains(hotelId)
                    ) {
                wonderHotels.add(0, getHotelByHotelId(hotelId));
            }
        }

        if (!isAnyHotelFound(wonderHotels)) {
            wonderHotels = new ArrayList<>();
            return wonderHotels;
        }

        try {
            findImage(wonderHotels);

            findFacility(wonderHotels);
        } catch (Exception exception) {
            log.warn(exception.toString());
        }

        return wonderHotels;
    }

    private boolean isAnyHotelFound(List<WonderHotel> wonderHotels) {
        if (wonderHotels == null || wonderHotels.size() == 0) {
            return false;
        }
        return true;
    }


    private void findFacility(List<WonderHotel> wonderHotels) {
        List<ExpediaHotelAmenity> amenities = expediaHotelAmenityMapper.getAmenity(
                wonderHotels.stream().map(wonderHotel -> wonderHotel.getId()).collect(Collectors.toSet())
        );

        amenities.forEach(amenity -> {

            Optional<WonderHotel> wonderHotel = wonderHotels.stream()
                    .filter(wonder -> wonder.getId().compareTo(amenity.getPropertyId()) ==0)
                    .findFirst();

            if (wonderHotel.isPresent()) {
                String amenityKoName = HotelAmenityMap.amenityKindToKoName.get(
                        HotelAmenityMap.enNameToAmenityKind.get(amenity.getAmenity())
                );
                wonderHotel.get().getFacilities().add(amenityKoName);
            }
        });
    }

    private void findImage(List<WonderHotel> wonderHotels) {
        List<ExpediaHotelImage> images = expediaHotelImageMapper.getImages(
                wonderHotels.stream().map(wonderHotel -> wonderHotel.getId()).collect(Collectors.toSet())
        );

        images.forEach(image -> {
            Optional<WonderHotel> wonderHotel = wonderHotels.stream()
                    .filter(wonder -> wonder.getId().compareTo(image.getPropertyId()) == 0)
                    .findFirst();

            if (wonderHotel.isPresent()) {
                wonderHotel.get().getImages().add(image.getUrl());
            }
        });
    }

    @Transactional(readOnly = true)
    public WonderHotel getHotelByHotelId(Long hotelId){
        WonderHotel wonderHotel = wonderHotelInfoMapper.getHotelByHotelId(hotelId);

        List<WonderHotel> wonderHotelList = new ArrayList<>();

        wonderHotelList.add(wonderHotel);

        findImage(wonderHotelList);

        findFacility(wonderHotelList);

        return wonderHotelList.get(0);
    }

    public Integer getTotalCount(HotelSearch hotelSearch) {
        if (hotelSearch.getPlace().getPlaceType() != null
                && hotelSearch.getPlace().getPlaceType().equals("country")) {
            return wonderHotelInfoMapper.getTotalHotelCountryByCountryCode(hotelSearch);
        }
        else {
            return wonderHotelInfoMapper.getTotalHotelCount(hotelSearch);
        }
    }

    public List<CommonRoom> getRooms(List<Long> vendorIndex) {
        return null;
    }

}
