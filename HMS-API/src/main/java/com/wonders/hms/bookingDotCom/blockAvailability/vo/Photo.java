package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Photo {
    @JsonProperty("url_square60")
    private String urlSquare60;

    @JsonProperty("url_max300")
    private String urlMax300;

    @JsonProperty("photo_id")
    private int photoId;

    @JsonProperty("photo_tags")
    private PhotoTag photoTag;

    @JsonProperty("url_original")
    private String urlOriginal;
}
