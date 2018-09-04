package com.wonders.hms.bookingDotCom.changedHotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ChangedHotel {
    @JsonProperty("hotel_id")
    private Long hotelId;

    private ArrayList<String> changes;
}
