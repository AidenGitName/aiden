package com.wonders.hms.place.google.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearbySearchVO {
    // 10km
    private final static int DEFAULT_RADIUS = 10000;
    private final static String DEFAULT_LANGUAGE = "ko";
    private final static String DEFAULT_TYPE = "point_of_interest";

    private String key;

    private String location;

    private int radius = DEFAULT_RADIUS;

    @JsonProperty("rankby")
    private String rankBy;

    private String keyword;

    private String language = DEFAULT_LANGUAGE;

    @JsonProperty("minprice")
    private Integer minPrice;

    @JsonProperty("maxprice")
    private Integer maxPrice;

    private String name;

    @JsonProperty("opennew")
    private String openNew;

    private String type = DEFAULT_TYPE;

    @JsonProperty("pagetoken")
    private String pageToken;
}
