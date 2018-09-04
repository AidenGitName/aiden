package com.wonders.hms.autocomplete.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Forecast {

    @JsonProperty("min_temp_c")
    private int minTempC;

    @JsonProperty("max_temp_f")
    private int maxTempF;

    private String icon;

    @JsonProperty("max_temp_c")
    private int maxTempC;

    @JsonProperty("min_temp_f")
    private int minTempF;
}
