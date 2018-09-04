package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class Result {
    @JsonProperty("qualifies_for_no_cc_reservation")
    private boolean qualifiesForNoCcReservation;

    @JsonProperty("cc_required")
    private boolean ccRequired;

    private ArrayList<Policy> policies;

    @JsonProperty("booking_commission")
    private double bookingCommission;

    @JsonProperty("address_required")
    private boolean addressRequired;

    @JsonProperty("cvc_required")
    private boolean cvcRequired;

    @JsonProperty("direct_payment")
    private boolean directPayment;

    @JsonProperty("hotel_url")
    private String hotelUrl;

    @JsonProperty("is_flash_deal")
    private boolean isFlashDeal;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkout;

    @JsonProperty("group_recommendations")
    private ArrayList<GroupRecommendation> groupRecommendations;

    @JsonProperty("deep_link_url")
    private String deepLinkUrl;

    @JsonProperty("important_information")
    private String importantInformation;

    @JsonProperty("hotel_id")
    private Long hotelId;

    @JsonProperty("domestic_no_cc")
    private boolean domesticNoCc;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkin;

    @JsonProperty("block")
    private ArrayList<Block> blocks;

    @JsonProperty("max_rooms_in_reservation")
    private int maxRoomsInReservation;
}
