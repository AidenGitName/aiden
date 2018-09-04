package com.wonders.hms.bookingDotCom.bookingDetail.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingDetailRS {
    private List<Booking> result;
    private Meta meta;
}
