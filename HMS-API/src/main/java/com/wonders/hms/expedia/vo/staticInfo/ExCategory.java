package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExCategory {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
}
