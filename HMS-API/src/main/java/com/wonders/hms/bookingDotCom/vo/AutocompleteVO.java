package com.wonders.hms.bookingDotCom.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@Setter
@ToString(of = {"text"})
public class AutocompleteVO {

    private static final String DEFAULT_LANGUAGE = "ko";

    @NotBlank
    private String text;

    private String language = DEFAULT_LANGUAGE;

    @JsonProperty("affiliate_id")
    private String affiliateId;

    private ArrayList<String> extras;

}
