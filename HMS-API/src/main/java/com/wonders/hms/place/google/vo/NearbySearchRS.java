package com.wonders.hms.place.google.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NearbySearchRS {
    @JsonProperty("html_attributions")
    private List<String> htmlAttributions;

    @JsonProperty("next_page_token")
    private String nextPageToken;

    private List<Result> results;

    private String status;
}
