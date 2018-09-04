package com.wonders.hms.place.service;

import com.wonders.hms.place.google.service.GooglePlaceInfoCache;
import com.wonders.hms.place.google.service.NearbySearchService;
import com.wonders.hms.place.google.vo.NearbySearchVO;
import com.wonders.hms.place.google.vo.NearbyTouristAttraction;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GooglePlaceInfoDBServiceTest {
    @Autowired
    NearbySearchService nearbySearchService;

    @Autowired
    GooglePlaceInfoCache googlePlaceInfoCache;

    @Test
    public void test1SetGooglePlace() throws Exception{
        String location = "35.159425,129.1593063";

        NearbySearchVO nearbySearchVO = new NearbySearchVO();
        nearbySearchVO.setLocation(location);

        List<NearbyTouristAttraction> nearbyTouristAttractions = nearbySearchService.nearbySearch(nearbySearchVO);

        googlePlaceInfoCache.set(location, nearbyTouristAttractions);

        System.out.println(googlePlaceInfoCache.get(location));
    }

    @Test
    @Ignore
    public void test2DeleteGooglePlace() throws IOException {
        String location = "35.159425,129.1593063";

        googlePlaceInfoCache.set(location);

        System.out.println(googlePlaceInfoCache.get(location));
    }
}
