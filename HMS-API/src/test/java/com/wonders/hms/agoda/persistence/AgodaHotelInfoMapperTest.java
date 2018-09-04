package com.wonders.hms.agoda.persistence;

import com.wonders.hms.agoda.vo.AgodaHotel;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.hotel.vo.Place;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AgodaHotelInfoMapperTest {
    @Autowired
    private AgodaHotelInfoMapper agodaHotelInfoMapper;

    @Test
    @Ignore
    public void getHotel() {
        Place place = new Place();
        place.setLatitude(12.568135);
        place.setLongitude(101.466979);

        HotelSearch hotelSearch = new HotelSearch();
        hotelSearch.setPlace(place);

        List<AgodaHotel> list = agodaHotelInfoMapper.getHotels(hotelSearch);
        System.out.println("size: " + list.get(0));
//        System.out.println("size: " + list.size());

    }

}
