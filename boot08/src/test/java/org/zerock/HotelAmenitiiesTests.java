package org.zerock;

import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.HotelInfo;
import org.zerock.persistence.HotelRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class HotelAmenitiiesTests {
    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void hotelAmenitiiesTest(){

    }

    @Test
    public void getHotel(){
        List<Integer> hotelIds = hotelRepository.getAllByHotelId();

        log.info("hotel Size : " + hotelIds.size());
    }
}
