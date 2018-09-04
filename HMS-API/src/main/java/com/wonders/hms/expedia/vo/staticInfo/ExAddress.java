package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExAddress {

    @JsonProperty("line_1")
    private String addressLine1;

    @JsonProperty("line_2")
    private String addressLine2;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state_province_code")
    private String stateProvinceCode;

    @JsonProperty("state_province_name")
    private String stateProvinceName;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("country_code")
    private String countryCode;

}
