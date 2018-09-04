package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExCheckin {

    @JsonProperty("begin_time")
    private String beginTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("instructions")
    private String instructions;

    @JsonProperty("special_instructions")
    private String specialInstructions;

    @JsonProperty("min_age")
    private int minAge;
}
