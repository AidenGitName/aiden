package com.wonders.hms.bookingDotCom.changedHotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Result {
    @JsonProperty("changed_hotels")
    private ArrayList<ChangedHotel> changedHotels;

    @JsonProperty("closed_hotels")
    private ArrayList<Long> closedHotels;
}
