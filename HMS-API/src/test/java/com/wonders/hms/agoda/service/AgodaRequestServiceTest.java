package com.wonders.hms.agoda.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgodaRequestServiceTest {
    @Autowired
    private AgodaRequestService agodaRequestService;


    @Test
    public void updateBookingResult() throws Exception {
        agodaRequestService.updateBookingResult();
    }
}
