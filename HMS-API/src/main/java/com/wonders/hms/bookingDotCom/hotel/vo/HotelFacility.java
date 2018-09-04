package com.wonders.hms.bookingDotCom.hotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class HotelFacility {
    private String name;

    private ArrayList<String> attrs;

    @JsonProperty("hotel_facility_type_id")
    private int hotelFacilityTypeId;
}
