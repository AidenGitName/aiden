package com.wonders.hms.hotel.service;

import com.wonders.hms.util.Levenshtein;
import com.wonders.hms.util.LocationDistance;
import com.wonders.hms.util.LocationDistanceUnit;
import com.wonders.hms.util.StringTypeCheck;
import com.wonders.hms.wonder.vo.WonderHotel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelNameComparer {

    private static final LocationDistanceUnit DEFAULT_DISTANCE_UNIT = LocationDistanceUnit.METER;
    private static final double MIN_LEVENSHTEIN_RATE = 0.9;
    private static final double DISTANCE_STANDARD = 20.0;
    private static final double THE_SAME_HOTEL_DISTINCTION_DISTANCE = 400;


    public boolean isSame(WonderHotel hotel, WonderHotel otherHotel) {
        try {
            if (hotel.getSourceDataHotelVendorKind() == otherHotel.getSourceDataHotelVendorKind()) {
                return false;
            }

            if (getDistance(hotel, otherHotel) > THE_SAME_HOTEL_DISTINCTION_DISTANCE) {
                return false;
            }

            String hotelName = getFilteredName(hotel.getName());
            String otherHotelName = getFilteredName(otherHotel.getName());

            if (hotelName.equals(otherHotelName)) {
                return true;
            }

            if (isSimilarHotelName(hotelName, otherHotelName)) {
                return true;
            }

            // 거리가 5m 이하고 주소가 같으면
            if (isSimilarDistance(hotel, otherHotel) && isDetailAddressSame(hotel.getAddress(), otherHotel.getAddress())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(hotel);
            System.out.println(otherHotel);
            return false;
        }

        return false;
    }

    private double getDistance(WonderHotel hotel, WonderHotel otherHotel) {
        return LocationDistance.distance(
                hotel.getLatitude(),
                hotel.getLongitude(),
                otherHotel.getLatitude(),
                otherHotel.getLongitude(),
                DEFAULT_DISTANCE_UNIT);
    }

    private boolean isSimilarDistance(WonderHotel hotel, WonderHotel otherCommonHotel) {
        return getDistance(hotel, otherCommonHotel) < DISTANCE_STANDARD;
    }

    private boolean isDetailAddressSame(String address, String otherAddress) {
        // 주소 중 가장 디테일한 부분만 가져온다.
        String addressFirstZone = address.split(",")[0];
        String otherHotelAddressFirstZone = otherAddress.split(",")[0];

        // 그 중 지번지(순수 숫자)만 가져와서 비교
        Pattern pattern = Pattern.compile("(\\s+[\\d]+$|^[\\d]+$|^[\\d]+\\s+|\\s+[\\d]+\\s+)");
        Matcher matcher1 = pattern.matcher(addressFirstZone);
        Matcher matcher2 = pattern.matcher(otherHotelAddressFirstZone);

        if (matcher1.find() && matcher2.find()) {
            return matcher1.group(1).trim().equals(matcher2.group(1).trim());
        }

        return false;
    }

    private String getFilteredName(String hotelName) {
        // hotel 이름 중 1 번째 체인을 I로 쓰는 케이스가 있어서 마지막 I -> 1로 통일
        // , \\s -   등 뜻이 없는 문자 제거
        // & N => 엔 으로 변경
        // 호텔 게스트하우스 등 일관된 뜻의 단어 제거
        return hotelName.trim()
                .replaceAll("\\s|,|-", "")
                .replaceAll("호텔", "")
                .replaceAll("게스트하우스", "")
                .replaceAll("에", "애")
                .replaceAll("&", "엔")
                .replaceAll("N", "엔")
                .replaceAll("I$", "1");
    }

    public boolean isSimilarHotelName(String hotelName, String otherHotelName) {
        String hotelNameLastString = hotelName.substring(hotelName.length() - 1);
        String otherHotelNameLastString = otherHotelName.substring(otherHotelName.length() - 1);

        if (Levenshtein.rateOfStringMatch(hotelName, otherHotelName) < MIN_LEVENSHTEIN_RATE) {
            return false;
        }
        if (StringTypeCheck.isStringDouble(hotelNameLastString) && !hotelNameLastString.equals(otherHotelNameLastString)) {
            return false;
        }

        return true;
    }
}
