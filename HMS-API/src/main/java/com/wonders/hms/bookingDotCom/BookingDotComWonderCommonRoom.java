package com.wonders.hms.bookingDotCom;

import com.wonders.hms.bookingDotCom.blockAvailability.vo.Block;
import com.wonders.hms.bookingDotCom.blockAvailability.vo.MinPrice;
import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.type.HotelVendorKind;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingDotComWonderCommonRoom implements CommonRoom {
    private static final String KOREA_CURRENCY_CODE = "KRW";
    private Block block;
    private Long hotelVendorIndex;
    private String bookingUrl;
    private String detailUrl;
    private String listUrl;
    private int stayNight;
    private String uuid;
    private String roomTypeName;
    private int numberOfRooms;

    public BookingDotComWonderCommonRoom(
            Block block,
            Long hotelVendorIndex,
            String detailUrl,
            String listUrl,
            int stayNight,
            String roomTypeName,
            int numberOfRooms
    ) {
        this.block = block;
        this.hotelVendorIndex = hotelVendorIndex;
        this.bookingUrl = detailUrl;
        this.detailUrl = detailUrl;
        this.listUrl = listUrl;
        this.stayNight = stayNight;
        this.uuid = UUID.randomUUID().toString().replaceAll("-", "");
        this.roomTypeName = roomTypeName;
        this.numberOfRooms = numberOfRooms;
    }

    @Override
    public Long getHotelId() {
        return this.hotelVendorIndex;
    }

    @Override
    public HotelVendorKind getHotelVendorKind() {
        return HotelVendorKind.BOOKING_DOT_COM;
    }

    @Override
    public BigDecimal getTotalPrice() {
        MinPrice minPrice = this.block.getMinPrices();

        if (KOREA_CURRENCY_CODE.equals(minPrice.getCurrency())) {
            return minPrice.getPrice().multiply(BigDecimal.valueOf(this.numberOfRooms));
        }

        if (KOREA_CURRENCY_CODE.equals(minPrice.getOtherCurrency().getCurrency())) {
            return minPrice.getOtherCurrency().getPrice().multiply(BigDecimal.valueOf(this.numberOfRooms));
        }

        return minPrice.getPrice().multiply(BigDecimal.valueOf(this.numberOfRooms));
    }

    @Override
    public String getName() {
        return this.block.getName();
    }

    @Override
    public String getUrl() {
        return this.bookingUrl;
    }

    @Override
    public String getListUrl() {
        return this.listUrl + "&label=" + this.getUuid();
    }

    @Override
    public String getDetailUrl() {
        return this.detailUrl + this.getUuid();
    }

    @Deprecated
    public List<String> getImageUrls() {
        List<String> imageUrls = new ArrayList();
        this.block.getPhotos().forEach(photo -> imageUrls.add(photo.getUrlOriginal()));
        return imageUrls;
    }

    @Override
    public BigDecimal getPricePerNight() {
        return this.getTotalPrice()
                .divide(BigDecimal.valueOf(this.stayNight), 4, BigDecimal.ROUND_UP)
                .divide(BigDecimal.valueOf(this.numberOfRooms), 4, BigDecimal.ROUND_UP);
    }

    @Override
    public String getInformation() {
        return roomTypeName;
    }

    @Override
    public String getUuid() {
        return this.uuid;
    }

    @Override
    public String getRoomImageUrl() {
        if (this.block.getPhotos().isEmpty()) {
            return null;
        }
        return this.block.getPhotos().get(0).getUrlMax300();
    }

}
