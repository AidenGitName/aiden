package com.wonders.hms.user.service;

import com.wonders.hms.bookingDotCom.bookingDetail.vo.Booking;
import com.wonders.hms.bookingDotCom.bookingDetail.vo.BookingDetailRS;
import com.wonders.hms.user.type.BookStatus;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @Ignore
    public void updateSearchHistoryWithBookingDotComTest() throws Exception {
        userService.updateSearchHistoryWithBookingDotCom();
    }

    @Test
    @Ignore
    public void updateSearchHistoryOneCaseTest() throws Exception {
        Booking booking = new Booking();

        booking.setUrl("https://www.booking.com/hotel/kr/holiday-inn-incheon-songdo.html");
        booking.setReservationId(1111111111L);
        booking.setPriceLocal(BigDecimal.valueOf(231000));
        booking.setStatus("cancelled");
        booking.setPincode("4799");
        booking.setCancellationDate("2018-08-10");
        booking.setCheckin(LocalDate.of(2018,8,25));
        booking.setCreated(LocalDateTime.of(2018,8,10,7,22,19));
        booking.setAffiliateId(1414756L);
        booking.setAffiliateLabel("ff47522f63704d49864eba7b1131246a");
        booking.setTotalRoomNights(1);
        booking.setUrl("https://www.booking.com/hotel/kr/tongyeong-bay-condo.html");
        booking.setCheckout(LocalDate.of(2018,8,26));
        // test 시 코드 수정 필요
//        userService.insertBookingDotComBookedInfo(booking);
//        userService.insertBookingDotComCancelInfo(booking);
    }
}
