package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockPolicy {

    private String content;

    @JsonProperty("mealplan_vector")
    private int mealplanVector;

    @JsonProperty("class")
    private String blockPolicyClass;
}
