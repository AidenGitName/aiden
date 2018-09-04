package com.wonders.hms.expedia.ph.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PHConversionData {

    @JsonProperty("conversion_data")
    private PHConversionDataDetail phConversionDataDetail;
}
