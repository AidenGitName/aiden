package com.wonders.hms.bookingDotCom.changedHotel.domain;

import com.wonders.hms.bookingDotCom.changedHotel.vo.Meta;
import com.wonders.hms.bookingDotCom.changedHotel.vo.Result;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ChangedHotelsRS {
    private Result result;
    private Meta meta;
}
