package com.wonders.hms.agoda.vo;

import com.wonders.hms.hotel.vo.CommonHotel;
import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.type.HotelVendorKind;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class AgodaHotel extends CommonHotel {

    private Long id;
    private Long hotelId;
    private Long chainId;
    private String chainName;
    private Long brandId;
    private String brandName;
    private String hotelName;
    private String hotelFormerlyName;
    private String hotelTranslatedName;
    private String addressLine1;
    private String addressLine2;
    private String zipcode;
    private String city;
    private String state;
    private String country;
    private String countryIsoCode;
    private Double starRating;
    private Double longitude;
    private Double latitude;
    private String url;
    private String checkin;
    private String checkout;
    private int numberRooms;
    private int numberFloors;
    private String yearOpened;
    private String yearEnovated;
    private String photo1;
    private String photo2;
    private String photo3;
    private String photo4;
    private String photo5;
    private String overview;
    private String ratesFrom;
    private Long continentId;
    private String continentName;
    private Long cityId;
    private Long countryId;
    private int numberOfReviews;
    private Double ratingAverage;
    private String ratesCurrency;
    private String ratesFromExclusive;
    private String accommodationType;
    private List<? extends CommonRoom> rooms;

    public String getEngName() {
        return this.hotelName;
    }

    @Override
    public String getName() {
        return this.hotelTranslatedName;
    }

    @Override
    public Long getHotelId() {
        return this.hotelId;
    }

    @Override
    public String getAddress() {
        return this.addressLine1 + " " +this.addressLine2;
    }

    @Override
    public Double getStar() { return this.starRating; }

    @Override
    public Double getLongitude() {
        return this.longitude;
    }

    @Override
    public Double getLatitude() {
        return this.latitude;
    }

    @Override
    public String getHotelUrl() {
        return this.url;
    }

    @Override
    public String getCheckinTime() {
        return this.checkin;
    }

    @Override
    public List<String> getImageUrls() {
        List<String> imageUrls = new ArrayList();
        imageUrls.add(this.photo1);
        imageUrls.add(this.photo2);
        imageUrls.add(this.photo3);
        imageUrls.add(this.photo4);
        imageUrls.add(this.photo5);

        return imageUrls;
    }

    @Override
    public HotelVendorKind getVendor() {
        return HotelVendorKind.AGODA;
    }

    @Override
    public Double getScore() { return this.ratingAverage; }

    public int compare(AgodaHotel other) {
        if ((this.getHotelId().compareTo(other.getHotelId()) == 0)
            && this.getAddressLine1().equals(other.getAddressLine1())
            && this.getAddressLine2().equals(other.getAddressLine2())
                && this.getCheckin().equals(other.getCheckin())
                && this.getCheckout().equals(other.getCheckout())
                && this.getHotelUrl().equals(other.getHotelUrl())
                && this.getImageUrls().equals(other.getImageUrls())
                && (this.getLatitude().compareTo(other.getLatitude()) == 0)
                && (this.getLongitude().compareTo(other.getLongitude()) == 0)
                && this.getRatesFrom().equals(other.getRatesFrom())
                && this.getRatesFromExclusive().equals(other.getRatesFromExclusive())
                && this.getAccommodationType().equals(other.getAccommodationType())
                && (this.getRatingAverage().compareTo(other.getRatingAverage()) == 0)
                && (this.getScore().compareTo(other.getScore()) == 0)
                && (this.getStar().compareTo(other.getStar()) == 0)
                && (this.getNumberOfReviews() == other.getNumberOfReviews())
                && (this.getStarRating().compareTo(other.getStar()) == 0)
            ) {
            return 0;
        }

        return 1;
    }
}
