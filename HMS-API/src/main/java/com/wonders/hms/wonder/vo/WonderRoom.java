package com.wonders.hms.wonder.vo;

import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.type.HotelVendorKind;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class WonderRoom implements CommonRoom {

    private String name;

    private BigDecimal totalPrice;

    @Deprecated
    private String url;

    private String listUrl;

    private String detailUrl;

    private HotelVendorKind hotelVendorKind;

    private BigDecimal pricePerNight;

    private Long hotelId;

    private String information;

    private String uuid;

    private String roomImageUrl;

    public void resetUuid() {
        String oldUuid = this.uuid;

        this.uuid = UUID.randomUUID().toString().replaceAll("-", "");

        resetUuidUrl(oldUuid);
    }

    private void resetUuidUrl(String oldUuid) {
        this.listUrl = this.listUrl.replaceAll(oldUuid, this.uuid);
        this.detailUrl = this.detailUrl.replaceAll(oldUuid, this.uuid);
    }
}
