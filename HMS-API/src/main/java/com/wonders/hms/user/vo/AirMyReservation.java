package com.wonders.hms.user.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.hotel.vo.HotelSearch;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class AirMyReservation {
    private Boolean rsvFlag;
    private String mid;

    @JsonProperty("statusInfo")
    private AirStatusInfo airStatusInfo;

    @JsonProperty("rsvData")
    private AirReservation airReservation;

    public boolean isValidSpecialCustom(HotelSearch hotelSearch) {
        //검색 기간은 항공 출발일 ~ -30일
        //머무를 호텔 기간은 항공 출발 ~ 항공 도착, 장소는 항공 도착 장소
        if (rsvFlag == null || !rsvFlag) {
            return false;
        }

        if (hotelSearch.getCheckin().isBefore(airReservation.getDepDtm())) {
            return false;
        }
        if (hotelSearch.getCheckout().isAfter(airReservation.getArrDtm())) {
            return false;
        }

        return true;
    }

    public String getCityCode() {
        if (rsvFlag == null || !rsvFlag) {
            return null;
        }

        return airReservation.getArrCityCd();
    }
}
