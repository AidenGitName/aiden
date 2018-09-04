package com.wonders.hms.util.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EncryptVO {
    private Map<String, String> cookie;
    private Map<String, String> database;
}
