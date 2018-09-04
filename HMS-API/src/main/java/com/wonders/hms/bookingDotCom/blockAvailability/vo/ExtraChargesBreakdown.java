package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;

@Getter
@Setter
class ExtraChargesBreakdown {
    @JsonProperty("net_price_other_currency")
    private NetPriceOtherCurrency netPriceOtherCurrency;

    @JsonProperty("extra_charge")
    private ArrayList<ExtraCharge> extraCharges;

    @JsonProperty("net_price")
    private BigDecimal netPrice;
}
