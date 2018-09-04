package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RackRate {

    private BigDecimal price;

    @JsonProperty("other_currency")
    private OtherCurrency otherCurrency;

    private String Currency;
}
