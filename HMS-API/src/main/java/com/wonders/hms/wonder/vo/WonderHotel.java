package com.wonders.hms.wonder.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wonders.hms.hotel.service.HotelNameComparer;
import com.wonders.hms.hotel.vo.CommonHotel;
import com.wonders.hms.wonder.type.HotelVendorKind;
import com.wonders.hms.room.vo.CommonRoom;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString(exclude = {"rooms"})
@JsonPropertyOrder(value = {
        "id", "name", "address", "star", "rooms",
        "teaser", "checkin", "checkout", "importantFacilityKinds",
        "facilities", "roomTypes", "longitude", "latitude",
        "numberOfReviews"
})
public class WonderHotel extends CommonHotel {

    private Long id;

    private String name;

    private String address;

    private Double star = 0.0;

    private Double rating = 0.0;

    @JsonIgnore
    private List<String> images = new ArrayList<>();

    private List<WonderRoom> rooms = new ArrayList<>();

    private String teaser;

    private String checkin;

    private String checkout;

    private Set<String> facilities = new HashSet<String>();

    private List<RoomType> roomTypes;

    private Double longitude;

    private Double latitude;

    private Integer numberOfReviews = 0;

    @JsonIgnore
    // use to equals methods(호텔 이름 비교시에 사용)
    private HotelVendorKind sourceDataHotelVendorKind;

    @JsonIgnore
    // use to search hotel vendor
    private Long hotelVendorIndex;

    @JsonIgnore
    private String city;

    @JsonIgnore
    private String cityEng;

    @JsonIgnore
    private String nameEng;

    @JsonIgnore
    private String country;

    private String category;

    @JsonIgnore
    private String postalCode;

    @JsonIgnore
    private String hotelTeaser;

    @JsonIgnore
    private String phone;

    @JsonIgnore
    private Long agodaId;

    @JsonIgnore
    private Long bookingId;

    @JsonIgnore
    private Long expediaId;

    @Override
    public boolean equals(Object obj) {
        if ( obj != null && obj instanceof WonderHotel) {
            WonderHotel otherHotel = (WonderHotel) obj;

            HotelNameComparer hotelNameComparer = new HotelNameComparer();

            return hotelNameComparer.isSame(this, otherHotel);
        }

        return false;
    }

    @Deprecated
    public void addCommonRoom(List<? extends CommonRoom> rooms) {
        if (rooms == null) return;

        rooms.stream().forEach(room -> {
            WonderRoom wonderRoom = new WonderRoom();
            wonderRoom.setName(room.getName());
            wonderRoom.setUrl(room.getUrl());
            wonderRoom.setTotalPrice(room.getTotalPrice());
            wonderRoom.setPricePerNight(room.getPricePerNight());
            wonderRoom.setHotelVendorKind(room.getHotelVendorKind());
            wonderRoom.setInformation(room.getInformation());

            this.rooms.add(wonderRoom);
        });
    }

    public void add(CommonRoom commonRoom) {

        if (commonRoom.getHotelId().compareTo(this.id) == 0) {
            WonderRoom wonderRoom = new WonderRoom();
            wonderRoom.setName(commonRoom.getName());
            wonderRoom.setTotalPrice(commonRoom.getTotalPrice());
            wonderRoom.setHotelVendorKind(commonRoom.getHotelVendorKind());
            wonderRoom.setPricePerNight(commonRoom.getPricePerNight());
            wonderRoom.setUrl(commonRoom.getUrl());
            wonderRoom.setHotelId(commonRoom.getHotelId());
            wonderRoom.setInformation(commonRoom.getInformation());
            wonderRoom.setDetailUrl(commonRoom.getDetailUrl());
            wonderRoom.setListUrl(commonRoom.getListUrl());
            wonderRoom.setUuid(commonRoom.getUuid());
            wonderRoom.setRoomImageUrl(commonRoom.getRoomImageUrl());

            this.rooms.add(wonderRoom);
        }
    }

    @Override
    public Long getHotelId() {
        return this.id;
    }

    @Override
    public String getHotelUrl() {
        return null;
    }

    @Override
    public String getCheckinTime() {
        return this.checkin;
    }

    @Override
    public List<String> getImageUrls() {
        return this.images;
    }

    @Override
    public HotelVendorKind getVendor() {
        return this.sourceDataHotelVendorKind;
    }

    @Override
    public Double getScore() {
        return this.rating;
    }

    public Double getHotelWeight() {
        // review count data가 없음.
        return HotelWeight.computeWeight(this.getStar(), this.getNumberOfReviews(), this.getRating());
    }
}
