package com.wonders.hms.bookingDotCom;

import com.wonders.hms.bookingDotCom.hotelAvailability.vo.Result;
import com.wonders.hms.hotel.vo.CommonHotel;
import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.type.HotelVendorKind;

import java.util.ArrayList;
import java.util.List;

public class BookingDotComHotel extends CommonHotel {

    private Result result;
    private List<CommonRoom> rooms;

    public BookingDotComHotel(Result result) {
        this.result = result;
    }

    public void setRooms(List<CommonRoom>  commonRooms) {
        this.rooms = commonRooms;
    }

    @Override
    public List<CommonRoom> getRooms() {
        return rooms;
    }

    @Override
    public String getName() {
        return result.getHotelName();
    }

    public Long getHotelId() {
        return result.getHotelId();
    }

    @Override
    public String getAddress() {
        return result.getAddress();
    }

    @Override
    public Double getStar() {
        return result.getStars();
    }

    @Override
    public Double getLongitude() {
        return result.getLocation().getLongitude();
    }

    @Override
    public Double getLatitude() {
        return result.getLocation().getLatitude();
    }

    @Override
    public String getHotelUrl() {
        return result.getHotelUrl();
    }

    @Override
    public String getCheckinTime() {
        return result.getCheckinTime().getFrom();
    }

    @Override
    public List<String> getImageUrls() {
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(result.getPhoto());

        return imageUrls;
    }

    @Override
    public HotelVendorKind getVendor() {
        return HotelVendorKind.BOOKING_DOT_COM;
    }

    @Override
    public Double getScore() {
        return result.getReviewScore();
    }
}
