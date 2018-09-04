package com.wonders.hms.bookingDotCom.bookingDetail.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Booking {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonProperty("hotel_fax")
    private String hotelFax;

    @JsonProperty("booker_email")
    private String bookerEmail;

    private String language;

    @JsonProperty("guest_name")
    private String guestName;

    @JsonProperty("hotel_email")
    private String hotelEmail;

    @JsonProperty("nr_rooms")
    private Integer nrRooms;

    @JsonProperty("total_room_nights")
    private Integer totalRoomNights;

    private String url;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkout;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkin;

    @JsonProperty("chain_ids")
    private String chainIds;

    @JsonProperty("fee_percentage")
    private Double feePercentage;

    @JsonProperty("guest_country")
    private String guestCountry;

    @JsonProperty("affiliate_id")
    private Long affiliateId;

    @JsonProperty("hotel_address")
    private String hotelAddress;

    @JsonProperty("hotel_phone")
    private String hotelPhone;

    @JsonProperty("reservation_id")
    private Long reservationId;

    private String creditslip;

    @JsonProperty("cancellation_info")
    private List<CancellationInfo> cancellationInfo;

    @JsonProperty("hotel_zipcode")
    private String hotelZipcode;

    @JsonProperty("hotel_name")
    private String hotelName;

    @JsonProperty("hotel_id")
    private Long hotelId;

    @JsonProperty("price_local")
    private BigDecimal priceLocal;

    @JsonProperty("affiliate_label")
    private String affiliateLabel;

    @JsonProperty("local_fee")
    private BigDecimal localFee;

    @JsonProperty("guest_city")
    private String guestCity;

    private String currency;

    @JsonProperty("hotel_countrycode")
    private String hotelCountrycode;

    @JsonProperty("booker_lastname")
    private String bookerLastname;

    @JsonProperty("loyalty_member_id")
    private String loyaltyMemberId;

    @JsonProperty("euro_fee")
    private BigDecimal euroFee;

    private String pincode;

    @JsonProperty("booker_firstname")
    private String bookerFirstname;

    @JsonProperty("cancellation_date")
    private String cancellationDate;

    private Charges charges;

    @JsonProperty("fee_calculation_date")
    private String feeCalculationDate;

    @JsonProperty("nr_guests")
    private Integer nrGuests;

    private String status;

    @JsonProperty("destination_ufi")
    private String destinationUfi;

    @JsonProperty("booker_mailinglist")
    private String bookerMailinglist;

    @JsonProperty("price_euro")
    private BigDecimal priceEuro;

    @JsonProperty("booker_phone")
    private String bookerPhone;

    private List<Room> rooms;

}
