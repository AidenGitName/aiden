package com.wonders.hms.wonder.service;

import com.wonders.hms.hotel.service.WonderHotelInfoMigrationService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WonderHotelInfoMigrationServiceTest {

    @Autowired
    WonderHotelInfoMigrationService wonderHotelInfoMigrationService;

    @Test
    @Ignore
    public void mergeHotel(){
        wonderHotelInfoMigrationService.migrationAgodaToBooking();
    }

    @Test
    @Ignore
    public void mergeHotelBaseExpedia(){
        wonderHotelInfoMigrationService.migrationExpediaToBooking();
    }

    @Test
    @Ignore
    public void wonderHotelInfoCityNameCompareAutoComplete() {
        wonderHotelInfoMigrationService.cityNameCompareAutoComplete();
    }

    @Test
    public void getHotelNameTest() throws IOException {
        Long bookingId = wonderHotelInfoMigrationService.getBookingId("드 루 비치 리조트", "MY");
        System.out.println(bookingId);
    }
}
