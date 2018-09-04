package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class GroupRecommendationBlock {
    @JsonProperty("extrabed_adult_count")
    private ArrayList<Integer> extrabedAdultCount;

    @JsonProperty("extrabed_child_count")
    private ArrayList<Integer> extrabedChildCount;

    @JsonProperty("extrabed_baby_count")
    private ArrayList<Integer> extrabedBabyCount;

    @JsonProperty("deposit_required")
    private boolean depositRequired;

    private MinPrice price;

    @JsonProperty("baby_count")
    private ArrayList<Integer> babyCount;

    @JsonProperty("adult_count")
    private ArrayList<Integer> adultCount;

    @JsonProperty("child_count")
    private ArrayList<Integer> childCount;

    @JsonProperty("room_id")
    private Long room_id;

    private String name;

    @JsonProperty("refundable_until")
    private String refundableUntil;

    @JsonProperty("extrabed_price")
    private ExtraBedPrice extraBedPrice;

    @JsonProperty("stay_count")
    private int stayCount;

    @JsonProperty("block_id")
    private String blockId;

    @JsonProperty("breakfast_included")
    private boolean breakfastIncluded;
}
