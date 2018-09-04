package com.wonders.hms.bookingDotCom.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingDotComHotelInfoServiceTest {
    @Autowired
    BookingDotComHotelInfoService bookingDotComHotelInfoService;

    @Test
    @Ignore
    public void insertBookingDotComHotels() throws Exception {
        bookingDotComHotelInfoService.insertHotelData();
    }

    @Test
    @Ignore
    public void updateBookingDotComHotels() throws Exception {
        bookingDotComHotelInfoService.updateBookingHotelInfo();
    }
}
