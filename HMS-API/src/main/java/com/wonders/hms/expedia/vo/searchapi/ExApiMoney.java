package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExApiMoney {

    @JsonProperty("Value")
    private BigDecimal value;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("LocalCurrencyPrice")
    private ExApiMoney localCurrencyPrice;
}
