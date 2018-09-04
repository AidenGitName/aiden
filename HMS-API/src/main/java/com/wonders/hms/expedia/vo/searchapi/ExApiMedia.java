package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExApiMedia {

    @JsonProperty("Type")
    private int type; //1.image , only 1 is supported now(2018.07,20)

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Size")
    private String size;

    @JsonProperty("Url")
    private String url;
}
