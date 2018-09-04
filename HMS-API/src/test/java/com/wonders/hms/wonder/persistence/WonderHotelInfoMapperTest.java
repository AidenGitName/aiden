package com.wonders.hms.wonder.persistence;

import com.wonders.hms.wonder.vo.WonderHotel;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WonderHotelInfoMapperTest {

    @Autowired
    private WonderHotelInfoMapper wonderHotelInfoMapper;

    @Test
    @Ignore
    public void updateChangedWonderHotel() {
        WonderHotel wonderHotel = wonderHotelInfoMapper.getHotelByHotelId(1L);
        System.out.println(wonderHotel);
        wonderHotel.setStar(5.0);
        wonderHotel.setCheckin("11:11 AM");
        wonderHotel.setCheckout("22:22 PM");
        wonderHotel.setCategory("HOTEL");
        wonderHotel.setPhone(null);
        wonderHotelInfoMapper.updateChangedWonderHotel(wonderHotel);
        System.out.println(wonderHotel);
    }

    @Test
    @Ignore
    public void insertWonderHotel() {
        WonderHotel wonderHotel = new WonderHotel();
        wonderHotel.setLongitude(111.111111);
        wonderHotel.setLatitude(2222.2222222);
        wonderHotel.setHotelVendorIndex(1L);
        wonderHotel.setName("test hotel");
        wonderHotel.setNameEng("test hotel eng");
        wonderHotel.setCity("city");
        wonderHotel.setCityEng("city eng");
        wonderHotel.setCountry("EN");
        wonderHotel.setAddress("somewhere...");
        wonderHotel.setCategory("HOTEL");
        wonderHotel.setCheckin("12:12 AM");
        wonderHotel.setCheckout("11:12 PM");
        wonderHotel.setStar(5.0);
        wonderHotel.setPostalCode("1234");

        System.out.println(wonderHotel.getId());
        wonderHotelInfoMapper.insertWonderHotel(wonderHotel);
        System.out.println(wonderHotel.getId());
    }
}
