package org.zerock.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "hotel_info")
public class HotelInfo {

    @Id
    private int hotelId;
    private int chainId;
    private String chainName;
    private int brandId;
    private String brandName;
    private String hotelName;
    private String hotelFormerlyName;
    private String hotelTranslatedName;
    private String addressline1;
    private String addressline2;
    private String zipcode;
    private String city;
    private String state;
    private String country;
    private String countryisocode;
    private float star_rating;
    private String longitude;
    private String latitude;
    private String url;
    private String checkin;
    private String checkout;
    private int numberrooms;
    private int numberfloors;
    private String yearopened;
    private String yearrenovated;
    private String photo1;
    private String photo2;
    private String photo3;
    private String photo4;
    private String photo5;
    private String overview;
    private Long rates_from;
    private int continent_id;
    private String continent_name;
    private int city_id;
    private int country_id;
    private int number_of_reviews;
    private float rating_average;
    private String rates_currency;
    private Long rates_from_exclusive;
    private String accommodation_type;
}
