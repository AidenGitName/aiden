package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Ex70pxLink {
    @JsonProperty("method")
    private String method;

    @JsonProperty("href")
    private String href;
}
