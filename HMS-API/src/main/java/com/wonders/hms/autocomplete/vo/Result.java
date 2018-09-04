package com.wonders.hms.autocomplete.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Result {

    private String url;

    private String label;

    private ArrayList<Endorsement> endorsements;

    private String timezone;

    @JsonProperty("city_ufi")
    private String cityUfi;

    private Forecast forecast;

    private String region;

    private String language;

    private String name;

    @JsonProperty("country_name")
    private String countryName;

    private String type;

    @JsonProperty("city_name")
    private String cityName;

    private String latitude;

    private String id;

    @JsonProperty("nr_dest")
    private int nrDest;

    @JsonProperty("top_destinations")
    private ArrayList<Integer> topDestinations;

    private String country;

    private String longitude;

    @JsonProperty("nr_hotels")
    private int nrHotels;

    @JsonProperty("right-to-left")
    private String rightToLeft;
}
