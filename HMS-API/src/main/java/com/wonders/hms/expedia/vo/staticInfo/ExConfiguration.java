package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExConfiguration {

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("size")
    private String size;

    @JsonProperty("type")
    private String type;
}
