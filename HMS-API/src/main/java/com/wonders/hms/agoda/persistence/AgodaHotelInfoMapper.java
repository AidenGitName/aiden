package com.wonders.hms.agoda.persistence;

import com.wonders.hms.agoda.vo.AgodaHotel;
import com.wonders.hms.hotel.vo.HotelSearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AgodaHotelInfoMapper {

    List<AgodaHotel> getHotels(HotelSearch hotelSearch);

    List<AgodaHotel> getAllHotels();

    void updateHotel(AgodaHotel agodaHotel);

    void insertHotel(AgodaHotel newAgodaHotel);

    AgodaHotel getHotelByHotelId(@Param("hotelId") int hotelId);
}
