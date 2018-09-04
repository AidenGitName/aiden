package com.wonders.hms.util.vo.database;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cluster {
    private String username;
    private String host;
    private String password;
    private String port;
    private String dbname;
}
