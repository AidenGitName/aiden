package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExtraBedAvailable {

    private BigDecimal price;

    private boolean available;

    @JsonProperty("age_limit")
    private int ageLimit;

    @JsonProperty("price_other_currency")
    private int priceOtherCurrency;

}
