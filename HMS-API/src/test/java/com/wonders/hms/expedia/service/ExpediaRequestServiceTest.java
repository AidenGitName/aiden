package com.wonders.hms.expedia.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpediaRequestServiceTest {

    @Autowired
    private ExpediaRequestService expediaRequestService;

    @Test
    public void getexpediaMinPrice() throws Exception {
       expediaRequestService.updateMinPrice();
    }
}
