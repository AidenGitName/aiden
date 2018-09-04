package com.wonders.hms.etc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.expedia.vo.staticInfo.ExpediaHotelInfo;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JavaTest {

    @Test
    @Ignore
    public void division() {
        assertThat(3/2, is(1));
        assertThat(4/2, is(2));
    }

    @Test
    @Ignore
    public void day() {
        int diff = 5;
        LocalDate today = LocalDate.now();
        LocalDate targetDay = today.plusDays(diff);

        Period period = today.until(targetDay);

        assertThat(period.getDays(), is(diff));
    }

    @Test
    @Ignore
    public void readZipFile() throws Exception {
        ZipFile zipFile = new ZipFile("/Users/we/Documents/expedia/hotel db/Expedia_Static_Hotel_Content.zip");

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            InputStream stream = zipFile.getInputStream(entry);

            BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

            ObjectMapper objectMapper = new ObjectMapper();

            String hotel = null;
            while ((hotel = in.readLine()) != null) {
                ExpediaHotelInfo expediaHotelInfo = objectMapper.readValue(hotel, ExpediaHotelInfo.class);

            }


        }
    }

    @Test
    @Ignore
    public void BigDecimalTest() {
        String value = "₩123,231";
        BigDecimal bigDecimal = new BigDecimal(value.replaceAll("₩", ""));

        System.out.println(bigDecimal);
    }
}
