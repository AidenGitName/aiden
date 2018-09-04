package com.wonders.hms.wonder.translateMap;

import com.wonders.hms.wonder.type.HotelAmenityKind;

import java.util.HashMap;
import java.util.Map;

public class HotelAmenityMap {
    public static final Map<String, HotelAmenityKind> enNameToAmenityKind;
    static {
        enNameToAmenityKind = new HashMap<String, HotelAmenityKind>();
        enNameToAmenityKind.put("Wireless Internet access-free", HotelAmenityKind.WIRELESS_INTERNET);
        enNameToAmenityKind.put("Dining-Breakfast", HotelAmenityKind.BREAKFAST);
        enNameToAmenityKind.put("Pool sun loungers", HotelAmenityKind.POOL_SUM_LOUNGERS);
        enNameToAmenityKind.put("Dining-Restaurant", HotelAmenityKind.RESTAURANT);
        enNameToAmenityKind.put("Dry cleaning/laundry service", HotelAmenityKind.LAUNDRY_FACILITIES);
        enNameToAmenityKind.put("Laundry Facilities", HotelAmenityKind.LAUNDRY_FACILITIES);
        enNameToAmenityKind.put("Business center", HotelAmenityKind.BUSINESS_CENTER);
        enNameToAmenityKind.put("Airport transportation", HotelAmenityKind.AIRPORT_TRANSPORTATION);
        enNameToAmenityKind.put("Massage-spa treatment room(s)", HotelAmenityKind.MASSAGE_SPA_TREATMENT);
        enNameToAmenityKind.put("Luggage storage", HotelAmenityKind.LUGGAGE_STORAGE);
        enNameToAmenityKind.put("Recreation-Fitness facilities", HotelAmenityKind.FITNESS_FACILITIES);
        enNameToAmenityKind.put("24-hour front desk", HotelAmenityKind.FRONT_DESK_24);
        enNameToAmenityKind.put("Parking - offsite (reservations)", HotelAmenityKind.PARKING);
        enNameToAmenityKind.put("Parking - self (surcharge)", HotelAmenityKind.PARKING);
        enNameToAmenityKind.put("Barbecue grill(s)", HotelAmenityKind.BARBECUE_GRILL);
        enNameToAmenityKind.put("Coffee/tea in common areas", HotelAmenityKind.COFFEE_AND_TEA_IN_COMMON_AREAS);
        enNameToAmenityKind.put("Conference space", HotelAmenityKind.CONFERENCE_SPACE);
        enNameToAmenityKind.put("Elevator/lift", HotelAmenityKind.ELEVATOR);
        enNameToAmenityKind.put("Front desk (limited hours)", HotelAmenityKind.FRONT_DESK_LIMITED_HOURS);
        enNameToAmenityKind.put("Meeting rooms", HotelAmenityKind.MEETING_ROOMS);
        enNameToAmenityKind.put("Multilingual staff", HotelAmenityKind.MULTILIGUAL_STAFF);
        enNameToAmenityKind.put("Smoke-free property", HotelAmenityKind.SMOKE_FREE_PROPERTY);
        enNameToAmenityKind.put("Tours/ticket assistance", HotelAmenityKind.TOURS_TICKET_ASSISTANCE);
        enNameToAmenityKind.put("Wired Internet-free", HotelAmenityKind.WIRED_INTERNET_FREE);
        enNameToAmenityKind.put("Wired Internet-surcharge", HotelAmenityKind.WIRED_INTERNET_SURCHARAGE);
    }

    public static final Map<HotelAmenityKind, String> amenityKindToKoName;
    static {
        amenityKindToKoName = new HashMap<HotelAmenityKind, String>();
        amenityKindToKoName.put(HotelAmenityKind.WIRELESS_INTERNET, "와이파이");
        amenityKindToKoName.put(HotelAmenityKind.BREAKFAST, "조식");
        amenityKindToKoName.put(HotelAmenityKind.POOL_SUM_LOUNGERS, "수영장");
        amenityKindToKoName.put(HotelAmenityKind.RESTAURANT, "레스토랑");
        amenityKindToKoName.put(HotelAmenityKind.LAUNDRY_FACILITIES, "세탁");
        amenityKindToKoName.put(HotelAmenityKind.BUSINESS_CENTER, "비즈니스 센터");
        amenityKindToKoName.put(HotelAmenityKind.AIRPORT_TRANSPORTATION, "공항셔틀");
        amenityKindToKoName.put(HotelAmenityKind.MASSAGE_SPA_TREATMENT, "스파");
        amenityKindToKoName.put(HotelAmenityKind.LUGGAGE_STORAGE, "수화물 보관소");
        amenityKindToKoName.put(HotelAmenityKind.FITNESS_FACILITIES, "피트니스 센터");
        amenityKindToKoName.put(HotelAmenityKind.FRONT_DESK_24, "24시 프론트");
        amenityKindToKoName.put(HotelAmenityKind.PARKING, "주차");
        amenityKindToKoName.put(HotelAmenityKind.BARBECUE_GRILL, "바베큐 시설");
        amenityKindToKoName.put(HotelAmenityKind.COFFEE_AND_TEA_IN_COMMON_AREAS, "카페");
        amenityKindToKoName.put(HotelAmenityKind.CONFERENCE_SPACE, "회의시설");
        amenityKindToKoName.put(HotelAmenityKind.ELEVATOR, "엘레베이터");
        amenityKindToKoName.put(HotelAmenityKind.FRONT_DESK_LIMITED_HOURS, "프론트 데스크(제한된 시간)");
        amenityKindToKoName.put(HotelAmenityKind.MEETING_ROOMS, "미팅 룸");
        amenityKindToKoName.put(HotelAmenityKind.MULTILIGUAL_STAFF, "다국어 스태프");
        amenityKindToKoName.put(HotelAmenityKind.SMOKE_FREE_PROPERTY, "흡연 가능");
        amenityKindToKoName.put(HotelAmenityKind.TOURS_TICKET_ASSISTANCE, "투어 및 티켓");
        amenityKindToKoName.put(HotelAmenityKind.WIRED_INTERNET_FREE, "유선 인터넷 무료");
        amenityKindToKoName.put(HotelAmenityKind.WIRED_INTERNET_SURCHARAGE, "유선 인터넷 유료");
    }
}
