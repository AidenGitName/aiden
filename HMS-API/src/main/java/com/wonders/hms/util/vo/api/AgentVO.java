package com.wonders.hms.util.vo.api;

import com.wonders.hms.util.vo.api.agent.Agoda;
import com.wonders.hms.util.vo.api.agent.Booking;
import com.wonders.hms.util.vo.api.agent.Expedia;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AgentVO {
    private Expedia expedia;
    private Agoda agoda;
    private Booking booking;
}
