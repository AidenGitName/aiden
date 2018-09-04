package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class GroupRecommendation {
    @JsonProperty("block")
    private ArrayList<GroupRecommendationBlock> groupRecommendationBlocks;

    @JsonProperty("hotel_id")
    private Long hotelId;

    @JsonProperty("price_for_group")
    private PriceForGroup priceForGroup;
}
