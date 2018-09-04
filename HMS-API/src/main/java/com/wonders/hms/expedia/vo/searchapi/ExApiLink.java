package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExApiLink {

    @JsonProperty("WebSearchResult")
    private ExApiWebSearchResult webSearchResult;
}
