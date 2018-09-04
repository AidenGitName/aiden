package com.wonders.hms.util.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageVO {

    @JsonIgnoreProperties(value = "api")
    private ApiVO api;
    @JsonIgnoreProperties(value = "database")
    private DatabaseVO database;
    @JsonIgnoreProperties(value = "encrypt")
    private EncryptVO encrypt;
    @JsonIgnoreProperties(value = "redis")
    private RedisVO redis;
}
