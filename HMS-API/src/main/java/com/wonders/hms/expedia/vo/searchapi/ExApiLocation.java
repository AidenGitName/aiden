package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ExApiLocation {

    @JsonProperty("Address")
    private ExApiAddress address;

    @JsonProperty("GeoLocation")
    private ExApiGeoLocation geoLocation;
}
