package com.wonders.hms.bookingDotCom.cites.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Result {
    @JsonProperty("city_id")
    private Long cityId;

    @JsonProperty("nr_hotels")
    private Integer nrHotels;

    private Location Location;

    private Timezone timezone;

    private String name;

    private ArrayList<Translation> translations;

    private String country;
}
