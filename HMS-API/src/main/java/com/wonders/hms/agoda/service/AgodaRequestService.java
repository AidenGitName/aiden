package com.wonders.hms.agoda.service;

import com.wonders.hms.agoda.persistence.AgodaHotelInfoMapper;
import com.wonders.hms.agoda.vo.*;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.user.persistence.SearchHistoryMapper;
import com.wonders.hms.user.type.BookStatus;
import com.wonders.hms.user.vo.SearchHistory;
import com.wonders.hms.util.HttpRequest;
import com.wonders.hms.util.XMLUtils;
import com.wonders.hms.util.vo.api.agent.Agoda;
import com.wonders.hms.util.vo.api.agent.agoda.Special;
import com.wonders.hms.wonder.persistence.WonderHotelInfoMapper;
import com.wonders.hms.wonder.type.HotelVendorKind;
import com.wonders.hms.wonder.vo.WonderHotel;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AgodaRequestService {

    @Value("${agoda.api.search.url}")
    private String agodaHotelSearchApiUrl;

    @Value("${agoda.api.bookinglist.url}")
    private String agodaBookingListApiUrl;


    private String siteId;

    private String apiKey;

    private String specialSiteId;

    private String specialApiKey;

    @Value("${agoda.account.xmlns}")
    private String xmlns;

    private RequestBuilder requestHotelSearchBuilder;

    private RequestBuilder requestBookingListBuilder;

    private RequestBuilder requestSpecialHotelSearchBuilder;

    private RequestBuilder requestSpecailBookingListBuilder;

    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    @Autowired
    private AgodaHotelInfoMapper agodaHotelInfoMapper;

    @Autowired
    private WonderHotelInfoMapper wonderHotelInfoMapper;

    @Autowired
    private Agoda agodaProp;

    @Autowired
    private Special agodaSpecial;

    @PostConstruct
    public void init() {

        this.siteId = agodaProp.getSiteId();
        this.apiKey = agodaProp.getApiKey();

        this.specialSiteId = agodaSpecial.getSiteId();
        this.specialApiKey = agodaSpecial.getApiKey();


        requestHotelSearchBuilder = RequestBuilder.post()
                .setUri(agodaHotelSearchApiUrl)
                .setHeader("Accept-encoding", "gzip,deflate")
                .setHeader("Content-Type", "text/xml")
                .setHeader("Authorization", siteId + ":" + apiKey);

        requestBookingListBuilder = RequestBuilder.post()
                .setUri(agodaBookingListApiUrl)
                .setHeader("Accept-encoding", "gzip,deflate")
                .setHeader("Content-Type", "text/xml")
                .setHeader("Authorization", siteId + ":" + apiKey);

        requestSpecialHotelSearchBuilder = RequestBuilder.post()
                .setUri(agodaHotelSearchApiUrl)
                .setHeader("Accept-encoding", "gzip,deflate")
                .setHeader("Content-Type", "text/xml")
                .setHeader("Authorization", specialSiteId + ":" + specialApiKey);

        requestSpecailBookingListBuilder = RequestBuilder.post()
                .setUri(agodaBookingListApiUrl)
                .setHeader("Accept-encoding", "gzip,deflate")
                .setHeader("Content-Type", "text/xml")
                .setHeader("Authorization", specialSiteId + ":" + specialApiKey);
    }

    @Deprecated
    public AgodaHotelResult getHotels(HotelSearch hotelSearch, List<AgodaHotel> hotelList) throws Exception {

        return null;
    }

    private List<Long> getHotelIds(List<AgodaHotel> hotelList) {
        return hotelList.stream()
                .map(AgodaHotel::getHotelId)
                .collect(Collectors.toList());
    }

    private AgodaHotelSearchRequest makeAgodaRequest(HotelSearch hotelSearch, List<Long> idList, boolean isSpecial) {
        AgodaHotelSearchRequest agodaHotelSearchRequest = new AgodaHotelSearchRequest();
        agodaHotelSearchRequest.setSiteId(isSpecial ? specialSiteId : siteId);
        agodaHotelSearchRequest.setApiKey(isSpecial ? specialApiKey : apiKey);
        agodaHotelSearchRequest.setXmlns(xmlns);
        agodaHotelSearchRequest.setHotelIds(idList);
        agodaHotelSearchRequest.setNumberOfAdults(hotelSearch.getNumberOfAdults());
        agodaHotelSearchRequest.setNumberOfChildren(hotelSearch.getNumberOfChildren());
        agodaHotelSearchRequest.setNumberOfRooms(hotelSearch.getNumberOfRooms());

        ArrayList<Integer> childrenAges = new ArrayList<>();
        hotelSearch.getAgesOfChildren().forEach(age -> {
            childrenAges.add(age);
        });
        if (!childrenAges.isEmpty()) {
            agodaHotelSearchRequest.setChildrenAges(childrenAges);
        }

        agodaHotelSearchRequest.setCheckin(hotelSearch.getCheckin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        agodaHotelSearchRequest.setCheckout(hotelSearch.getCheckout().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return agodaHotelSearchRequest;
    }

    public AgodaHotelResult getRooms(List<Long> agodaIds, HotelSearch hotelSearch, boolean isSpecial) throws Exception {

        AgodaHotelResult agodaHotelResult = null;

        int maxSize = 100; // 아고다에 한번에 요청하는 호텔정보 개수
        int restOfListSize = agodaIds.size();
        int firstIndex = 0;


        while (restOfListSize > 0) {
            int lastIndex = restOfListSize >= maxSize ? firstIndex + maxSize - 1: (firstIndex + restOfListSize - 1);

            List<Long>  requestIds = agodaIds.subList(firstIndex, lastIndex + 1);

            AgodaHotelSearchRequest agodaHotelSearchRequest = makeAgodaRequest(hotelSearch, requestIds, isSpecial);

            String body = XMLUtils.ObjectToXML(agodaHotelSearchRequest);

            AgodaHotelResult hotelResult = callAgodaApi(body, isSpecial);

            if (hotelResult.getAgodaErrorMessage() == null) {
               if (agodaHotelResult == null) {
                 agodaHotelResult = hotelResult;
                } else {
                 Optional.of(agodaHotelResult.getHotels())
                        .ifPresent(hotels -> hotels.addAll(hotelResult.getHotels()));
                }
            } else {
                log.warn("agoda error response %s", hotelResult.getAgodaErrorMessage().getErrorMessage());
            }

            restOfListSize -= requestIds.size();
            firstIndex = lastIndex + 1;

        }

        return agodaHotelResult;
    }

    private AgodaHotelResult callAgodaApi(String body, boolean isSpecial) throws IOException {
        HttpUriRequest request = isSpecial ?
                requestSpecialHotelSearchBuilder.setEntity(new StringEntity(body)).build()
                : requestHotelSearchBuilder.setEntity(new StringEntity(body)).build();

        HttpRequest httpRequest = new HttpRequest();
        String resultXml = httpRequest.send(request);

        return XMLUtils.toObject(resultXml, AgodaHotelResult.class);
    }

    public void updateBookingResult() throws Exception {
        List<SearchHistory> searchHistoryList = getBookingListFromDB();

        List<String> tags = extractTags(searchHistoryList);

        if (tags == null || tags.size() == 0) {
            return;
        }

        // todo: booking list 가져오는 것도 특가 따로인지 확인
        AgodaBookingListResponse agodaBookingListResponse = getBookingListFromApi(tags);

        List<AgodaBooking> bookings = agodaBookingListResponse.getBookings();

        if (bookings == null) {
            return;
        }

        statusHistoryUpdate(bookings);
    }


    private void statusHistoryUpdate(List<AgodaBooking> bookings) {
        for(AgodaBooking agodaBooking : bookings ) {
            if (agodaBooking.getTag() == null) {
                continue;
            }

            BookStatus status = agodaBooking.getWonderBookingStatus();
            Long reservationId = agodaBooking.getBookingId();

            List<SearchHistory> uuidSearchHistories =
                    searchHistoryMapper.getSearchHistoryWithUuid(agodaBooking.getTag());

            if (status.equals(BookStatus.BOOKED)
                    && !uuidSearchHistories.isEmpty()
                    && !uuidSearchHistories.stream()
                    .filter(searchHistory ->
                            searchHistory.getReservationId() != null
                                    && searchHistory.getReservationId().compareTo(reservationId) == 0
                    )
                    .findFirst().isPresent()
                    ) {
                Optional<SearchHistory> optionalSearchHistory = uuidSearchHistories.stream()
                        .filter(searchHistory -> searchHistory.getStatus().equals(BookStatus.BOOKING)).findFirst();

                SearchHistory searchHistory;
                if (optionalSearchHistory.isPresent()) {
                    searchHistory = optionalSearchHistory.get();
                }
                else {
                    searchHistory = uuidSearchHistories.get(0);
                }

                searchHistory.setStatus(status);
                searchHistory.setStatusUpdatedDate(LocalDateTime.now());
                searchHistory.setReservationVendorUrl(agodaBooking.getSelfSerice());
                searchHistory.setReservationId(agodaBooking.getBookingId());

                AgodaHotel agodaHotel = agodaHotelInfoMapper.getHotelByHotelId(agodaBooking.getHotelId());

                searchHistory.setRealHotelName(agodaBooking.getHotelName() == null? null : agodaBooking.getHotelName());
                searchHistory.setRealCityName(agodaBooking.getCityName() == null? null : agodaBooking.getCityName());
                searchHistory.setRealCheckin(agodaBooking.getArrival() == null? null : agodaBooking.getArrival());
                searchHistory.setRealCheckOut(agodaBooking.getDeparture() == null? null : agodaBooking.getDeparture());
                searchHistory.setRealPrice(agodaBooking.getUsdAmount() == null? null : agodaBooking.getUsdAmount());

                searchHistory.setRealHotelAddress(agodaHotel.getAddress());
                searchHistory.setRealLatitude(agodaHotel.getLatitude());
                searchHistory.setRealLongitude(agodaHotel.getLongitude());

                changeHotelNameInKorean(agodaHotel, searchHistory);

                searchHistoryMapper.insertSearchHistory(searchHistory);

            } else if (status.equals(BookStatus.CANCEL)
                    && !uuidSearchHistories.isEmpty()
                    && !uuidSearchHistories.stream()
                    .filter(searchHistory ->
                            searchHistory.getReservationId() != null
                                    && searchHistory.getReservationId().compareTo(reservationId) == 0
                                    && searchHistory.getStatus().equals(status)
                    )
                    .findFirst().isPresent()
                    ) {
                Optional<SearchHistory> optionalSearchHistory = uuidSearchHistories.stream()
                        .filter(searchHistory ->
                                searchHistory.getReservationId() != null
                                        && searchHistory.getReservationId().compareTo(reservationId) == 0
                                        && BookStatus.BOOKED.equals(searchHistory.getStatus()))
                        .findFirst();

                SearchHistory searchHistory;
                if (optionalSearchHistory.isPresent()) {
                    searchHistory = optionalSearchHistory.get();
                }
                else {
                    searchHistory = uuidSearchHistories.get(0);
                    searchHistory.setReservationId(reservationId);
                    searchHistory.setStatusUpdatedDate(LocalDateTime.now());
                    searchHistory.setReservationVendorUrl(agodaBooking.getSelfSerice());
                    searchHistory.setReservationId(agodaBooking.getBookingId());

                    AgodaHotel agodaHotel = agodaHotelInfoMapper.getHotelByHotelId(agodaBooking.getHotelId());

                    searchHistory.setRealHotelName(agodaBooking.getHotelName() == null? null : agodaBooking.getHotelName());
                    searchHistory.setRealCityName(agodaBooking.getCityName() == null? null : agodaBooking.getCityName());
                    searchHistory.setRealCheckin(agodaBooking.getArrival() == null? null : agodaBooking.getArrival());
                    searchHistory.setRealCheckOut(agodaBooking.getDeparture() == null? null : agodaBooking.getDeparture());
                    searchHistory.setRealPrice(agodaBooking.getUsdAmount() == null? null : agodaBooking.getUsdAmount());

                    searchHistory.setRealHotelAddress(agodaHotel.getAddress());
                    searchHistory.setRealLatitude(agodaHotel.getLatitude());
                    searchHistory.setRealLongitude(agodaHotel.getLongitude());

                    changeHotelNameInKorean(agodaHotel, searchHistory);
                }
                searchHistory.setReservationVendorUrl(agodaBooking.getSelfSerice());
                searchHistory.setStatus(status);
                searchHistoryMapper.insertSearchHistory(searchHistory);
            }
        }
    }

    private void changeHotelNameInKorean(AgodaHotel agodaHotel, SearchHistory searchHistory) {
        WonderHotel wonderHotel = wonderHotelInfoMapper.getHotelByAgodaHotelId(agodaHotel.getHotelId());
        if (wonderHotel != null) {
            searchHistory.setRealHotelName(wonderHotel.getName());
            searchHistory.setRealCityName(wonderHotel.getCity());
        }
    }


    private SearchHistory getSearchHistoryByTag(String uuid, List<SearchHistory> searchHistoryList) {
        for (SearchHistory searchHistory : searchHistoryList) {
           if (searchHistory.getUuid().equals(uuid)) {
               return searchHistory;
           }
        }
        return null;
    }

    private List<String> extractTags(List<SearchHistory> searchHistoryList) {
        return searchHistoryList.stream()
                .map(searchHistory -> {
                    return searchHistory.getUuid();
                }).collect(Collectors.toList());
    }

    private List<SearchHistory> getBookingListFromDB() {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setVendor(HotelVendorKind.AGODA);
        searchHistory.setExitedDateFrom(LocalDateTime.now().minusMonths(1));

        return searchHistoryMapper.getLeastHistory(searchHistory);
    }

    private AgodaBookingListResponse getBookingListFromApi(List<String> tags) throws Exception {

        AgodaBookingListRequest agodaBookingListRequest = new AgodaBookingListRequest();
        agodaBookingListRequest.setSiteId(siteId);
        agodaBookingListRequest.setApiKey(apiKey);
        agodaBookingListRequest.setXmlns(xmlns);
        agodaBookingListRequest.setTags(tags);

        String body = XMLUtils.ObjectToXML(agodaBookingListRequest);

        HttpUriRequest request = requestBookingListBuilder.setEntity(new StringEntity(body)).build();

        HttpRequest httpRequest = new HttpRequest();
        String resultXml = httpRequest.send(request);

        return XMLUtils.toObject(resultXml, AgodaBookingListResponse.class);

    }
}
