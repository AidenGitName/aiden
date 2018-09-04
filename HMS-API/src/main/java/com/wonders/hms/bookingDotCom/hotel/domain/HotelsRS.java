package com.wonders.hms.bookingDotCom.hotel.domain;

import com.wonders.hms.bookingDotCom.hotel.vo.Meta;
import com.wonders.hms.bookingDotCom.hotel.vo.Result;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class HotelsRS {

    private ArrayList<Result> result;
    private Meta meta;
}
