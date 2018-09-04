package com.wonders.hms.bookingDotCom.hotel.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDataUpdateTimeByCountry {
    private Long id;
    private Long countryId;
    private LocalDateTime updateTime;
}
