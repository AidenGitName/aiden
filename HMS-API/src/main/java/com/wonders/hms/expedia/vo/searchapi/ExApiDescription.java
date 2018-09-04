package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExApiDescription {

    @JsonProperty("LocationTeaser")
    private String locationTeaser;

    @JsonProperty("HotelTeaser")
    private String hotelTeaser;

    @JsonProperty("RoomTeaser")
    private String roomTeaser;
}
