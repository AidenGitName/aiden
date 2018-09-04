package com.wonders.hms.agoda.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AgodaErrorMessage {

    @JsonProperty("ErrorMessage")
    private String errorMessage;
}
