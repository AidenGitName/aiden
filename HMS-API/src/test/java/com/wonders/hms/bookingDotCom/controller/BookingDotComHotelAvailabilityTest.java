package com.wonders.hms.bookingDotCom.controller;

import com.wonders.hms.autocomplete.controller.BookingDotCom;
import com.wonders.hms.bookingDotCom.service.BookingDotComService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingDotCom.class)
public class BookingDotComHotelAvailabilityTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private BookingDotComService bookingDotComService;

    @Test
    public void hotelAvailabilityTest()  throws Exception {
//        checkin=2018-09-24&checkout=2018-09-25&city_ids=-1565670&room1=A,A&extras=room_details,hotel_details
        mvc.perform(get("/booking-com/hotel-availability")
                .param("checkin", "2018-09-25")
                .param("checkout", "2018-09-27")
                .param("cityIds", "-1565670")
                .param("room1", "A,A")
                .param("extras", "room_policies,payment_terms,room_amenities,hotel_details,hotel_amenities,room_details")
        ).andDo(print());
    }
}
