package com.wonders.hms.bookingDotCom.bookingDetail.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CancellationInfo {
    private BigDecimal fee;
    private String timezone;
    private String from;
    private String currency;
    private String until;
}
