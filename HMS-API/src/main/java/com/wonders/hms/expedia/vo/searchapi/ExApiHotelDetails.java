package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExApiHotelDetails {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Location")
    private ExApiLocation location;

    @JsonProperty("Rooms")
    private List<ExApiRoom> room;

    @JsonProperty("WebItinRetrieve")
    private ExApiWebItinRetrieve webItinRetrieve;
}
