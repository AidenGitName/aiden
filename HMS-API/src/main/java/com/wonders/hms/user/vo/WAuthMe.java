package com.wonders.hms.user.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WAuthMe {
    private Long mid;
    private String name;
    private String mobile;
    private String email;
    private String gender;
    private String birth;
}
