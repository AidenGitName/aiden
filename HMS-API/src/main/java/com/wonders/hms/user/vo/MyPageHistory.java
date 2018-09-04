package com.wonders.hms.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyPageHistory {

    private String mid;
    private String checkin;
    private String checkout;
    private String reservationUrl;
    private String hotelName;
    private String hotelAddress;
    private String hotelPhone;
    private String hotelCity;
    private String status;
    private Double hotelLongitude;
    private Double hotelLatitude;
    private String googleMapLink;

    public String getGoogleMapLink() {
        return "https://google.com/maps/search/" + this.hotelLatitude + "," + this.hotelLongitude;
    }
}
