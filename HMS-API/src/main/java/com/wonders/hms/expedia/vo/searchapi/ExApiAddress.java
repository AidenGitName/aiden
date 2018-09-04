package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ExApiAddress {

    @JsonProperty("Address1")
    private String address1;

    @JsonProperty("Address2")
    private String address2;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Province")
    private String province;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("PostalCode")
    private String postalCode;

}
