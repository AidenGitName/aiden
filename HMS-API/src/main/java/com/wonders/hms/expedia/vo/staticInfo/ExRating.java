package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//@JsonIgnoreProperties({"tripadvisor"})
public class ExRating{

    @JsonProperty("property")
    private ExProperty property;

    @JsonProperty("tripadvisor")
    private ExProperty tripadvisor;
}
