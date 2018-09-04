package com.wonders.hms.bookingDotCom.hotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckinCheckoutTime {

    @JsonProperty("checkout_from")
    private String checkoutFrom;

    @JsonProperty("checkin_to")
    private String checkinTo;

    @JsonProperty("checkin_from")
    private String checkinFrom;

    @JsonProperty("checkout_to")
    private String checkoutTo;
}
