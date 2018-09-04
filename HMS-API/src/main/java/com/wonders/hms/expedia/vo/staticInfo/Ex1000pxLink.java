package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Ex1000pxLink {

    @JsonProperty("method")
    private String method;

    @JsonProperty("href")
    private String href;
}
