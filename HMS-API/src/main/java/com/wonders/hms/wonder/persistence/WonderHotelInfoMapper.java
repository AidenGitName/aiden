package com.wonders.hms.wonder.persistence;

import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.wonder.vo.WonderHotel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WonderHotelInfoMapper {

    List<WonderHotel> getHotels(HotelSearch hotelSearch);

    List<WonderHotel> getHotelByCountryCode(HotelSearch hotelSearch);

    WonderHotel getHotelByHotelId(Long hotelId);

    Long getHotelIdByHotelVendorIndexId(Long hotelVendorIndexId);

    Integer getTotalHotelCount(HotelSearch hotelSearch);

    Integer getTotalHotelCountryByCountryCode(HotelSearch hotelSearch);

    void insertWonderHotel(WonderHotel wonderHotel);

    List<Map> getAllCityEngAndCountry();

    void updateCity(@Param("cityName") String cityName, @Param("cityEng") String cityEng);

    List<WonderHotel> getAllHotel();

    WonderHotel getHotelByExpediaHotelId(@Param("expediaPropertyId") Long propertyId);

    void updateChangedWonderHotel(WonderHotel oldWonderHoel);

    List<WonderHotel> getAllHotelWithVendors();

    void calculateWeight(WonderHotel wonderHotel);

    WonderHotel getHotelByHotelIndexId(Long hotelVendorIndexId);

    WonderHotel getHotelByAgodaHotelId(@Param("agodaHotelId") Long agodaHotelId);
}
