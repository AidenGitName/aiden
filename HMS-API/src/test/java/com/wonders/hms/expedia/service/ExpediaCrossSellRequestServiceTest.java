package com.wonders.hms.expedia.service;


import com.wonders.hms.expedia.vo.ExpCrosApiRoom;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.hotel.vo.Place;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpediaCrossSellRequestServiceTest {
    @Autowired
    private ExpediaCrossSellRequestService expediaCrossSellRequestService;

    @Test
    @Ignore
    public void getParam() throws Exception{
        Place place = new Place();
        place.setLatitude(40.7141667);
        place.setLongitude(-74.0063889);

        HotelSearch hotelSearch = new HotelSearch();
        hotelSearch.setCheckin(LocalDate.now().plusDays(1));
        hotelSearch.setCheckout(LocalDate.now().plusDays(2));
        hotelSearch.setPlace(place);

        ExpCrosApiRoom result = expediaCrossSellRequestService.getSpecialRooms(hotelSearch);

        System.out.println("-------------------------------");
        if (result.getExXSellHotel() != null) {
            System.out.println(result.getExXSellHotel().size());
        }
        System.out.println("-------------------------------");
    }
}
