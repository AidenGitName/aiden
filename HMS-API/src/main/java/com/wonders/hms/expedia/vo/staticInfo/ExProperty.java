package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExProperty {

    @JsonProperty("rating")
    private Double rating;

    @JsonProperty("type")
    private String type;

    @JsonProperty("count")
    private int count;
}
