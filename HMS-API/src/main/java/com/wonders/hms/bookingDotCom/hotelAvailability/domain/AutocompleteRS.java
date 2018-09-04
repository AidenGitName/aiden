package com.wonders.hms.bookingDotCom.hotelAvailability.domain;

import com.wonders.hms.autocomplete.vo.Meta;
import com.wonders.hms.autocomplete.vo.Result;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class AutocompleteRS {

    private ArrayList<Result> result;

    private Meta meta;
}
