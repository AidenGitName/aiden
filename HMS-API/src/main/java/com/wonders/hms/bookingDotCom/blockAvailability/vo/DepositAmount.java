package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositAmount {
    private int amount;

    @JsonProperty("OtherCurrency")
    private OtherCurrency otherCurrency;

    private String currency;
}
