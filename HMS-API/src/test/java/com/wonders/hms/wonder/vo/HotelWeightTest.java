package com.wonders.hms.wonder.vo;

import org.junit.Test;

public class HotelWeightTest {

    private HotelWeight hotelWeight = new HotelWeight();

    @Test
    public void hotelWeight() {
        Double score = hotelWeight.computeWeight(5.0D, 685, 8.0D);
        System.out.println(score);
        assert score == 8.4;
    }

    @Test
    public void hotelWeight2() {
        Double score = hotelWeight.computeWeight(3.5D, 324, 5.4D);

        System.out.println(score);
        assert score == 5.8;
    }
}
