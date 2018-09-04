package com.wonders.hms.wonder.vo;

import java.util.Comparator;

// TODO : 클래스 위치 조정 필요
public class HotelWeightComparator implements Comparator<WonderHotel> {
    @Override
    public int compare(WonderHotel wonderHotel1, WonderHotel wonderHotel2) {
        return wonderHotel1.getHotelWeight().compareTo(wonderHotel2.getHotelWeight());
    }
}
