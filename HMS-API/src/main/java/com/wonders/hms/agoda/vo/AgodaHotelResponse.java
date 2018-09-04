package com.wonders.hms.agoda.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class AgodaHotelResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("base")
    private String base;

    @JsonProperty("Room")
    @JacksonXmlElementWrapper(useWrapping=false)
    private List<AgodaRoom> rooms;
}
