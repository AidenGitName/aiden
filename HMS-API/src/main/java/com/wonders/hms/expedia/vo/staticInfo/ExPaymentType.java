package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExPaymentType {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;
}