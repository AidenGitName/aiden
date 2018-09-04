package com.wonders.hms.expedia.ph.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PHVonversionValue {

    @JsonProperty("conversion_status")
    private String conversionStatus;

    @JsonProperty("value")
    private BigDecimal value;

    @JsonProperty("publisher_commission")
    private BigDecimal publisherCommission;
}
