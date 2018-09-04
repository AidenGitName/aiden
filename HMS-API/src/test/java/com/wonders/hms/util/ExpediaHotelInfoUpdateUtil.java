package com.wonders.hms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.expedia.vo.searchapi.*;
import com.wonders.hms.expedia.persistence.ExpediaHotelInfoMapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

import java.util.*;
import java.util.stream.Collectors;

/*
expedia hotel search api 결과로 호출된 값 중 static 데이터에 없는 데이터를 추가하는 유틸
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExpediaHotelInfoUpdateUtil {

    @Value("${expedia.account.search.key}")
    private String key;

    @Value("${expedia.account.search.password}")
    private String password;

    @Value("${expedia.account.search.url.hotellist}")
    private String apiUrl;

    private String authenticationString;

    private String auth;

    private int apiCallCount = 0;

    @Autowired
    private ExpediaHotelInfoMapper expediaHotelInfoMapper;


    @Before
    public void setup() {
        authenticationString = key + ":" + password;
        auth =  "Basic " + Base64Utils.encodeToString(authenticationString.getBytes());
    }


    @Test
    public void getHotelName() throws Exception {
        System.out.println("start!");

        System.out.println("selecting...");
        List<ExpediaHotelInfo> hotelInfoList = expediaHotelInfoMapper.getAllHotels();
        List<Long> idList = hotelInfoList.stream().map(ExpediaHotelInfo::getPropertyId).collect(Collectors.toList());

        int totalSize = idList.size();
        int restSize = idList.size();
        int callSize = 900;

        System.out.println("updating...total: " + totalSize);

        List<Long> searchIds = new ArrayList<>();

       for (int i = 0; i < totalSize; i++) {
           if (idList.get(i) <= 23964247) {
               restSize--;
               continue;
           }

           searchIds.add(idList.get(i));

           if (searchIds.size() % callSize == 0) {
               callHotelInfoApi(searchIds);
               restSize -= searchIds.size();
               searchIds = new ArrayList<>();
               System.out.println("rest size: " + restSize);
           }

           if (restSize < callSize && restSize > 0) {
               searchIds = idList.subList(i, totalSize);
               callHotelInfoApi(searchIds);
               break;
           }

       }
    }

    private void callHotelInfoApi(List<Long> searchIds) throws Exception {
        apiCallCount++;
        System.out.println("api call count: " + apiCallCount);

//        if (apiCallCount % 150 == 0) {
//            Thread.sleep(1000 * 60 * 65);
//        }

        List<String> strList = searchIds.stream().map(Object::toString).collect(Collectors.toList());
        String commaSeparatedHotelId = String.join(",", strList);

        String requestUrl = apiUrl + "?locale=ko_KR&currency=KRW&ecomHotelIds=" + commaSeparatedHotelId;

        System.out.println(requestUrl);


        HttpUriRequest request = RequestBuilder.get()
                .setUri(requestUrl)
                .setHeader("Key", key)
                .setHeader("Authorization", auth)
                .setHeader("Partner-Transaction-ID", "BestTravel-12345678-987654321")
                .setHeader("Accept", "application/vnd.exp-hotel.v3+json")
                .setHeader("Content-type", "application/json; charset=UTF-8")
                .build()
                ;

        HttpRequest httpRequest = new HttpRequest();
        String body = httpRequest.send(request);

        ObjectMapper objectMapper = new ObjectMapper();
        ExHotelSearchApiResponse exHotelSearchApiResponse = objectMapper.readValue(body, ExHotelSearchApiResponse.class);

        List<ExApiHotel> searchedHotelList = exHotelSearchApiResponse.getHotels();
        searchedHotelList.stream().forEach(exApiHotel -> {
//            updateHotelAmenityInfo(exApiHotel);
            updateHotelInfo(exApiHotel);
        });



        System.out.println("last index: " + searchIds.get(searchIds.size() - 1));
    }

    private void updateHotelInfo(ExApiHotel exApiHotel) {
        Map<String, Object> param = new HashMap<>();
        param.put("star_rating", exApiHotel.getStarRating());
        param.put("address1", exApiHotel.getLocation().getAddress().getAddress1());
        param.put("address2", exApiHotel.getLocation().getAddress().getAddress2());
        param.put("province", exApiHotel.getLocation().getAddress().getProvince());
        param.put("city", exApiHotel.getLocation().getAddress().getCity());
        try { param.put("hotel_teaser", exApiHotel.getDescription().getHotelTeaser());  } catch (NullPointerException e) {param.put("hotelTeaser", null);}
        try {param.put("room_teaser", exApiHotel.getDescription().getRoomTeaser()); } catch (NullPointerException e) {param.put("roomTeaser", null);}
        try {param.put("location_teaser", exApiHotel.getDescription().getLocationTeaser()); } catch (NullPointerException e) {param.put("locationTeaser", null);}
        param.put("propertyId", exApiHotel.getPropertyId());
        param.put("name", exApiHotel.getName());
        param.put("guest_rating", exApiHotel.getGuestRating());
        param.put("guest_review_count", exApiHotel.getGuestReviewCount());
        try {param.put("distance", exApiHotel.getDistance().getDirection() + " " + exApiHotel.getDistance().getValue() + exApiHotel.getDistance().getUnit());} catch (NullPointerException e) {param.put("distance", null);}

        expediaHotelInfoMapper.updateHotelInfoFromApi(param);

    }


    @Test
    @Ignore
    public void commaSeparated() {
        List<Long> longList = Arrays.asList(1L, 2L, 3L ,4L );

        List<String> strList = longList.stream().map(Object::toString).collect(Collectors.toList());

        System.out.println(String.join(",", strList));

    }
}
