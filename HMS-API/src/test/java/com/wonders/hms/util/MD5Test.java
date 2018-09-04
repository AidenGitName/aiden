package com.wonders.hms.util;

import org.junit.Test;

public class MD5Test {
    @Test
    public void md5Test(){
        String test1 = "place.placeName=%ED%98%B8%ED%85%94+%ED%8E%98%EC%9D%B4%ED%86%A0+%EC%82%BC%EC%84%B1&place.placeType=hotel&place.placeId=1502223&place.latitude=37.5083963081286&place.longitude=127.058829367161&place.countryName=%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD&place.countryCode=kr&place.cityName=%EC%84%9C%EC%9A%B8&place.cityId=-716583&checkin=2018-11-01&checkout=2018-11-02&numberOfAdults=3&numberOfRooms=1&numberOfChildren=0&agesOfChildren=&hotelSortTypeKind=BEST&row=0&mid=8951642";
        String test2 = "place.placeName=%ED%98%B8%ED%85%94+%ED%8E%98%EC%9D%B4%ED%86%A0+%EC%82%BC%EC%84%B1&place.placeType=hotel&place.placeId=1502223&place.latitude=37.5083963081286&place.longitude=127.058829367161&place.countryName=%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD&place.countryCode=kr&place.cityName=%EC%84%9C%EC%9A%B8&place.cityId=-716583&checkin=2018-11-01&checkout=2018-11-02&numberOfAdults=3&numberOfRooms=1&numberOfChildren=0&agesOfChildren=&hotelSortTypeKind=BEST&row=0&mid=8951642";

        String md5Result = MD5.getMD5Str(test1);
        System.out.println(md5Result);
        assert md5Result.equals(MD5.getMD5Str(test2));
    }
}
