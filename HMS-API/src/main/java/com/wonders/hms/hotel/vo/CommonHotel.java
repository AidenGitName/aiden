package com.wonders.hms.hotel.vo;

import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.type.HotelVendorKind;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

// 호텔 공통 정보
@Slf4j
public abstract class CommonHotel {

    public abstract List<? extends CommonRoom> getRooms();

    public abstract String getName();

    public abstract Long getHotelId();

    public abstract String getAddress();

    public abstract Double getStar();

    public abstract Double getLongitude();

    public abstract Double getLatitude();

    public abstract String getHotelUrl();

    public abstract String getCheckinTime();

    public abstract List<String> getImageUrls();

    public abstract HotelVendorKind getVendor();

    public abstract Double getScore();


}
