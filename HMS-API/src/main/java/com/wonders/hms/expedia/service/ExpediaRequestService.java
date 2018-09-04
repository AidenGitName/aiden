package com.wonders.hms.expedia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.expedia.persistence.ExpediaHotelInfoMapper;
import com.wonders.hms.expedia.vo.searchapi.ExApiBooking;
import com.wonders.hms.expedia.vo.searchapi.ExApiHotel;
import com.wonders.hms.expedia.vo.searchapi.ExApiRoomType;
import com.wonders.hms.expedia.vo.searchapi.ExHotelSearchApiResponse;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.util.HttpRequest;
import com.wonders.hms.util.vo.api.agent.Expedia;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpediaRequestService {

    private String key;

    private String password;

    @Value("${expedia.account.search.url.hotellist}")
    private String hotelListApiUrl;

    @Value("${expedia.account.search.url.booking}")
    private String hotelBookingApiUrl;

    @Autowired
    private ExpediaHotelInfoMapper expediaHotelInfoMapper;

    private String authenticationString;

    private String auth;

    @Autowired
    private Expedia expediaProp;

    @PostConstruct
    public void init() {

        this.key = expediaProp.getKey();
        this.password = expediaProp.getPassword();

        authenticationString = key + ":" + password;
        auth =  "Basic " + Base64Utils.encodeToString(authenticationString.getBytes());
    }


    public List<ExHotelSearchApiResponse> getRoom(List<Long> expediaIds, HotelSearch hotelSearch) throws Exception {

        int totalSize = expediaIds.size();
        int restSize = expediaIds.size();
        int onceCallSize = 900;

        List<ExHotelSearchApiResponse> result = new ArrayList<>();
        List<Long> searchIds = new ArrayList<>();

        for (int i = 0; i < totalSize; i++) {
            searchIds.add(expediaIds.get(i));

            if (searchIds.size() % onceCallSize == 0) {
                result.add(callApi(searchIds, hotelSearch));
                restSize -= searchIds.size();
                searchIds = new ArrayList<>();
            }
            if (restSize < onceCallSize && restSize > 0) {
                searchIds = expediaIds.subList(i , totalSize);
                result.add(callApi(searchIds, hotelSearch));
                break;
            }
        }

        return result;
    }

    public List<ExApiHotel> callHotelInfoApi(List<Long> searchIds) throws IOException {

        List<String> strList = searchIds.stream().map(Object::toString).collect(Collectors.toList());
        String commaSeparatedHotelId = String.join(",", strList);

        String requestUrl = hotelListApiUrl + "?locale=ko_KR&currency=KRW&ecomHotelIds=" + commaSeparatedHotelId;

        HttpUriRequest request = RequestBuilder.get()
                .setUri(requestUrl)
                .setHeader("Key", key)
                .setHeader("Authorization", auth)
                .setHeader("Partner-Transaction-ID", "wmp")
                .setHeader("Accept", "application/vnd.exp-hotel.v3+json")
                .setHeader("Content-type", "application/json; charset=UTF-8")
                .build();

        HttpRequest httpRequest = new HttpRequest();
        String body = httpRequest.send(request);

        ObjectMapper objectMapper = new ObjectMapper();
        ExHotelSearchApiResponse exHotelSearchApiResponse = objectMapper.readValue(body, ExHotelSearchApiResponse.class);

        List<ExApiHotel> searchedHotelList = exHotelSearchApiResponse.getHotels();

        return searchedHotelList;
    }

    private ExHotelSearchApiResponse callApi(List<Long> searchIds, HotelSearch hotelSearch) throws Exception {

        List<String> strList = searchIds.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        String commaSeparatedHotelId = String.join(",", strList);
        String checkIn = hotelSearch.getCheckin().toString();
        String checkOut = hotelSearch.getCheckout().toString();
        String locale = "ko_KR";
        String currency = "KRW";
        String contentDetails = "low";
        String link = "WEB";
        String availOnly = "true";
        String allRoomTypes = "true";


        String requestUrl = hotelListApiUrl + "?"
        + "locale=" + locale
        + "&currency=" + currency
        + "&checkIn=" + checkIn
        + "&checkOut=" + checkOut
        + buildRoomQuery(hotelSearch)
        + "&contentDetails=" + contentDetails
        + "&ecomHotelIds=" + commaSeparatedHotelId
        + "&availOnly=" + availOnly
        + "&allRoomTypes=" + allRoomTypes
        + "&links=" + link;

        HttpUriRequest request = RequestBuilder.get()
                .setUri(requestUrl)
                .setHeader("Key", key)
                .setHeader("Authorization", auth)
                .setHeader("Partner-Transaction-ID", "wmp")
                .setHeader("Accept", "application/vnd.exp-hotel.v3+json")
                .setHeader("Content-type", "application/json; charset=UTF-8")
                .build()
                ;

        HttpRequest httpRequest = new HttpRequest();
        String body = null;
        try {
            body = httpRequest.send(request);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println(requestUrl);
            throw e;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(body, ExHotelSearchApiResponse.class);
    }

    private String buildRoomQuery(HotelSearch hotelSearch) {
        int roomSize = hotelSearch.getNumberOfRooms();

        int numberOfAdultPerRoom = hotelSearch.getNumberOfAdults()  / roomSize;
        int restOfAdult = hotelSearch.getNumberOfAdults() % roomSize;

        String roomParam = "";
        for(int i = 0; i < roomSize; i++) {
            if (i == 0 ) {
                roomParam += "&room" + (i + 1) + ".adults=" + (numberOfAdultPerRoom + restOfAdult);
            } else {
                roomParam += "&room" + (i + 1) + ".adults=" + numberOfAdultPerRoom;
            }
        }

        if (hotelSearch.getNumberOfChildren() > 0
                && hotelSearch.getAgesOfChildren() != null
                && hotelSearch.getAgesOfChildren().size() != 0) {
           int numberOfChildrenPerRoom = hotelSearch.getNumberOfChildren() / roomSize;
           int restOfChildren = hotelSearch.getNumberOfChildren() % roomSize;
           int firstRoomChildrenNumber =  numberOfChildrenPerRoom + restOfChildren;
           int childIndex = 0;

           for(int i = 0; i < roomSize; i++) {
               roomParam += "&room" + (i + 1) + ".childAges=";
               if (i == 0 ) {
                   for (int j = 0; j < firstRoomChildrenNumber; j++) {
                    roomParam += hotelSearch.getAgesOfChildren().get(childIndex++) + ",";
                   }
               } else {
                   for (int j = 0; j < numberOfChildrenPerRoom; j++) {
                       roomParam += hotelSearch.getAgesOfChildren().get(childIndex++) + ",";
                   }
               }
               roomParam = removeLastComma(roomParam);
           }
        }

        return roomParam;
    }

    private String removeLastComma(String str) {
            if (str.length() > 0 && str.charAt(str.length()-1)==',') {
                str = str.substring(0, str.length()-1);
            }
            return str;
    }


    public ExApiBooking retrieveBooking(String itineraryNumber) throws IOException {
        String requestUrl = hotelBookingApiUrl + "/" + itineraryNumber;

        HttpUriRequest request = RequestBuilder.get()
                .setUri(requestUrl)
                .setHeader("Key", key)
                .setHeader("Authorization", auth)
                .setHeader("Partner-Transaction-ID", "wmp")
                .setHeader("Accept", "application/vnd.exp-hotel.v3+json")
                .setHeader("Content-type", "application/json; charset=UTF-8")
                .build()
                ;

        HttpRequest httpRequest = new HttpRequest();
        String body = httpRequest.send(request);

        ObjectMapper objectMapper = new ObjectMapper();
        ExApiBooking exApiBooking = objectMapper.readValue(body, ExApiBooking.class);


        return exApiBooking;
    }

    public void updateMinPrice() throws Exception {
        updateMinPrice(DayOfWeek.MONDAY);
        updateMinPrice(DayOfWeek.TUESDAY);
        updateMinPrice(DayOfWeek.WEDNESDAY);
        updateMinPrice(DayOfWeek.THURSDAY);
        updateMinPrice(DayOfWeek.FRIDAY);
    }


    private int updateMinPrice(DayOfWeek from ) throws Exception {
        int count = expediaHotelInfoMapper.getHotelCount();
        int requestHotelSize = 500;
        int offset = 0;

        while ((offset + requestHotelSize) <= count) {
            List<Long> list = expediaHotelInfoMapper.getHotelIdsLimit(requestHotelSize, offset);
            System.out.println(offset +  "(" + list.size() + ")");


            HotelSearch hotelSearch = new HotelSearch();
            hotelSearch.setCheckin(LocalDate.now().plusMonths(1).with(TemporalAdjusters.dayOfWeekInMonth(2, from)));
            hotelSearch.setCheckout(hotelSearch.getCheckin().plusDays(1));
            hotelSearch.setNumberOfAdults(2);

            ExHotelSearchApiResponse exHotelSearchApiResponse = callApi(list, hotelSearch);

            List<ExApiHotel> hotelList = exHotelSearchApiResponse.getHotels();

            if (hotelList != null && hotelList.size() != 0) {

                hotelList.forEach(exApiHotel -> {
                    BigDecimal defaultPrice = BigDecimal.valueOf(0);

                    BigDecimal minPrice = defaultPrice;

                    Long propertyId = exApiHotel.getPropertyId();

                    try {
                        if (exApiHotel.getRoomType() != null) {
                            List<ExApiRoomType> rooms = exApiHotel.getRoomType();

                            for (ExApiRoomType room : rooms) {
                                if (room.getTotalPrice() == null) {
                                    continue;
                                }

                                if (minPrice.compareTo(defaultPrice) == 0) {
                                    minPrice = room.getTotalPrice();
                                    continue;
                                }

                                if (room.getTotalPrice().compareTo(minPrice) == -1) {
                                    minPrice = room.getTotalPrice();
                                }
                            }

                        }
                        if (minPrice.compareTo(defaultPrice) != 0) {
                            if (from.equals(DayOfWeek.MONDAY))
                                expediaHotelInfoMapper.updateMinMonPrice(propertyId, minPrice);
                            else if (from.equals(DayOfWeek.TUESDAY))
                                expediaHotelInfoMapper.updateMinTuePrice(propertyId, minPrice);
                            else if (from.equals(DayOfWeek.WEDNESDAY))
                                expediaHotelInfoMapper.updateMinWedPrice(propertyId, minPrice);
                            else if (from.equals(DayOfWeek.THURSDAY))
                                expediaHotelInfoMapper.updateMinThurPrice(propertyId, minPrice);
                            else if (from.equals(DayOfWeek.FRIDAY))
                                expediaHotelInfoMapper.updateMinFriPrice(propertyId, minPrice);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }

                });
            }

            offset = (offset + requestHotelSize) > count ? (offset + (count-offset)) : (offset + requestHotelSize);
        }

        return count;
    }
}
