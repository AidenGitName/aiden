package com.wonders.hms.bookingDotCom.hotel.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Result {

    @JsonProperty("hotel_data")
    private HotelData hotelData;

    @JsonProperty("hotel_id")
    private Long hotelId;

    @JsonProperty("room_data")
    private ArrayList<RoomData> roomData;
}
