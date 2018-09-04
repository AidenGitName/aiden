package com.wonders.hms.bookingDotCom.persistence;

import com.wonders.hms.bookingDotCom.hotel.vo.HotelData;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface BookingHotelInfoMapper {

    HotelData getHotel(Long hotelId);

    void insertHotel(@Param("bookingId") Long bookingId, @Param("hotelData") HotelData hotelData);

    void updateHotel(@Param("bookingId") Long bookingId, @Param("hotelData") HotelData hotelData);

    void deleteHotel(Long bookingId);

    List<HashMap> getHotelImages(@Param("bookingIds") List<Long> bookingIds);
}
