package com.wonders.hms.bookingDotCom.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.hotel.vo.Place;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class HotelAvailabilityVO {

    private static final String ADULT_STRING = "A";

    private static final int DEFAULT_RADIUS = 50;
    private static final int DEFAULT_ROWS = 150;

    private static final String DEFAULT_LANGUAGE = "ko";
    private static final ArrayList<String> DEFAULT_EXTRAS = new ArrayList<String>(
            Arrays.asList("room_amenities", "room_policies", "room_details", "hotel_amenities", "hotel_details", "payment_terms"));

    // required
    @JsonProperty("landmark_ids")
    private ArrayList<Long> landmarkIds;

    @JsonProperty("district_ids")
    private ArrayList<Long> districtIds;

    @JsonProperty("region_ids")
    private ArrayList<Long> regionIds;

    private Double latitude;

    private Double longitude;

    @JsonProperty("hotel_ids")
    private ArrayList<Long> hotelIds;

    @JsonProperty("city_ids")
    private ArrayList<Long> cityIds;

    // A,A,4 -> adults 2명, 4살 어린이 1명 1개방
    private ArrayList<String> room1;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkout;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkin;


    // optional param
    private String airport;

    private Integer radius;

    // 2번째 방에 몇명 넣을지..
    // room3 room4 room5 ....... room30
    // need minimum room number
//    private ArrayList<String> room2;
//    private ArrayList<String> room3;
//    private ArrayList<String> room4;
//    private ArrayList<String> room5;
//    private ArrayList<String> room6;
//    private ArrayList<String> room7;
//    private ArrayList<String> room8;
//    private ArrayList<String> room9;



    @JsonProperty("affiliate_id")
    private Integer affiliateId;

    private ArrayList<String> countries;

    private String currency;

    private ArrayList<String> extras = DEFAULT_EXTRAS;

    private ArrayList<String> filter;

    @JsonProperty("guest_country")
    private String guestCountry;

    @JsonProperty("guest_ip")
    private String guestIp;

    @JsonProperty("hotel_facilities")
    private ArrayList<String> hotelFacilities;

    private String language = DEFAULT_LANGUAGE;

    @JsonProperty("max_price")
    private Integer maxPrice;

    private String mealplan;

    @JsonProperty("min_price")
    private Integer minPrice;

    @JsonProperty("min_review_score")
    private Integer minReviewScore;

    @JsonProperty("no_rooms")
    private String noRooms;

    private Integer offset;

    private ArrayList<String> options;

    @JsonProperty("order_by")
    private String orderBy = "distance";

    @JsonProperty("order_dir")
    private String orderDir;

    @JsonProperty("property_type")
    private ArrayList<String> propertyType;

    @JsonProperty("room_facilities")
    private ArrayList<String> roomFacilities;

    private Integer rows = DEFAULT_ROWS;

    @JsonProperty("show_only_deals")
    private ArrayList<String> showOnlyDeals;

    private ArrayList<Integer> stars;

    @JsonProperty("user_platform")
    private String userPlatform;

    public void setPlaceInformation(Place place) {
        if (place.getPlaceName() == null) {
            this.latitude = place.getLatitude();
            this.longitude = place.getLongitude();
            this.radius = this.DEFAULT_RADIUS;
            return;
        }

        ArrayList<Long> placeIdList = new ArrayList();
        placeIdList.add(place.getPlaceId());

        switch (place.getPlaceType()) {
            case "landmark":
                this.landmarkIds = placeIdList;
                break;
            case "district":
                this.districtIds = placeIdList;
                break;
            case "region":
                this.regionIds = placeIdList;
                break;
            case "hotel":
                this.hotelIds = placeIdList;
                break;
            case "city":
                this.cityIds = placeIdList;
                break;
        }
    }

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
            this.noRooms = String.valueOf(roomNum);
        }

    }
}
