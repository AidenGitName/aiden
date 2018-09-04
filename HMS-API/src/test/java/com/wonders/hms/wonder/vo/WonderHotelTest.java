package com.wonders.hms.wonder.vo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class WonderHotelTest {

    @Test
    public void equalTest() {
        WonderHotel wonderHotel1 = new WonderHotel();
        wonderHotel1.setName("Viengtai Hotel Bangkok");

        WonderHotel wonderHotel2 = new WonderHotel();
        wonderHotel2.setName("Viengtai Hotel Bangk");

        assertThat(wonderHotel1.equals(wonderHotel2), is(true));
    }
}
