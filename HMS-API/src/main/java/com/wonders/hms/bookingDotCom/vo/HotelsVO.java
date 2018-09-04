package com.wonders.hms.bookingDotCom.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
public class HotelsVO {
    private static final String DEFAULT_LANGUAGE = "ko";
    private static final ArrayList<String> DEFAULT_EXTRAS = new ArrayList<String>(
            Arrays.asList("room_facilities", "hotel_facilities", "room_info", "room_description",
                    "room_photos", "hotel_description", "payment_details", "hotel_info",
                    "hotel_photos", "hotel_policies"));

    @JsonProperty("chain_ids")
    private ArrayList<Long> chainIds;

    @JsonProperty("city_ids")
    private ArrayList<Long> cityIds;

    @JsonProperty("country_ids")
    private ArrayList<String> countryIds;

    @JsonProperty("district_ids")
    private ArrayList<Long> districtIds;

    private ArrayList<String> extras = DEFAULT_EXTRAS;

    @JsonProperty("hotel_facility_type_ids")
    private ArrayList<Integer> hotelFacilityTypeIds;

    @JsonProperty("hotel_ids")
    private ArrayList<Long> hotelIds;

    private String language = DEFAULT_LANGUAGE;

    private Integer offset;

    @JsonProperty("region_ids")
    private ArrayList<Long> regionIds;

    private Integer rows;
}
