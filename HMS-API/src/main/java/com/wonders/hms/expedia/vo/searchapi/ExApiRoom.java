package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExApiRoom {
    @JsonProperty("Status")
    private String status;

    @JsonProperty("StayDates")
    private List<ExApiStayDate> stayDate;
}
