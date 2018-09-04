package com.wonders.hms.bookingDotCom.hotelAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomPolicy {
    @JsonProperty("class")
    private String roomPolicyClass;
    private String content;
    @JsonProperty("mealplan_vector")
    private int mealplanVector;
}
