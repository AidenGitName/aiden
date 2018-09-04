package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExRooms {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("descriptions")
    private ExRoomDescription description;

    @JsonProperty("amenities")
    private ExAmenity amenity;

    @JsonProperty("images")
    private ExImage image;

    @JsonProperty("bed_groups")
    private ExBedGroup bedGroup;
}
