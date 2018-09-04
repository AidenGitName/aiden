package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MinPrice {
    @JsonProperty("net_price")
    private BigDecimal netPrice;

    @JsonProperty("tax_price")
    private BigDecimal texPrice;

    @JsonProperty("extra_charges_breakdown")
    private ExtraChargesBreakdown extraChargesBreakdown;

    @JsonProperty("other_currency")
    private OtherCurrency otherCurrency;

    @JsonProperty("net_price_other_currency")
    private NetPriceOtherCurrency netPriceOtherCurrency;

    private BigDecimal price;

    private String currency;
}
