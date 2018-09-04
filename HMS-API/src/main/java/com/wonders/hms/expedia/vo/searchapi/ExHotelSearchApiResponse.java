package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExHotelSearchApiResponse {

    @JsonProperty("Count")
    private int count;

    @JsonProperty("TransactionId")
    private String transactionId;

    @JsonProperty("NumberOfRooms")
    private int numberOfRooms;

    @JsonProperty("Occupants")
    private List<ExApiOccupants> occupants;

    @JsonProperty("Hotels")
    private List<ExApiHotel> hotels;

    @JsonProperty("StayDates")
    private ExApiStayDate stayDates;

    @JsonProperty("LengthOfStay")
    private int lengthOfStay;
}
