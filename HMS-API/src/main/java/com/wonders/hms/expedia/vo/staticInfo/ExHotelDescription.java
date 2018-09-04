package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExHotelDescription {

    @JsonProperty("amenities")
    private String amenity;

    @JsonProperty("dining")
    private String dining;

    @JsonProperty("renovations")
    private String renovation;

    @JsonProperty("business_amenities")
    private String businessAmenity;

    @JsonProperty("rooms")
    private String room;

    @JsonProperty("attractions")
    private String attraction;

    @JsonProperty("location")
    private String location;

    @JsonProperty("national_ratings")
    private String nationalRating;
}
