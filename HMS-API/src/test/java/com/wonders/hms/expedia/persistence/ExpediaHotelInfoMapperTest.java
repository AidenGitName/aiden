package com.wonders.hms.expedia.persistence;

import com.wonders.hms.expedia.vo.staticInfo.ExCoordinate;
import com.wonders.hms.expedia.vo.staticInfo.ExLacation;
import com.wonders.hms.expedia.vo.staticInfo.ExpediaHotelInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpediaHotelInfoMapperTest {
    @Autowired
    ExpediaHotelInfoMapper expediaHotelInfoMapper;


    @Test
    @Transactional
    public void updateStaticInfo() {
        ExCoordinate exCoordinate = new ExCoordinate();
        exCoordinate.setLatitude(12345.1);
        exCoordinate.setLongitude(2345.1);

        ExLacation location = new ExLacation();
        location.setCoordinates(exCoordinate);

        ExpediaHotelInfo expediaHotelInfo = new ExpediaHotelInfo();
        expediaHotelInfo.setLocation(location);
        expediaHotelInfo.setPropertyId("26812282");

        int result = expediaHotelInfoMapper.updateStaticInfo(expediaHotelInfo);
        System.out.println(result);
    }

    @Test
    @Transactional
    public void insertStaticInfo() {
        ExCoordinate exCoordinate = new ExCoordinate();
        exCoordinate.setLatitude(12345.1);
        exCoordinate.setLongitude(2345.1);

        ExLacation location = new ExLacation();
        location.setCoordinates(exCoordinate);

        ExpediaHotelInfo expediaHotelInfo = new ExpediaHotelInfo();
        expediaHotelInfo.setPropertyId("12345");
        expediaHotelInfo.setName("wonder hotel");
        expediaHotelInfo.setLocation(location);

        expediaHotelInfoMapper.insertNewStaticInfo(expediaHotelInfo);
    }
}
