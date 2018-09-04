package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtraCharge {
    //1 : per stay, 2: per person per stay, 3: per night, 4: per person per night, 5: percentage
    @JsonProperty("charge_price_mode")
    private int chargePriceMode;

    private String type;

    private boolean excluded;

    @JsonProperty("charge_amount")
    private double chargeAmount;

    @JsonProperty("name_id")
    private int nameId;

    private String name;

    private String currency;

    private OtherCurrency otherCurrency;

    private double amount;
}
