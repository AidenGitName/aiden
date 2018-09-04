package com.wonders.hms.hotel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.agoda.service.AgodaHotelVendor;
import com.wonders.hms.bookingDotCom.BookingDotComHotel;
import com.wonders.hms.bookingDotCom.hotelAvailability.domain.HotelAvailabilityRS;
import com.wonders.hms.bookingDotCom.hotelAvailability.vo.Result;
import com.wonders.hms.hotel.vo.CommonHotel;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.hotel.vo.Place;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TotalCommonHotelSearcherTest {

    @Autowired
    TotalHotelSearcher totalHotelSearcher;

    @Autowired
    AgodaHotelVendor agodaHotelVendor;

    private HotelSearch hotelSearch = new HotelSearch();

    private void seoulHotelSearchInit(){
        Place seoulLatitudeLongitude = new Place();
        seoulLatitudeLongitude.setLatitude(37.5618924566236);
        seoulLatitudeLongitude.setLongitude(126.981161006443);

        hotelSearch.setPlace(seoulLatitudeLongitude);
        hotelSearch.setCheckin(LocalDate.of(2018, 9, 27));
        hotelSearch.setCheckout(LocalDate.of(2018, 9, 29));

        hotelSearch.setNumberOfAdults(2);
        hotelSearch.setNumberOfRooms(1);
    }

    private void mactanHotelSearchInit(){
        Place mactanLatitudeLongitude = new Place();
        mactanLatitudeLongitude.setLatitude(10.3000001907349);
        mactanLatitudeLongitude.setLongitude((double)124);

        hotelSearch.setPlace(mactanLatitudeLongitude);
        hotelSearch.setCheckin(LocalDate.of(2018, 9, 27));
        hotelSearch.setCheckout(LocalDate.of(2018, 9, 29));

        hotelSearch.setNumberOfAdults(2);
        hotelSearch.setNumberOfRooms(1);
    }

    private void initTotalSearcherWithoutBookingApi() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        HotelAvailabilityRS hotelAvailabilityRS =
                objectMapper.readValue(new FileReader("/Users/we/HMS-API/src/test/java/com/wonders/hms/service/responseBody.json"), HotelAvailabilityRS.class);

        ArrayList<Result> results = hotelAvailabilityRS.getResult();
        ArrayList<CommonHotel> commonHotels = new ArrayList<>();

        for(Result result : results) {
            commonHotels.add(new BookingDotComHotel(result));
        }

        seoulHotelSearchInit();
        commonHotels.addAll(agodaHotelVendor.getHotels(hotelSearch));

    }

    @Test
    @Ignore
    public void readHotelSeoulLive() throws Exception{
        seoulHotelSearchInit();
        totalHotelSearcher.search(hotelSearch);
        long startTime = System.currentTimeMillis();

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
    }

    @Test
    @Ignore
    public void readHotelMactan() throws Exception{
        mactanHotelSearchInit();
        totalHotelSearcher.search(hotelSearch);
        long startTime = System.currentTimeMillis();

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
    }
}
