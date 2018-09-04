package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExApiRoomPromotion {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Amount")
    private ExApiMoney amount;
}
