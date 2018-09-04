package com.wonders.hms.place.google.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Photo {
    private Integer height;
    private Integer width;

    @JsonProperty("html_attributions")
    private List<String> htmlAttributions;

    @JsonProperty("photo_reference")
    private String photoReference;
}
