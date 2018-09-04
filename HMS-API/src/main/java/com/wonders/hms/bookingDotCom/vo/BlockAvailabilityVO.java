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
public class BlockAvailabilityVO {

    private static final String ADULT_STRING = "A";

    private static final String DEFAULT_LANGUAGE = "ko";
    private static final String DEFAULT_CURRENCY = "KRW";
    private static final ArrayList<String> DEFAULT_EXTRAS = new ArrayList<String>(
            Arrays.asList("photos", "payment_terms", "room_type_id"));

    // required param

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkout;

    @JsonProperty("hotel_ids")
    private ArrayList<Long> hotelIds;

    //optional param

    @JsonProperty("affiliate_id")
    private int affiliateId;

    @JsonProperty("allow_past")
    private int allowPast;

    @JsonProperty("block_ids")
    private ArrayList<String> blockIds;

    private String currency = DEFAULT_CURRENCY;

    @JsonProperty("detail_level")
    private int detailLevel;

    private ArrayList<String> extras = DEFAULT_EXTRAS;

    @JsonProperty("guest_cc")
    private String guestCc;

    @JsonProperty("guest_ip")
    private String guestIp;

    @JsonProperty("guest_qty")
    private ArrayList<Integer> guestQty;

    @JsonProperty("https_photos")
    private int httpsPhotos;

    @JsonProperty("is_24hr")
    private int is24hr;

    private String language = DEFAULT_LANGUAGE;

    @JsonProperty("limit_incremental_prices")
    private int limitIncrementalPrices;

    @JsonProperty("num_rooms")
    private int numRooms;

    private ArrayList<String> room1;

    @JsonProperty("show_only_test")
    private int showOnlyTest;

    @JsonProperty("show_test")
    private int showTest;

    private String units;

    @JsonProperty("user_platform")
    private String userPlatform;

    public void setRooms(int adultNum, List<Integer> childrenAges, int roomNum) {
        ArrayList<String> room1 = new ArrayList<String>();

        for(int i = 0; i < adultNum; i++) {
            room1.add(this.ADULT_STRING);
        }

        if (childrenAges != null) {
            for (Integer age: childrenAges) {
                room1.add(String.valueOf(age));
            }
        }

        this.room1 = room1;

        if (roomNum > 1) {
            this.numRooms = roomNum;
        }

    }

}
