package com.wonders.hms.bookingDotCom.hotelAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentTerms {

    @JsonProperty("cancellation_description")
    private String cancellationDescription;

    private String name;

    @JsonProperty("prepayment_description")
    private String prepaymentDescription;
}
