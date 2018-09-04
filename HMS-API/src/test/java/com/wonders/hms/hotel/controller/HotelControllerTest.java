package com.wonders.hms.hotel.controller;

import com.wonders.hms.config.URIMapping;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelControllerTest {

    @Autowired
    protected WebApplicationContext wac;


    protected MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getHotelList() throws Exception {
        String testUrl = URIMapping.BASE_URI
                + "/hotel?checkin=2018-08-01&checkout=2018-08-10&numberOfAdults=1&numberOfRooms=1&place.latitude=13.7558002471924&place.longitude=100.505996704102";

        mockMvc.perform(MockMvcRequestBuilders.get(testUrl))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
