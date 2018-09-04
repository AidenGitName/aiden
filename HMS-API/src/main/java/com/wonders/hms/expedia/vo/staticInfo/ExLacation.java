package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExLacation {

    @JsonProperty("coordinates")
    private ExCoordinate coordinates;
}
