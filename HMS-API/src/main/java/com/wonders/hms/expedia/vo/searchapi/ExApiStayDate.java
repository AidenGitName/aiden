package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ExApiStayDate {

    @JsonProperty("CheckInDate")
    private String checkIn;

    @JsonProperty("CheckOutDate")
    private String checkOut;
}
