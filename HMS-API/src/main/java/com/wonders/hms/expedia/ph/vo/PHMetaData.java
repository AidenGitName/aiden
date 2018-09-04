package com.wonders.hms.expedia.ph.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PHMetaData {

    @JsonProperty("dest")
    private String dest;

    @JsonProperty("tier")
    private String tier;

    @JsonProperty("device")
    private String device;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("rewards")
    private Boolean rewards;

    @JsonProperty("hotel_id")
    private Long hotelId;

    @JsonProperty("net_value")
    private BigDecimal netValue;

    @JsonProperty("dest_state")
    private String destState;

    @JsonProperty("hotel_type")
    private String hotelType;

    @JsonProperty("refundable")
    private Boolean refundable;

    @JsonProperty("device_type")
    private String deviceType;

    @JsonProperty("gross_value")
    private BigDecimal grossValue;

    @JsonProperty("hotel_brand")
    private String hotelBrand;

    @JsonProperty("room_nights")
    private int roomNights;

    @JsonProperty("star_rating")
    private Double starRating;

    @JsonProperty("dest_country")
    private String destCountry;

    @JsonProperty("flight_class")
    private String flightClass;

    @JsonProperty("activity_name")
    private String activityName;

    @JsonProperty("check_in_date")
    private String checkInDate;

    @JsonProperty("coupon_amount")
    private String couponAmount;

    @JsonProperty("booking_window")
    private int bookingWindow;

    @JsonProperty("check_out_date")
    private String checkOutDate;

    @JsonProperty("length_of_stay")
    private int lengthOfStay;

    @JsonProperty("marketing_code")
    private String marketingCode;

    @JsonProperty("points_awarded")
    private Double pointsAwarded;

    @JsonProperty("taxes_and_fees")
    private BigDecimal taxesAndFees;

    @JsonProperty("vacationrentals")
    private Boolean vacationrentals;

    @JsonProperty("itinerary_number")
    private String itineraryNumber;

    @JsonProperty("length_of_flight")
    private int lengthOfFlight;

    @JsonProperty("partnerloyaltyid")
    private String partnerLoyaltyId;

    @JsonProperty("original_net_value")
    private BigDecimal originalNetValue;

    @JsonProperty("override_percentage")
    private Double overridePercentage;

    @JsonProperty("original_gross_value")
    private BigDecimal originalGrossValue;
}
