package com.wonders.hms.bookingDotCom.roomType.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Result {
    @JsonProperty("room_type_id")
    private Integer roomTypeId;

    private String name;

    private List<Translation> translations;
}
