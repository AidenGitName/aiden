package com.wonders.hms.bookingDotCom.hotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetail {
    @JsonProperty("payment_id")
    private int paymentId;

    private boolean bookable;

    private boolean payable;

    @JsonProperty("cvc_required")
    private boolean cvcRequired;
}
