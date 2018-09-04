package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExApiPrice {

    @JsonProperty("BaseRate")
    private ExApiTotalPrice baseRate;

    @JsonProperty("TaxesAndFees")
    private ExApiTotalPrice taxesAndFees;

    @JsonProperty("TotalPrice")
    private ExApiTotalPrice totalPrice;

    @JsonProperty("AvgNightlyRate")
    private ExApiTotalPrice avgNightlyRate;

    @JsonProperty("HotelMandatoryFees")
    private ExApiTotalPrice hotelMandatoryFees;

    @JsonProperty("TotalPriceWithHotelFees")
    private ExApiTotalPrice totalPriceWithHotelFees;

}
