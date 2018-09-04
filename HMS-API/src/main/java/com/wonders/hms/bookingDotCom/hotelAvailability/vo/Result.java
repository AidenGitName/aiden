package com.wonders.hms.bookingDotCom.hotelAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;

@Getter
@Setter
public class Result {
    private String photo;

    @JsonProperty("deepLink_url")
    private String deepLinkUrl;

    private String country;

    @JsonProperty("net_price")
    private BigDecimal netPrice;

    @JsonProperty("review_nr")
    private int reviewNr;

    @JsonProperty("hotel_currency_code")
    private String hotelCurrencyCode;

    private String postcode;

    @JsonProperty("hotel_url")
    private String hotelUrl;

    private BigDecimal price;

    @JsonProperty("direct_payment")
    private boolean directPayment;

    private String address;

    @JsonProperty("cc_required")
    private boolean ccRequired;

    @JsonProperty("hotel_id")
    private Long hotelId;

    @JsonProperty("default_language")
    private String defaultLanguage;

    @JsonProperty("cvc_required")
    private boolean cvcRequired;

    private Double stars;

    @JsonProperty("hotel_amenities")
    private ArrayList<String> hotelAmenities;

    @JsonProperty("review_score")
    private Double reviewScore;

    @JsonProperty("review_score_word")
    private String reviewScoreWord;

    private Location location;

    @JsonProperty("checkin_time")
    private CheckinTime checkinTime;

    private ArrayList<BookingRoom> rooms;

    @JsonProperty("hotel_name")
    private String hotelName;
}
