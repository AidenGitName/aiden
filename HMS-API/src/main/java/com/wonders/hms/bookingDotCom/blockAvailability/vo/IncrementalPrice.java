package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class IncrementalPrice {
    @JsonProperty("net_price")
    private BigDecimal netPrice;

    @JsonProperty("tax_price")
    private BigDecimal texPrice;

    @JsonProperty("extra_charges_breakdown")
    private ExtraChargesBreakdown extraChargesBreakdown;

    @JsonProperty("other_currency")
    private OtherCurrency otherCurrency;

    private BigDecimal price;

    private String currency;
}
