package com.wonders.hms.hotel.service;

import com.wonders.hms.expedia.service.ExpediaHotelInfoUpdateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpediaHotelInfoUpdateServiceTest {

    @Autowired
    private ExpediaHotelInfoUpdateService expediaHotelInfoUpdateService;

    @Test
    @Transactional
    public void update() throws Exception {
        expediaHotelInfoUpdateService.updateHotelInfo();
    }
}
