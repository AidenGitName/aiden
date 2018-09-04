package com.wonders.hms.room.vo;

import com.wonders.hms.wonder.type.HotelVendorKind;

import java.math.BigDecimal;
import java.util.List;

public interface CommonRoom {

    public Long getHotelId(); // hotel_vendor_index 테이블의 id 컬럼

    public HotelVendorKind getHotelVendorKind();

    public String getName();

    public BigDecimal getTotalPrice();

    @Deprecated
    public String getUrl();

    public String getListUrl();

    public String getDetailUrl();

    public BigDecimal getPricePerNight();

    public String getInformation();

    public String getUuid();

    public String getRoomImageUrl();
}
