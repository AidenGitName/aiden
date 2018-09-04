package com.wonders.hms.agoda.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.type.HotelVendorKind;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgodaRoom implements CommonRoom {

    @JsonProperty("id")
    private Long id;

    private Long hotelId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("standardtranslation")
    private String standardtranslation;

    @JsonProperty("ex")
    private BigDecimal ex;

    @JsonProperty("loyal")
    private String loyal;

    @JsonProperty("inc")
    private BigDecimal inc;

    @JsonProperty("benefit")
    private String benefit;

    @JsonProperty("url")
    private String url;

    @JsonProperty("ratecategoryid")
    private int rateCategoryId;

    @JsonProperty("freewifi")
    private Boolean isFreeWifi;

    @JsonProperty("freecancellation")
    private Boolean isFreeCancelLation;

    @JsonProperty("booknowpaylater")
    private Boolean isBookNowPayLater;

    @JsonProperty("promotionsavings")
    private BigDecimal promotionSavings;

    @JsonProperty("promotiontext")
    private String promotionText;

    private int stayNights;

    private int numberOfRooms;

    private String uuid;

    public AgodaRoom() {
        this.uuid = UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public Long getHotelId() {
        return this.hotelId;
    }

    @Override
    public HotelVendorKind getHotelVendorKind() {
        return HotelVendorKind.AGODA;
    }

    @Override
    public String getName() {
        return this.standardtranslation;
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = this.ex.multiply(BigDecimal.valueOf(stayNights))
                .multiply(BigDecimal.valueOf(numberOfRooms));
        return totalPrice;
    }

    @Override
    public String getUrl() { return this.url + "&tag=" + this.uuid; }

    @Override
    public String getListUrl() {
        return this.url + "&tag=" + this.uuid;
    }

    @Override
    public String getDetailUrl() {
        return this.url + "&tag=" + this.uuid;
    }

    @Override
    public BigDecimal getPricePerNight() {
        return this.ex;
    }

    @Override
    public String getInformation() {
        if (promotionText != null) return promotionText;
        if (benefit != null) return benefit;
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
