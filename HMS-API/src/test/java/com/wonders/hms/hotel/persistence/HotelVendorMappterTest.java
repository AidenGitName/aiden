package com.wonders.hms.hotel.persistence;

import com.wonders.hms.agoda.vo.AgodaAndWonderHotelIndex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HotelVendorMappterTest {

    @Autowired
    private HotelVendorMapper hotelVendorMapper;

    @Test
    public void getAgodaIds() {
        List<Long> vendorIndex = new ArrayList<>();
        vendorIndex.add(1L);
        vendorIndex.add(2L);
        vendorIndex.add(3L);
        vendorIndex.add(4L);
        vendorIndex.add(5L);

        Map<String, Object> param = new HashMap<>();
        param.put("vendorIndex", vendorIndex);

        List<AgodaAndWonderHotelIndex> agodaIds = hotelVendorMapper.getAgodaIds(param);

        System.out.println(agodaIds.size());
    }
}
