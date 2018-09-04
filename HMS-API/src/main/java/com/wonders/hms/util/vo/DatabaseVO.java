package com.wonders.hms.util.vo;

import com.wonders.hms.util.vo.database.Cluster;
import com.wonders.hms.util.vo.database.Reader;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class DatabaseVO {
    private Cluster cluster;
    private Reader reader;
}
