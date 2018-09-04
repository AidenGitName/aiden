package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ExImage {

    @JsonProperty("caption")
    private String caption;

    @JsonProperty("hero_image")
    private boolean isHeroImage;

    @JsonProperty("category")
    private int category;

    @JsonProperty("links")
    private ExLink link;


}
