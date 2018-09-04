package com.wonders.hms.hotel.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wonders.hms.hotel.type.HotelSortTypeKind;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class HotelSearch {
    private final static Integer DEFAULT_OFFSET = 50;

    @JsonIgnore
    private String midToken;

    private Place place;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkout;

    private int distanceKm = 30;
    private int numberOfAdults = 1;
    private int numberOfRooms = 1;
    private int numberOfChildren = 0;
    private String destinationCode;

    private ArrayList<Integer> agesOfChildren = new ArrayList<>();

    private HotelSearchFilter hotelSearchFilter;

    private HotelSortTypeKind hotelSortTypeKind = HotelSortTypeKind.BEST;

    private Integer row = 0;
    private Integer offset = DEFAULT_OFFSET;

    public HotelSearch() {
        agesOfChildren.add(0);
    }

    public String getHotelSortTypeKind() {
        return this.hotelSortTypeKind.name();
    }

    public int getDistanceKm() {
        if (place.getPlaceType() != null
                && place.getPlaceType().equals("region")
                && place.getPlaceId().compareTo(2996L) == 0) {
            return 150;
        }
        return this.distanceKm;
    }
}
