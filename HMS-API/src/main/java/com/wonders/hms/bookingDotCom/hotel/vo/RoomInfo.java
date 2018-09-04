package com.wonders.hms.bookingDotCom.hotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;

@Getter
@Setter
public class RoomInfo {
    @JsonProperty("bed_configurations")
    private ArrayList<BedConfiguration> bedConfigurations;

    @JsonProperty("room_size")
    private RoomSize roomSize;

    private String roomtype;

    private boolean bookable;

    @JsonProperty("room_type_id")
    private int roomTypeId;

    @JsonProperty("bathroom_count")
    private int bathroomCount;

    @JsonProperty("bedroom_count")
    private int bedroomCount;

    @JsonProperty("max_price")
    private BigDecimal maxPrice;

    private ArrayList<BedroomDescription> bedrooms;

    @JsonProperty("room_type")
    private String roomType;

    @JsonProperty("min_price")
    private BigDecimal minPrice;

    @JsonProperty("max_persons")
    private int maxPersons;

    private int ranking;
}
