package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ExLink {

    @JsonProperty("method")
    private String method;

    @JsonProperty("href")
    private String href;

    @JsonProperty("350px")
    private Ex350pxLink link350px;

    @JsonProperty("70px")
    private Ex70pxLink link70px;

    @JsonProperty("1000px")
    private Ex1000pxLink link1000px;
}
