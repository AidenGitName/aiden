package com.wonders.hms.bookingDotCom.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CitesVO {
    @JsonProperty("city_ids")
    private ArrayList<Long> cityIds;

    private ArrayList<String> countries;

    private ArrayList<String> extras;

    private ArrayList<String> languages;

    private Integer offset;

    private Integer rows;
}
