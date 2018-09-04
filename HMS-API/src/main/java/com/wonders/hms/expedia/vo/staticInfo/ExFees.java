package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExFees {

    @JsonProperty("mandatory")
    private String mandatory;

    @JsonProperty("optional")
    private String optional;
}
