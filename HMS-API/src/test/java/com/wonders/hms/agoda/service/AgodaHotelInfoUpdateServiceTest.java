package com.wonders.hms.agoda.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgodaHotelInfoUpdateServiceTest {
    @Autowired
    private AgodaHotelInfoUpdateService agodaHotelInfoUpdateService;

    @Test
    @Ignore
    public void fileDownload() throws Exception {
        agodaHotelInfoUpdateService.hotelInfoUpdate();
    }
}
