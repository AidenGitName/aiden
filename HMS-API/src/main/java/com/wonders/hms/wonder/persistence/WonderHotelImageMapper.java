package com.wonders.hms.wonder.persistence;

import com.wonders.hms.wonder.vo.WonderHotelImage;

import java.util.List;

public interface WonderHotelImageMapper {
    void insertWonderHotelImage(WonderHotelImage wonderHotelImage);

    void deleteWonderHotelImage(Long wonderHotelInfoId);

    List<WonderHotelImage> getWonderHotelImages(Long wonderHotelInfoId);
}
