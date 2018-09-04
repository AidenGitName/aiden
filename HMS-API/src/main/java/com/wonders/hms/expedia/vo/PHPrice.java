package com.wonders.hms.expedia.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PHPrice {

    @JsonProperty("USD")
    private int value;
}
