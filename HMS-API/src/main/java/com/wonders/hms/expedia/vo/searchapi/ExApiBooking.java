package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.user.type.BookStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExApiBooking {

    @JsonProperty("TransactionId")
    private String transactionId;

    @JsonProperty("Warnings")
    private List<String> warnings;

    @JsonProperty("ItineraryNumber")
    private Long itineraryNumber;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("BookingDate")
    private String bookingDate;

    @JsonProperty("HotelDetails")
    private ExApiHotelDetails hotelDetails;

    @JsonProperty("TotalPrice")
    private ExApiMoney totalPrice;

    public BookStatus getStatus() {
        if ("BOOKED".equals(this.status)) return BookStatus.BOOKED;
        else if ("CANCELED".equals(this.status)) return BookStatus.CANCEL;

        return BookStatus.BOOKING;
    }
}
