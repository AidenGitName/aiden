package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class NetPriceOtherCurrency {
    private BigDecimal price;
    private String currency;
}
