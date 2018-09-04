package com.wonders.hms.util.vo.encrypt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Database {
    private String padding;
    private String key;
    private String block;
    private String algorithm;
    private String iv;
}
