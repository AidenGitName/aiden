package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BedConfiguration {
    private int count;

    private String description;

    private String name;

    @JsonProperty("description_imperial")
    private String descriptionImperial;
}
