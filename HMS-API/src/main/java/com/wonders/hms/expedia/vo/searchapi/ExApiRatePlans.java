package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExApiRatePlans {

    @JsonProperty("RoomTypeId")
    private String roomTypeId;

    @JsonProperty("RatePlanId")
    private String ratePlanId;

    @JsonProperty("RateRuleId")
    private String rateRuleId;

    @JsonProperty("InventorySourceId")
    private String inventorySourceId;

    @JsonProperty("InventorySourceCode")
    private String inventorySourceCode;

    @JsonProperty("StayDates")
    private ExApiStayDate stayDates;

    @JsonProperty("RemainingCount")
    private int remainingCount;
}
