package com.wonders.hms.util.vo;

import com.wonders.hms.util.vo.api.AgentVO;
import com.wonders.hms.util.vo.api.InfraVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiVO {
    private AgentVO agent;
    private InfraVO infra;
}
