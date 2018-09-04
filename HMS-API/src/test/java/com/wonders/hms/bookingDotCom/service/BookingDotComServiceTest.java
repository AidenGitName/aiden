package com.wonders.hms.bookingDotCom.service;

import com.wonders.hms.bookingDotCom.changedHotel.domain.ChangedHotelsRS;
import com.wonders.hms.bookingDotCom.hotelAvailability.domain.AutocompleteRS;
import com.wonders.hms.bookingDotCom.hotel.domain.HotelsRS;
import com.wonders.hms.bookingDotCom.vo.AutocompleteVO;
import com.wonders.hms.bookingDotCom.vo.ChangedHotelsVO;
import com.wonders.hms.bookingDotCom.vo.HotelsVO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingDotComServiceTest {


    @Autowired
    BookingDotComService bookingDotComService;


    @Test
    public void autocompleteServiceTest() throws Exception {
        AutocompleteVO autocompleteVO = new AutocompleteVO();

        autocompleteVO.setText(URLEncoder.encode("Sandals Royal Caribbean Resort & Private Island - Couples Only", "UTF-8").replaceAll("\\+", "%20"));
        autocompleteVO.setLanguage("en");

        AutocompleteRS autocompleteRS = this.bookingDotComService.autocomplete(autocompleteVO);

        System.out.println(autocompleteRS);
    }

    @Test
    public void sillaHotelInformationCallTest() throws Exception{
        HotelsVO hotelsVO = new HotelsVO();

        ArrayList<Long> hotelIds = new ArrayList();
        hotelIds.add(new Long(254475));

        hotelsVO.setHotelIds(hotelIds);

        HotelsRS hotelsRS = this.bookingDotComService.hotels(hotelsVO);

        System.out.println(hotelsRS);
    }

    @Test
    @Ignore
    public void changedHotelsServiceTest() throws Exception {
        ChangedHotelsVO changedHotelsVO = new ChangedHotelsVO();

        ArrayList<String> countries = new ArrayList();
        countries.add("kr");

        changedHotelsVO.setCountries(countries);
        changedHotelsVO.setLastChange(LocalDateTime.of(2018, 7, 13, 12, 0, 0));

        ChangedHotelsRS changedHotelsRS = this.bookingDotComService.changedHotels(changedHotelsVO);

        assertThat(changedHotelsRS.getResult().getClosedHotels()).isInstanceOf(ArrayList.class);
    }
}
