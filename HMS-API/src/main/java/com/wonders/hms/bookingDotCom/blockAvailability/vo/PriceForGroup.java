package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PriceForGroup {
    private String currency;

    @JsonProperty("other_currency")
    private OtherCurrency otherCurrency;

    private BigDecimal price;
}
