package com.wonders.hms.schedule;

import com.wonders.hms.shcedule.HotelBookingHistoryScheduler;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelBookingHistorySchedulerTest {
    @Autowired
    HotelBookingHistoryScheduler hotelBookingHistoryScheduler;

    @Test
    public void hotelInfoUpdateTest(){
        hotelBookingHistoryScheduler.hotelInfoUpdate();
    }
}
