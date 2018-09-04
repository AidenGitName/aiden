package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExApiBedTypeOption {

    @JsonProperty("Id")
    private String bedTypeId;

    @JsonProperty("Description")
    private String description;
}
