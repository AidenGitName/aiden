package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExApiDistance {

    @JsonProperty("Value")
    private String value;

    @JsonProperty("Unit")
    private String unit;

    @JsonProperty("Direction")
    private String direction;
}
