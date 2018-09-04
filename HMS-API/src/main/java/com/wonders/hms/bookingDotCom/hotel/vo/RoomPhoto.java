package com.wonders.hms.bookingDotCom.hotel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class RoomPhoto {
    @JsonProperty("url_square60")
    private String urlSquare60;

    private ArrayList<String> tags;

    @JsonProperty("url_original")
    private String urlOriginal;

    @JsonProperty("url_max300")
    private String urlMax300;

    @JsonProperty("main_photo")
    private boolean mainPhoto;
}
