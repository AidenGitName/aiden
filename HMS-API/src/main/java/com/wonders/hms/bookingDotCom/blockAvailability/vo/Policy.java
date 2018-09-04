package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Policy {

    private String content;

    private ArrayList<String> items;

    @JsonProperty("class")
    private String policyClass;
}
