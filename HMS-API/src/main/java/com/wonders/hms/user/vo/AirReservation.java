package com.wonders.hms.user.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class AirReservation {
    private String arrCityCd;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate depDtm;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate arrDtm;
}
