package com.wonders.hms.util;

import com.wonders.hms.agoda.persistence.AgodaHotelInfoMapper;
import com.wonders.hms.agoda.vo.AgodaHotel;
import com.wonders.hms.expedia.vo.searchapi.ExpediaHotelInfo;
import com.wonders.hms.expedia.persistence.ExpediaHotelInfoMapper;
import com.wonders.hms.hotel.persistence.HotelVendorMapper;
import com.wonders.hms.wonder.type.HotelVendorKind;
import com.wonders.hms.wonder.vo.WonderHotel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/*
util class
1. 아고다와 익스피디아의 호텔 정보를 가져온다.
2. 동일성 체크후 hotel_vendor_index에 update
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExpediaCompareToAgodaHotelTest {

    @Autowired
    private AgodaHotelInfoMapper agodaHotelInfoMapper;

    @Autowired
    private ExpediaHotelInfoMapper expediaHotelInfoMapper;

    @Autowired
    private HotelVendorMapper hotelVendorMapper;

    @Test
    public void getAllHotel() {
        long start = System.currentTimeMillis();
        System.out.println("selecting...");

        List<AgodaHotel> agodaAllHotels = agodaHotelInfoMapper.getAllHotels();
        List<ExpediaHotelInfo> exAllHotels = expediaHotelInfoMapper.getAllHotels();

        List<WonderHotel> agodaWonderList = agodaAllHotels.stream().map(agodaHotel -> {
            WonderHotel wonderHotel = new WonderHotel();
            try {
                wonderHotel.setId(agodaHotel.getHotelId());
                wonderHotel.setName(agodaHotel.getEngName());
                wonderHotel.setAddress(agodaHotel.getAddress());
                wonderHotel.setLongitude(agodaHotel.getLongitude());
                wonderHotel.setLatitude(agodaHotel.getLatitude());
                wonderHotel.setSourceDataHotelVendorKind(HotelVendorKind.AGODA);
            }
            catch(Exception e) {
                e.printStackTrace();
                throw e;
            }
            return wonderHotel;
        }).collect(Collectors.toList());

        List<WonderHotel> expWonderList = exAllHotels.stream().map(exHotel -> {
            WonderHotel wonderHotel = new WonderHotel();
            try {
                wonderHotel.setId(exHotel.getPropertyId());
                wonderHotel.setName(exHotel.getName());
                wonderHotel.setAddress(exHotel.getAddress());
                wonderHotel.setLongitude(exHotel.getLongitude());
                wonderHotel.setLatitude(exHotel.getLatitude());
                wonderHotel.setSourceDataHotelVendorKind(HotelVendorKind.EXPEDIA);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            return wonderHotel;
        }).collect(Collectors.toList());

        AtomicInteger sameSize = new AtomicInteger();
        AtomicInteger differentSize = new AtomicInteger();

        System.out.println("comparing...");

        int totalSize = expWonderList.size();

        AtomicInteger processingCount = new AtomicInteger();

        expWonderList.parallelStream().forEach(exp-> {
            processingCount.getAndIncrement();

            agodaWonderList.parallelStream().forEach(ago-> {
                if (exp.equals(ago)) {
                    sameSize.getAndIncrement();
                    saveHotelVendorIndex(ago, exp);
                } else {
//                    System.out.println(ago.getName() + " : " + exp.getName());
                    differentSize.getAndIncrement();
                }
            });

            long innerEnd = System.currentTimeMillis();

            if (processingCount.intValue() % 1000  == 0) {
                long min = (innerEnd -start)/1000/60;

                System.out.println(processingCount.intValue() + " / " + totalSize + "(" + sameSize + ":" + (int)((sameSize.doubleValue() / processingCount.doubleValue()) * 100) + "%)" + "(" + min + "분)");
                System.out.println("Total: " + (int)(processingCount.doubleValue() / totalSize * 100) + "% (" + min/60 + " h)" );
            }

        });


        long end = System.currentTimeMillis();
        System.out.println("실행시간: " + (end - start)/1000/60 + "분");
        System.out.println("agoda size: " + agodaAllHotels.size());
        System.out.println("ex size: " + exAllHotels.size());
        System.out.println("same: " + sameSize);
        System.out.println("diff: " + differentSize);
    }

    private void saveHotelVendorIndex(WonderHotel agoda, WonderHotel expedia) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("expediaId", expedia.getId());
            map.put("agodaId", agoda.getId());

            hotelVendorMapper.setAgodaIndex(map);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

