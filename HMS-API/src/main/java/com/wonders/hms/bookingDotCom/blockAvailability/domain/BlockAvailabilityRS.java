package com.wonders.hms.bookingDotCom.blockAvailability.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.bookingDotCom.blockAvailability.vo.Meta;
import com.wonders.hms.bookingDotCom.blockAvailability.vo.Result;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class BlockAvailabilityRS {
    private Meta meta;

    @JsonProperty("result")
    private ArrayList<Result> results;
}
