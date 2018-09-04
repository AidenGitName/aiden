package com.wonders.hms.expedia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.expedia.vo.staticInfo.ExAmenity;
import com.wonders.hms.expedia.vo.staticInfo.ExAmenityList;
import com.wonders.hms.expedia.vo.staticInfo.ExpediaHotelInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpediaHotelInfoUpdateServiceTest {

    @Autowired
    ExpediaHotelInfoUpdateService expediaHotelInfoUpdateService;

    @Test
    @Ignore
    public void checkExistAndUpdateOrInsertTest() throws Exception{
        String readFileName = "/Users/we/Expedia_Static_Hotel_Content_1.zipjson";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(readFileName));
        String hotelJsonString;
        ObjectMapper objectMapper = new ObjectMapper();

        while ((hotelJsonString = bufferedReader.readLine()) != null) {
            ExpediaHotelInfo expediaHotelInfo = objectMapper.readValue(hotelJsonString, ExpediaHotelInfo.class);
            // test시 작업 필요....
//            expediaHotelInfoUpdateService.checkExistAndUpdateOrInsert(expediaHotelInfo);
            break;
        }
        bufferedReader.close();
    }
}
