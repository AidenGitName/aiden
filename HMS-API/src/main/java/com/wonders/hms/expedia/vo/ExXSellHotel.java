package com.wonders.hms.expedia.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.type.HotelVendorKind;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class ExXSellHotel implements CommonRoom {

    private String camref="1100l44Ed";

    @JsonProperty("hotelId")
    private Long hotelId;

    @JsonProperty("hotelName")
    private String hotelName;

    @JsonProperty("starRating")
    private String starRating;

    @JsonProperty("guestRating")
    private String guestRating;

    @JsonProperty("standAlonePrice")
    private String standAlonePrice;

    @JsonProperty("airAttachedPrice")
    private String airAttachedPrice;

    @JsonProperty("hotelImage")
    private String hotelImage;

    @JsonProperty("hotelDeepLink")
    private String hotelDeepLink;

    private String uuid;

    private BigDecimal totalPrice;

    public ExXSellHotel() {
        this.uuid = UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public HotelVendorKind getHotelVendorKind() {
        return HotelVendorKind.EXPEDIA;
    }

    @Override
    public String getName() {
        return "익스피디아 특전!";
    }

    @Override
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getListUrl() {
        return getDetailUrl();
    }

    @Override
    public String getDetailUrl() {
        String url = "https://prf.hn/click" +
                "/camref:" + camref +
                "/adref:xsell" +
                "/pubref:" + uuid +
                "/destination:" + this.hotelDeepLink;
        return url;
    }

    @Override
    public BigDecimal getPricePerNight() {
        String price = this.airAttachedPrice.replaceAll("₩", "");
        price = price.replaceAll(",","");

        return new BigDecimal(price);
    }

    @Override
    public String getInformation() {
        return null;
    }

    @Override
    public String getUuid() {
        return this.uuid;
    }

    @Override
    public String getRoomImageUrl() {
        return null;
    }

}
