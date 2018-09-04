package com.wonders.hms.util.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KmsVO {
    // 정상 응답시

    @JsonProperty(value = "Message")
    private MessageVO Message;
    @JsonProperty(value = "Code")
    private String Code;

    // 에러 발생시
    private String status;
    private String data;
}
