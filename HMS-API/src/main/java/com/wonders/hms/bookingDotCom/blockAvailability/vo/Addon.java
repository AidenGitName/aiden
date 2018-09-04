package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Addon {
    private int type;

    @JsonProperty("price_mode")
    private String priceMode;

    @JsonProperty("add_id")
    private String addonId;

    private String currency;

    @JsonProperty("price_per_unit")
    private BigDecimal pricePerUnit;

    private String label;

    @JsonProperty("other_currency")
    private OtherCurrency otherCurrency;
}
