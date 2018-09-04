package com.wonders.hms.agoda.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.user.type.BookStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@ToString
public class AgodaBooking {

    @JsonProperty("id")
    private Long bookingId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("hotelid")
    private int hotelId;

    @JsonProperty("hotelname")
    private String hotelName;

    @JsonProperty("cityname")
    private String cityName;

    @JsonProperty("received")
    private String received;

    @JsonProperty("lastmodified")
    private String lastModified;

    @JsonProperty("arrival")
    private String arrival;

    @JsonProperty("departure")
    private String departure;

    @JsonProperty("usdamount")
    private BigDecimal usdAmount;

    @JsonProperty("selfservice")
    private String selfSerice;

    @JsonIgnore
    public BookStatus getWonderBookingStatus() {
        if (this.status == null) {
            return BookStatus.BOOKING;
        }

        switch (this.status) {
            case "BookingReceived":
            case "BookingCharged":
//                return BookStatus.BOOKING;
            case "BookingConfirmed":
            case "Departed":
            case "BookingTest":
                return BookStatus.BOOKED;

            case "BookingRejected":
            case "BookingCancelled":
            case "BookingCancelledByCustomer":
            case "TechnicalError":
                return BookStatus.CANCEL;

            default: return BookStatus.BOOKING;
        }
    }
}
