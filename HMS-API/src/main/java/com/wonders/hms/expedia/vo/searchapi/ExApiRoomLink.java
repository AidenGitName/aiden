package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExApiRoomLink {

    @JsonProperty("WebDetails")
    private ExApiWebDetail webDetail; // 개별 방 결제 화면가는 링크

    @JsonProperty("WebSearchResult")
    private ExApiWebDetail webSearchResult; // 방 목록 리스트 가는 링크

    @JsonProperty("Accept")
    private String accept;

    @JsonProperty("Method")
    private String method;

    @JsonProperty("Href")
    private String href;

    @JsonProperty("ApiDetails")
    private ExApiApiDetails apiDetails;
}
