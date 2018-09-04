package com.wonders.hms.expedia.vo.searchapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpediaHotelInfo {
    private Long id;
    private Long propertyId;
    private Double longitude;
    private Double latitude;
    private String country;
    private String name;
    private String city;
    private String postalCode;
    private String address;
    private Double starRating;
    private String category;
    private String checkin;
    private String checkout;
    private String images;
    private String phone;
}
