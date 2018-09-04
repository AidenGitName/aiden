package com.wonders.hms.bookingDotCom.hotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class HotelData {

    private Long id;
    private Long bookingId;

    @JsonProperty("default_language")
    private String defaultLanguage;

    @JsonProperty("deep_link_url")
    private String deepLinkUrl;

    private String name;

    @JsonProperty("chain_id")
    private ArrayList<Long> chainId;

    @JsonProperty("exact_class")
    private Double exactClass;

    @JsonProperty("hotel_type_id")
    private Integer hotelTypeId;

    private Integer ranking;

    @JsonProperty("spoken_language")
    private ArrayList<String> spokenLanguage;

    private String country;

    @JsonProperty("checkin_checkout_times")
    private CheckinCheckoutTime checkinCheckoutTime;

    private Boolean preferred;

    @JsonProperty("max_rooms_in_reservation")
    private Integer maxRoomsInReservation;

    @JsonProperty("hotel_photos")
    private ArrayList<HotelPhoto> hotelPhotos;

    @JsonProperty("class_is_estimated")
    private Boolean classIsEstimated;

    @JsonProperty("hotel_policies")
    private ArrayList<HotelPolicy> hotelPolicies;

    @JsonProperty("review_score")
    private Double reviewScore;

    @JsonProperty("hotel_facilities")
    private ArrayList<HotelFacility> hotelFacilities;

    @JsonProperty("book_domestic_without_cc_details")
    private Boolean bookDomesticWithoutCcDetails;

    @JsonProperty("number_of_rooms")
    private Integer numberOfRooms;

    private Location location;

    @JsonProperty("hotel_description")
    private String HotelDescription;

    private String url;

    private String currency;

    private String address;

    @JsonProperty("hotelier_welcome_message")
    private String HotelierWelcomeMessage;

    @JsonProperty("hotel_important_information")
    private String hotelImportantInformation;

    @JsonProperty("payment_details")
    private ArrayList<PaymentDetail> paymentDetails;

    @JsonProperty("max_person_in_reservation")
    private Integer maxPersonInReservation;

    @JsonProperty("district_id")
    private Long districtId;

    private String zip;

    @JsonProperty("city_id")
    private Long cityId;

    @JsonProperty("class")
    private Integer hotelClass;

    private String city;

    @JsonProperty("number_of_reviews")
    private Integer numberOfReviews;
}
