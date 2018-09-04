package com.wonders.hms.bookingDotCom.service;


import com.wonders.hms.bookingDotCom.blockAvailability.domain.BlockAvailabilityRS;
import com.wonders.hms.bookingDotCom.vo.BlockAvailabilityVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlockAvailabilityTest {

    @Autowired
    BookingDotComService bookingDotComService;

    @Test
    public void blockAvailabilityTest() throws Exception {
        BlockAvailabilityVO blockAvailabilityVO = new BlockAvailabilityVO();

        blockAvailabilityVO.setCheckin(LocalDate.of(2018, 9, 27));
        blockAvailabilityVO.setCheckout(LocalDate.of(2018, 9, 29));

        ArrayList<Long> hotelIds = new ArrayList<>();

        hotelIds.add(new Long(2759154));

        blockAvailabilityVO.setHotelIds(hotelIds);

        ArrayList<String> room1 = new ArrayList<String>();

        room1.add("A");
        room1.add("A");

        blockAvailabilityVO.setRoom1(room1);

        ArrayList<String> extras = new ArrayList<String>();

        extras.add("photos");

        blockAvailabilityVO.setExtras(extras);

        BlockAvailabilityRS blockAvailabilityRS = bookingDotComService.blockAvailability(blockAvailabilityVO);

        System.out.println(blockAvailabilityRS);
    }
}
