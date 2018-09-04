package com.wonders.hms.bookingDotCom.hotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class RoomData {

    @JsonProperty("room_name")
    private String roomName;

    @JsonProperty("room_facilities")
    private ArrayList<RoomFacility> roomFacilities;

    @JsonProperty("room_photos")
    private ArrayList<RoomPhoto> roomPhotos;

    @JsonProperty("room_id")
    private Long roomId;

    @JsonProperty("room_description")
    private String roomDescription;

    @JsonProperty("room_info")
    private RoomInfo roomInfo;
}
