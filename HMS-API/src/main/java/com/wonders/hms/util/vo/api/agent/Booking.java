package com.wonders.hms.util.vo.api.agent;

import com.wonders.hms.util.vo.api.agent.booking.Affiliate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Booking {
    private String id;
    private String password;
    private Affiliate affiliate;
}
