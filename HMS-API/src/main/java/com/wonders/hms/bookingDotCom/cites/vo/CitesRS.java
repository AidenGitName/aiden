package com.wonders.hms.bookingDotCom.cites.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CitesRS {
    private ArrayList<Result> result;
    private Meta meta;
}
