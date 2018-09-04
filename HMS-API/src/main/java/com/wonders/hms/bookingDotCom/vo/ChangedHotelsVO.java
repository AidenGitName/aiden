package com.wonders.hms.bookingDotCom.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
public class ChangedHotelsVO {
    @JsonProperty("city_ids")
    private ArrayList<Long> cityIds;

    private ArrayList<String> countries;

    @JsonProperty("region_ids")
    private ArrayList<Long> regionIds;

    @JsonProperty("last_change")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastChange;
}
