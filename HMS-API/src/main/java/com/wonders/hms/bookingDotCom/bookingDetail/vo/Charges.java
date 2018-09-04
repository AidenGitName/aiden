package com.wonders.hms.bookingDotCom.bookingDetail.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Charges {
    @JsonProperty("transaction_sum")
    private BigDecimal transactionSum;

    private IncludedCharge included;
    private ExcludedCharge excluded;
}
