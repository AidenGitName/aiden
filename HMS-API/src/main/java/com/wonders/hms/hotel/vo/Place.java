package com.wonders.hms.hotel.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Place {
    private String placeName;
    private String placeType;
    private Long placeId;

    private String countryName;
    private String countryCode;

    private String cityName;
    private Long cityId;

    private Double latitude;
    private Double longitude;

    public void setCountryCode(String countryCode) {
        if (countryCode != null) {
            this.countryCode = countryCode.toUpperCase();
        }
    }
}
