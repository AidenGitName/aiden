package com.wonders.hms.bookingDotCom.persitence;

import com.wonders.hms.bookingDotCom.persistence.BookingDataUpdateTimeByCountryMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingDataUpdateTimeByCountryMapperTest {
    @Autowired
    BookingDataUpdateTimeByCountryMapper bookingDataUpdateTimeByCountryMapper;

    @Test
    @Ignore
    public void updateTimeTest() {
        bookingDataUpdateTimeByCountryMapper.updateBookingDataUpdateTime(LocalDateTime.now(), new Long(1));
    }
}
