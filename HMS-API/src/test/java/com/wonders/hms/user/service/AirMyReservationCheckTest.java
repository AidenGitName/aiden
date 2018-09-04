package com.wonders.hms.user.service;

import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.user.vo.AirMyReservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirMyReservationCheckTest {
    @Autowired
    private AirMyReservationCheck airMyReservationCheck;

    @Test
    public void airMyReservationCheckTest() throws Exception{
        Long mid = 21280377L;
        HotelSearch hotelSearch = new HotelSearch();
        hotelSearch.setCheckin(LocalDate.of(2018,9,29));

        AirMyReservation airMyReservation = airMyReservationCheck.getNowTo1MonthAirReservationInfo(mid);

        System.out.println(airMyReservation);
//        log.info(airMyReservation);
    }
}
