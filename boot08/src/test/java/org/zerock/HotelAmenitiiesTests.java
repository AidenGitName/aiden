package org.zerock;

import com.sun.deploy.xml.XMLParser;
import com.sun.javaws.jnl.XMLFormat;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.controller.HotelAmenityXmlParser;
import org.zerock.domain.HotelInfo;
import org.zerock.persistence.HotelRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class HotelAmenitiiesTests {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    HotelAmenityXmlParser hotelAmenityXmlParser;

    @Test
    public void hotelAmenitiiesTest(){
        hotelAmenityXmlParser.setHotelAmenity();


    }
    @Test
    public void selectAll(){
        List<Integer> hotelIds = hotelRepository.getAllbyHotelIds();
        log.info("Size : " + hotelIds.size());


    }
    @Test
    public void testUrl(){
        String url = "http://xml.agoda.com/datafeeds/Feed.asmx/GetFeed?feed_id=14&apikey=8ba7c83a-72ab-4586-b806-1827f95ed4a6&mhotel_id=12133";
        hotelAmenityXmlParser.getXmlList(url);

    }

}
