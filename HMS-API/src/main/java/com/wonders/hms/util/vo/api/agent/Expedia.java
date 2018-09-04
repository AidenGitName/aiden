package com.wonders.hms.util.vo.api.agent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wonders.hms.util.vo.api.agent.expedia.Crosssell;
import com.wonders.hms.util.vo.api.agent.expedia.Ph;
import com.wonders.hms.util.vo.api.agent.expedia.Special;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expedia {
    private String key;
    private String password;
    private String tpid;
    private String eapid;
    private Special special;
    private Ph ph;
    private Crosssell crosssell;
}
