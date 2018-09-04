package com.wonders.hms.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JaccardTest {

    @Test
    public void jaccardTest() {

        String agodaAddress = "Via Nicola Salvi 68";
        String bookingAddress = " Via Nicola Salvi 68, 리오네 몬티, 로마";

        double rate = Jaccard.similarity(agodaAddress, bookingAddress);

        System.out.println(rate);
    }
}
