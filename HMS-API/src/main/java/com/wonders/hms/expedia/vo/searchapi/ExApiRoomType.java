package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.type.HotelVendorKind;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExApiRoomType implements CommonRoom {

    private String camref="1100l44Ed";

    private Long hotelId;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Price")
    private ExApiPrice price;

    @JsonProperty("Links")
    private ExApiRoomLink roomLink;

    @JsonProperty("RoomKey")
    private String roomKey;

    @JsonProperty("OfferId")
    private String offerId;

    @JsonProperty("Promotions")
    private List<ExApiRoomPromotion> promotions;

    @JsonProperty("BedTypeOptions")
    private ExApiBedTypeOption bedTypeOptions;

    @JsonProperty("RatePlanType")
    private String ratePlanType;

    @JsonProperty("RatePlans")
    private List<ExApiRatePlans> ratePlans;

    @JsonProperty("Media")
    private ExApiMedia media;

    private String uuid;

    public ExApiRoomType() {
        this.uuid = UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public Long getHotelId() {
        return this.hotelId;
    }

    @Override
    public HotelVendorKind getHotelVendorKind() {
        return HotelVendorKind.EXPEDIA;
    }

    @Override
    public String getName() {
        if (this.description == null) {
            return "Room";
        }
        return this.description;
    }

    @Override
    public String getUrl() {
        return Optional.of(this.roomLink)
                .map(ExApiRoomLink::getApiDetails)
                .map(ExApiApiDetails::getHref)
                .orElse(null);
    }

    @Override
    public String getListUrl() {
        // shortcut list에서도 호텔 detail 화면 보이게끔 요청
//        return Optional.of(this.roomLink.getWebSearchResult())
//                .map(ExApiWebDetail::getHref)
//                .orElse(null);
        return getDetailUrl();
    }

    @Override
    public String getDetailUrl() {
        return Optional.of(this.roomLink.getWebDetail())
                .map(webDetail -> {
                    String url = "https://prf.hn/click" +
                            "/camref:" + camref +
                            "/adref:search" +
                            "/pubref:" + uuid +
                            "/destination:" + webDetail.getHref();
                    return url;
                })
                .orElse(null);
    }

    @Override
    public BigDecimal getPricePerNight() {
        return this.price.getAvgNightlyRate().getValue();
    }

    @Override
    public String getInformation() {
        if (bedTypeOptions != null) {
            return bedTypeOptions.getDescription();
        }

        List<String> promotionList = new ArrayList<>();
        if (promotions != null && promotions.size() > 0) {
            promotions.forEach(promotion -> {
                promotionList.add(promotion.getDescription());
            });
            return String.join(",", promotionList);
        }

        return null;
    }

    @Override
    public String getUuid() {
        return this.uuid;
    }

    @Override
    public String getRoomImageUrl() {
        if (media == null) return null;
        return media.getUrl();
    }

    @Override
    public BigDecimal getTotalPrice() {
        return Optional.of(this.price)
                .map(ExApiPrice::getBaseRate)
                .map(ExApiTotalPrice::getValue)
                .orElse(null);
    }
}
