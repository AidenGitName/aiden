package com.wonders.hms.bookingDotCom.hotelAvailability.domain;


import com.wonders.hms.bookingDotCom.hotelAvailability.vo.Result;
import com.wonders.hms.bookingDotCom.hotelAvailability.vo.Meta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class HotelAvailabilityRS {
    private ArrayList<Result> result;
    private Meta meta;
}
