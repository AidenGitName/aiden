package com.wonders.hms.bookingDotCom.hotelAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExtraCharge {

    private String name;

    private String type;

    private BigDecimal amount;

    @JsonProperty("charge_price_mode")
    private String chargePriceMode;

    @JsonProperty("charge_amount")
    private BigDecimal chargeAmount;

    @JsonProperty("name_id")
    private int nameId;

    private boolean excluded;
}
