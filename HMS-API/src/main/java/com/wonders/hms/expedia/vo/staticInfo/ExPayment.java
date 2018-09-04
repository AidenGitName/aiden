package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExPayment {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("types")
    private ExPaymentType type;
}
