package com.wonders.hms.expedia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.expedia.persistence.ExpediaHotelInfoMapper;
import com.wonders.hms.expedia.ph.vo.PHConversionData;
import com.wonders.hms.expedia.ph.vo.PHConversionDataDetail;
import com.wonders.hms.expedia.ph.vo.PHConversionItem;
import com.wonders.hms.expedia.ph.vo.PHMetaData;
import com.wonders.hms.expedia.vo.PHReportResponse;
import com.wonders.hms.expedia.vo.searchapi.*;
import com.wonders.hms.user.persistence.SearchHistoryMapper;
import com.wonders.hms.user.type.BookStatus;
import com.wonders.hms.user.vo.SearchHistory;
import com.wonders.hms.util.HttpRequest;
import com.wonders.hms.util.vo.api.agent.expedia.Ph;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExpediaSearchHistoryService {

    private String userName;

    private String password;

    private String authenticationString;

    private String auth;

    @Value("${expedia.ph.reporturl}")
    private String phReportUrl;

    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    @Autowired
    private ExpediaHotelInfoMapper expediaHotelInfoMapper;

    @Autowired
    private ExpediaRequestService expediaRequestService;

    private static final String reservationVendorUrl = "https://www.expedia.co.kr/user/itin";

    @Autowired
    private Ph expediaPhProp;

    @PostConstruct
    public void init() {

        this.userName = expediaPhProp.getUserName();
        this.password = expediaPhProp.getPassword();

        authenticationString = userName + ":" + password;
        auth =  "Basic " + Base64Utils.encodeToString(authenticationString.getBytes());
    }


    public void updateBookingResult() throws Exception {
        PHReportResponse phReportResponse = getPHReport();
        List<PHConversionData> conversionList =  phReportResponse.getConversions();

        conversionList.forEach(phConversionData -> {
            PHConversionItem phConversionItem = phConversionData.getPhConversionDataDetail().getConversionItems().get(0);
            BookStatus status = phConversionItem.getItemStatus();

            if (status == BookStatus.BOOKED) {
                try {
                    insertExpediaBookedInfo(phConversionData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (status == BookStatus.CANCEL) {
                try {
                    // TODO reject id 없는 이슈 해결
                    insertExpediaCanceledInfo(phConversionData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void insertExpediaCanceledInfo(PHConversionData phConversionData) throws IOException {
        String uuid = phConversionData.getPhConversionDataDetail().getPublisherReference();
        Long reservationId = Optional.of(phConversionData.getPhConversionDataDetail())
                .map(PHConversionDataDetail::getConversionItems)
                .map(itemList -> {
                    return itemList.get(0);
                })
                .map(PHConversionItem::getMetaData)
                .map(PHMetaData::getItineraryNumber)
                .map(Long::parseLong)
                .orElse(null);

        List<SearchHistory> uuidSearchHistories =
                searchHistoryMapper.getSearchHistoryWithUuid(uuid);

        if (
                reservationId != null
                || uuidSearchHistories.isEmpty()
                || uuidSearchHistories.stream()
                .filter(searchHistory ->
                        searchHistory.getReservationId() != null
                                && searchHistory.getReservationId().compareTo(reservationId) == 0
                                && searchHistory.getStatus().equals(BookStatus.CANCEL)
                )
                .findFirst().isPresent()
        ) {
            return;
        }
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
            searchHistory.setRealHotelName(
                    Optional.of(phConversionData.getPhConversionDataDetail())
                            .map(PHConversionDataDetail::getConversionItems)
                            .map(itemList -> {
                                return itemList.get(0);
                            })
                            .map(PHConversionItem::getSku)
                            .orElse(null)
            );
            searchHistory.setRealCityName(
                    Optional.of(phConversionData.getPhConversionDataDetail())
                            .map(PHConversionDataDetail::getConversionItems)
                            .map(itemList -> {
                                return itemList.get(0);
                            })
                            .map(PHConversionItem::getMetaData)
                            .map(PHMetaData::getDest)
                            .orElse(null)
            );
            searchHistory.setRealCheckin(
                    Optional.of(phConversionData.getPhConversionDataDetail())
                            .map(PHConversionDataDetail::getConversionItems)
                            .map(itemList -> {
                                return itemList.get(0);
                            })
                            .map(PHConversionItem::getMetaData)
                            .map(PHMetaData::getCheckInDate)
                            .orElse(null)
            );
            searchHistory.setRealCheckOut(
                    Optional.of(phConversionData.getPhConversionDataDetail())
                            .map(PHConversionDataDetail::getConversionItems)
                            .map(itemList -> {
                                return itemList.get(0);
                            })
                            .map(PHConversionItem::getMetaData)
                            .map(PHMetaData::getCheckOutDate)
                            .orElse(null)
            );
        }

        searchHistory.setReservationVendorUrl(reservationVendorUrl);
        searchHistory.setStatus(BookStatus.CANCEL);

        if (!hasRealInfo(searchHistory)) {
            getHotelRealInfo(phConversionData, searchHistory);
        }

        insertExpediaSearchHistory(searchHistory, uuid);
        System.out.println("insert cancel");
    }

    private boolean hasRealInfo(SearchHistory searchHistory) {
        if (searchHistory.getRealCheckin() == null
                || searchHistory.getRealCheckOut() == null
                || searchHistory.getRealHotelAddress() == null
                || searchHistory.getRealLongitude() == null
                || searchHistory.getRealLatitude() == null) {
            return false;
        }
        return true;
    }


    private void insertExpediaBookedInfo(PHConversionData phConversionData) throws IOException {
        String uuid = phConversionData.getPhConversionDataDetail().getPublisherReference();
        Long reservationId = Optional.of(phConversionData.getPhConversionDataDetail())
                .map(PHConversionDataDetail::getConversionItems)
                .map(itemList -> {
                    return itemList.get(0);
                })
                .map(PHConversionItem::getMetaData)
                .map(PHMetaData::getItineraryNumber)
                .map(Long::parseLong)
                .orElse(null);

        List<SearchHistory> uuidSearchHistories =
                searchHistoryMapper.getSearchHistoryWithUuid(uuid);

        if (reservationId == null
            || uuidSearchHistories.isEmpty()
            || uuidSearchHistories.stream()
                .filter(searchHistory ->
                        searchHistory.getReservationId() != null
                                && searchHistory.getReservationId().compareTo(reservationId) == 0
                )
                .findFirst().isPresent()
        ) {
            return;
        }

        Optional<SearchHistory> optionalSearchHistory = uuidSearchHistories.stream()
                .filter(searchHistory -> searchHistory.getStatus().equals(BookStatus.BOOKING)).findFirst();

        SearchHistory searchHistory;
        if (optionalSearchHistory.isPresent()) {
            searchHistory = optionalSearchHistory.get();
        }
        else {
            searchHistory = uuidSearchHistories.get(0);
        }

        searchHistory.setReservationId(reservationId);
        searchHistory.setRealHotelName(
                Optional.of(phConversionData.getPhConversionDataDetail())
                .map(PHConversionDataDetail::getConversionItems)
                .map(itemList -> {
                    return itemList.get(0);
                })
                .map(PHConversionItem::getSku)
                .orElse(null)
        );
        searchHistory.setRealCityName(
                Optional.of(phConversionData.getPhConversionDataDetail())
                        .map(PHConversionDataDetail::getConversionItems)
                        .map(itemList -> {
                            return itemList.get(0);
                        })
                        .map(PHConversionItem::getMetaData)
                        .map(PHMetaData::getDest)
                        .orElse(null)
        );
        searchHistory.setRealCheckin(
                Optional.of(phConversionData.getPhConversionDataDetail())
                        .map(PHConversionDataDetail::getConversionItems)
                        .map(itemList -> {
                            return itemList.get(0);
                        })
                        .map(PHConversionItem::getMetaData)
                        .map(PHMetaData::getCheckInDate)
                        .orElse(null)
        );
        searchHistory.setRealCheckOut(
                Optional.of(phConversionData.getPhConversionDataDetail())
                        .map(PHConversionDataDetail::getConversionItems)
                        .map(itemList -> {
                            return itemList.get(0);
                        })
                        .map(PHConversionItem::getMetaData)
                        .map(PHMetaData::getCheckOutDate)
                        .orElse(null)
        );

        searchHistory.setReservationVendorUrl(reservationVendorUrl);
        searchHistory.setStatus(BookStatus.BOOKED);

        getHotelRealInfo(phConversionData, searchHistory);

        insertExpediaSearchHistory(searchHistory, uuid);

    }

    private void getHotelRealInfo(PHConversionData phConversionData, SearchHistory searchHistory) throws IOException {
        Long hotelId = Optional.of(phConversionData.getPhConversionDataDetail())
                        .map(PHConversionDataDetail::getConversionItems)
                        .map(itemList -> {
                            return itemList.get(0);
                        })
                        .map(PHConversionItem::getMetaData)
                        .map(PHMetaData::getHotelId)
                        .orElse(null);

        if (hotelId != null) {
            ExpediaHotelInfo expediaHotelInfo = expediaHotelInfoMapper.getHotelByHotelId(hotelId) ;
            if (expediaHotelInfo != null) {
                searchHistory.setRealHotelName(expediaHotelInfo.getName());
                searchHistory.setRealCityName(expediaHotelInfo.getCity());
                searchHistory.setRealHotelAddress(expediaHotelInfo.getAddress());
                searchHistory.setRealHotelPhon(expediaHotelInfo.getPhone());
                searchHistory.setRealLongitude(expediaHotelInfo.getLongitude());
                searchHistory.setRealLatitude(expediaHotelInfo.getLatitude());
            } else {
                ExApiHotel exApiHotel = getHotelInfoFromApi(hotelId);
                if (exApiHotel != null) {
                    searchHistory.setRealHotelName(exApiHotel.getName());
                    searchHistory.setRealCityName(exApiHotel.getLocation().getAddress().getCity());
                    searchHistory.setRealHotelAddress(
                            exApiHotel.getLocation().getAddress().getAddress1()
                    + " " + exApiHotel.getLocation().getAddress().getAddress2());
                    searchHistory.setRealLongitude(exApiHotel.getLocation().getGeoLocation().getLongitude());
                    searchHistory.setRealLatitude(exApiHotel.getLocation().getGeoLocation().getLatitude());
                }

            }
        }
    }

    private ExApiHotel getHotelInfoFromApi(Long hotelId) throws IOException {
        List<Long> hotel = new ArrayList<>();
        hotel.add(hotelId);
        List<ExApiHotel> result = expediaRequestService.callHotelInfoApi(hotel);
        if (result != null && result.size() != 0) {
           return result.get(0);
        }
        return null;
    }

    private void insertExpediaSearchHistory(SearchHistory searchHistory, String uuid) {
        searchHistoryMapper.insertSearchHistory(searchHistory);
        searchHistoryMapper.updateExpediaStatus(uuid);
    }


    // 사용자 email 아는 경우
    public void updateBookingResultWithEmail() throws IOException {
        PHReportResponse phReportResponse = getPHReport();
        List<PHConversionData> conversionList =  phReportResponse.getConversions();

        Map<String, String> itinerarynumberMap = new HashMap<>();

        conversionList.forEach(phConversionData -> {
            String uuid = phConversionData.getPhConversionDataDetail().getPublisherReference();
            PHConversionItem phConversionItem = phConversionData.getPhConversionDataDetail().getConversionItems().get(0);
            String itineraryNumber = phConversionItem.getMetaData().getItineraryNumber();

            itinerarynumberMap.put(uuid, itineraryNumber);
        });

        getHotelBookingInfo(itinerarynumberMap);
    }

    // 익스피디아에서 Itinerary Retrieve API로 사용자 정보를 가져올경우 email이 필수인데, 현재(18.08.14) 실사용자가 결제때 사용한 email을 알 수 없는
    // 이슈로 인해 사용하지 않음.
    private void getHotelBookingInfo(Map<String,String> itinerarynumberMap) {

        itinerarynumberMap.forEach((uuid, itineraryNumber) -> {
            //TODO fix need
            List<SearchHistory> searchHistories = searchHistoryMapper.getSearchHistoryWithUuid(uuid);
            SearchHistory searchHistory = searchHistories.get(0);
            if (searchHistory == null) return;


            try {
                ExApiBooking exApiBooking = expediaRequestService.retrieveBooking(itineraryNumber);

                if (exApiBooking != null) {
                    if ((exApiBooking.getStatus() == BookStatus.BOOKED) ||
                    (exApiBooking.getStatus() == BookStatus.CANCEL)) {
                        searchHistory.setStatus(exApiBooking.getStatus());
                        searchHistory.setRealCityName(exApiBooking.getHotelDetails().getName());
                        searchHistory.setRealCityName(exApiBooking.getHotelDetails().getLocation().getAddress().getCity());
                        searchHistory.setCheckin(exApiBooking.getHotelDetails().getRoom().get(0).getStayDate().get(0).getCheckIn());
                        searchHistory.setCheckout(exApiBooking.getHotelDetails().getRoom().get(0).getStayDate().get(0).getCheckOut());
                        searchHistory.setRealPrice(exApiBooking.getTotalPrice().getValue());
                        searchHistory.setStatusUpdatedDate(LocalDateTime.now());
                        searchHistory.setReservationVendorUrl(
                                Optional.of(exApiBooking.getHotelDetails())
                                .map(ExApiHotelDetails::getWebItinRetrieve)
                                .map(ExApiWebItinRetrieve::getHref)
                                .orElse(null)
                        );

                        searchHistoryMapper.insertSearchHistory(searchHistory);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    private PHReportResponse getPHReport() throws IOException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String requestUrl = phReportUrl + "?"
                + "start_date=" + LocalDate.now().minusMonths(1).format(format)
                + "&end_date=" + LocalDate.now().plusDays(1).format(format)
                + "&campaign_id=1011l68"
                ;

        HttpUriRequest request = RequestBuilder.get()
                .setUri(requestUrl)
                .setHeader("Authorization", auth)
                .setHeader("Content-Type", "application/json")
                .setHeader("Content-Encoding", "gzip")
                .build();

        HttpRequest httpRequest = new HttpRequest();
        String body = httpRequest.send(request);

        ObjectMapper objectMapper = new ObjectMapper();
        PHReportResponse phReportResponse = objectMapper.readValue(body, PHReportResponse.class);

        return phReportResponse;
    }
}
