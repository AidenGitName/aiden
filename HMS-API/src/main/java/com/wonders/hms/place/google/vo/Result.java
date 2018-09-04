package com.wonders.hms.place.google.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Result {
    private Geometry geometry;
    private String icon;
    private String id;
    private String name;
    private OpeningHours openingHours;
    private List<Photo> photos;

    @JsonProperty("place_id")
    private String placeId;

    private String scope;
    private String reference;
    private List<String> types;
    private String vicinity;
}
