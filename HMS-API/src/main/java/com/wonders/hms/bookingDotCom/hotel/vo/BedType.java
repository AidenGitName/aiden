package com.wonders.hms.bookingDotCom.hotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BedType {
    private String name;
    private String count;

    @JsonProperty("descriptions_imperial")
    private String descriptionsImperial;

    private String description;
}
