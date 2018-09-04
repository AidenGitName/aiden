package com.wonders.hms.util.vo.api.agent;

import com.wonders.hms.util.vo.api.agent.agoda.Special;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Agoda {
    private String apiKey;
    private String siteId;
    private Special special;
}
