package com.wonders.hms.bookingDotCom.hotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomFacility {
    private String name;

    @JsonProperty("room_facility_type_id")
    private int roomFacilityTypeId;
}
