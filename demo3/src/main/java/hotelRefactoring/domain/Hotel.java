package hotelRefactoring.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
public class Hotel {

    private Long hotelId;
    private String hotelName;
    private String hotelChinName;
    private String hotelBrandName;
    private String hotelTranslateName;
    private String hotelFormerlyName;
    private String address;
    private String city;
    private String county;
    private String continent;
    private String longitude;
    private String latitude;
    private String url;
    private String zipcode;
    private String checkinTime;
    private String checkoutTime;
    private String checkinDate;
    private String nemberRoome;
    private String yearOpened;
    private String yearRenovated;
    private String photes;
    private String overview;
    private String nemverOfReviews;
    private String ratringAverage;
    private String minPrice;
    private String maxPrice;
    private String specialPrice;
    private float ratingAvg;
    private ArrayList rooms;

}
