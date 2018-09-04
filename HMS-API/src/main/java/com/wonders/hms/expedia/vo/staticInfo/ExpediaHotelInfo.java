package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class ExpediaHotelInfo {

    @JsonProperty("property_id")
    private String propertyId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private ExAddress address;

    @JsonProperty("ratings") // Container for property ratings
    private ExRating rating;

    @JsonProperty("location")
    private ExLacation location;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("fax")
    private String fax;

    @JsonProperty("category")
    private ExCategory category;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("checkin")
    private ExCheckin checkin;

    @JsonProperty("checkout")
    private ExCheckout checkout;

    @JsonProperty("fees")
    private ExFees fees;

    @JsonProperty("policies")
    private ExPolicy policy;

    @JsonProperty("amenities")
    private ExAmenityList amenity;

    @JsonProperty("images")
    private List<ExImage> image;

    @JsonProperty("onsite_payments")
    private ExPayment payment;

    @JsonProperty("rooms")
    private ExRooms rooms;

    @JsonProperty("rates") // Listing of additional rate information, enumerated by the Rate Plan ID
    private ExRate rates;

    @JsonProperty("descriptions")
    private ExHotelDescription description;

    @JsonProperty("date_entered")
    private String dateEntered;

}
