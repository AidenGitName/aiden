package hotelRefactoring.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;

@Getter
@Setter
public class BpHotelSearch {
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
    private double longitude;
    private double latitude;
    private String checkinDate;
    private String checkoutDate;
    private String checkinTimeFrom;
    private String checkinTimeTo;
    private String checkoutTimeFrom;
    private String checkoutTimeTo;
    private ArrayList romms;
    private int numberFloors;
    private String yearOpened;
    private String yearRenovated;
    private String continentName;
    private int numberOfReviews;
    private String ratesCurrency;
    private String accommodationType;
    private String howFarKm;
    private String type;
    private int adults;
    private int children;
    private String language;
    private String currency;
    private String includeSoldout;
    private String minPrice;
    private String maxPrice;
    private String ratingAvg;
    private int page;
    private String otherOptions;
    private HashSet result;


}
