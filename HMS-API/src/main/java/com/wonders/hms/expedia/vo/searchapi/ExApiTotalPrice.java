package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ExApiTotalPrice {

    @JsonProperty("Value")
    private BigDecimal value;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("LocalCurrencyPrice")
    private ExApiMoney localCurrencyPrice;
}
