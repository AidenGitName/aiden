package com.wonders.hms.user.service;

import com.wonders.hms.user.vo.WAuthMe;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WonderIdServiceTest {
    @Autowired
    WonderIdService wonderIdService;

    @Test
    @Ignore
    public void getMemberInfoTest() {
        String token = "";
        String name = "";

        WAuthMe wAuthMe = wonderIdService.getMemberInfo(token);

        assert wAuthMe.getName().equals(name);
    }
}
