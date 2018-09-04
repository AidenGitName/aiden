package com.wonders.hms.bookingDotCom.hotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomSize {
    @JsonProperty("metre_square")
    private double metreSquare;
}
