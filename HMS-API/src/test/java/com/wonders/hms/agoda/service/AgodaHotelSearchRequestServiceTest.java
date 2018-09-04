package com.wonders.hms.agoda.service;

import com.wonders.hms.agoda.vo.AgodaHotelResult;
import com.wonders.hms.agoda.vo.AgodaHotel;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.room.vo.CommonRoom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {AgodaRequestService.class},
        properties = "classpath:application.properties")
public class AgodaHotelSearchRequestServiceTest {

    @Autowired
    private AgodaRequestService agodaRequestService;

    @Test
    public void getHotels() throws Exception {
//        HotelSearch hotelSearch = new HotelSearch();
//        hotelSearch.setCheckin(LocalDate.now());
//        hotelSearch.setCheckout(LocalDate.now().plusDays(5));
//
//        AgodaHotel agodaHotel1 = new AgodaHotel();
//        agodaHotel1.setHotelId(1L);
//        AgodaHotel agodaHotel2 = new AgodaHotel();
//        agodaHotel2.setHotelId(6L);
//
//        List<AgodaHotel> hotelList = new ArrayList<>();
//        hotelList.add(agodaHotel1);
//        hotelList.add(agodaHotel2);
//
//        AgodaHotelResult result = agodaRequestService.getHotels(hotelSearch, hotelList);
//        System.out.println(result.getHotels().get(0));
//        List<? extends CommonRoom> room = result.getRooms(1L);
//        for (CommonRoom room1 : room) {
//            System.out.println(room1.getTotalPrice());
//        }
//        assertThat(result.getStatus(), is("200"));
    }
}
