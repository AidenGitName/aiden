package com.wonders.hms.bookingDotCom.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
public class BookingDetailsVO {

    private static final List<String> DEFAULT_EXTRAS = new ArrayList<String>(
            Arrays.asList("key_collection_info", "no_show", "total_room_nights",
                    "hotel_page_url", "cancellation_info"));
    /*
        {checkin_from}+{checkin_until}
        {checkout_from}+{checkout_until}
        {created_from}+{created_until}
        {reservation_id}
        {last_change}
     */
    @JsonProperty("checkin_from")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkinFrom;

    @JsonProperty("checkin_until")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkinUtil;

    @JsonProperty("checkout_from")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkoutFrom;

    @JsonProperty("checkout_until")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkoutUntil;

    @JsonProperty("created_from")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdFrom;

    @JsonProperty("created_until")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdUntil;

    private List<String> extras = DEFAULT_EXTRAS;

    @JsonProperty("last_change")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastChange;

    @JsonProperty("local_fee_currency")
    private String localFeeCurrency;

    private Integer offset;

    private Integer rows;

    private List<String> options;

    @JsonProperty("reservation_id")
    private Long reservationId;

}
