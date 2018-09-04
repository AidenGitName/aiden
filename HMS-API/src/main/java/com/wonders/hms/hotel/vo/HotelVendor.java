package com.wonders.hms.hotel.vo;

import com.wonders.hms.room.vo.CommonRoom;

import java.util.Collection;
import java.util.List;

public interface HotelVendor {

    @Deprecated
    Collection<? extends CommonHotel> getHotels (HotelSearch hotelSearch) throws Exception;

    List<CommonRoom> getRooms(List<Long> vendorIndex, HotelSearch hotelSearch) throws Exception;

    List<CommonRoom> getSpecialPriceRooms(List<Long> vendorIndex, HotelSearch hotelSearch) throws Exception;
}
