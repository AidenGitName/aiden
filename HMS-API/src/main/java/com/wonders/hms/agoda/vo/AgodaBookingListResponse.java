package com.wonders.hms.agoda.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AgodaBookingListResponse {

    @JsonProperty("Bookings")
    private List<AgodaBooking> bookings;

    @JsonProperty("status")
    private String status;

}
