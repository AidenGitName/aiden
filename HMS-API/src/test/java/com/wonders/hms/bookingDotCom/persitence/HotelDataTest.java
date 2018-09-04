package com.wonders.hms.bookingDotCom.persitence;

import com.wonders.hms.bookingDotCom.hotel.domain.HotelsRS;
import com.wonders.hms.bookingDotCom.hotel.vo.HotelData;
import com.wonders.hms.bookingDotCom.service.BookingDotComService;
import com.wonders.hms.bookingDotCom.vo.HotelsVO;
import com.wonders.hms.bookingDotCom.persistence.BookingHotelInfoMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelDataTest {
    @Autowired
    BookingDotComService bookingDotComService;

    @Autowired
    BookingHotelInfoMapper bookingHotelInfoMapper;

    @Test
    @Ignore
    public void saveSillaHotelData() throws Exception{
        Long sillaHotelId = new Long(254475);
        HotelsVO hotelsVO = new HotelsVO();

        ArrayList<Long> hotelIds = new ArrayList();
        hotelIds.add(sillaHotelId);

        hotelsVO.setHotelIds(hotelIds);

        HotelsRS hotelsRS = this.bookingDotComService.hotels(hotelsVO);

        bookingHotelInfoMapper.insertHotel(hotelsRS.getResult().get(0).getHotelId(), hotelsRS.getResult().get(0).getHotelData());

        System.out.println(hotelsRS);
    }

    @Test
    public void getSillaHotelData(){
        Long sillaHotelId = new Long(254475);

        HotelData hotelData = bookingHotelInfoMapper.getHotel(sillaHotelId);

        assertThat(hotelData.getName()).isEqualTo("The Shilla Seoul");
        System.out.println(hotelData.getName());
    }
}
