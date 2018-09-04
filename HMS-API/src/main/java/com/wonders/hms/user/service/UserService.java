package com.wonders.hms.user.service;

import com.wonders.hms.autocomplete.vo.Result;
import com.wonders.hms.bookingDotCom.bookingDetail.vo.Booking;
import com.wonders.hms.bookingDotCom.bookingDetail.vo.BookingDetailRS;
import com.wonders.hms.bookingDotCom.hotelAvailability.domain.AutocompleteRS;
import com.wonders.hms.bookingDotCom.service.BookingDotComService;
import com.wonders.hms.bookingDotCom.vo.AutocompleteVO;
import com.wonders.hms.bookingDotCom.vo.BookingDetailsVO;
import com.wonders.hms.hotel.persistence.HotelVendorMapper;
import com.wonders.hms.user.persistence.SearchHistoryMapper;
import com.wonders.hms.user.type.BookStatus;
import com.wonders.hms.user.vo.HotelUrlInfo;
import com.wonders.hms.user.vo.MyPageHistory;
import com.wonders.hms.user.vo.MypageHotelSearchInfo;
import com.wonders.hms.user.vo.SearchHistory;
import com.wonders.hms.util.RestClient;
import com.wonders.hms.wonder.persistence.WonderHotelInfoMapper;
import com.wonders.hms.wonder.vo.WonderHotel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserService {
    private static final String BOOKING_RESERVATION_VENDOR_HOST = "https://secure.booking.com/";
    private static final String BOOKING_RESERVATION_VENDOR_URL = "myreservations.ko.html?bn=%d&pincode=%s";

    @Value("${wonder.hotel.front.url}")
    private String wonderHotelFrontUrl;

    @Autowired
    BookingDotComService bookingDotComService;

    @Autowired
    SearchHistoryMapper searchHistoryMapper;

    @Autowired
    WonderHotelInfoMapper wonderHotelInfoMapper;

    @Autowired
    HotelVendorMapper hotelVendorMapper;

    public List<SearchHistory> getSearchHistory(Long mId) {
        List<SearchHistory> searchHistories = searchHistoryMapper.getSearchHistory(mId);

        return searchHistories;
    }

    public void insertSearchHistory(SearchHistory searchHistory) {
        searchHistory.setStatus(BookStatus.BOOKING);

        searchHistoryMapper.insertSearchHistory(searchHistory);
    }

    public void updateSearchHistoryWithBookingDotCom() throws Exception{
        final int DEFAULT_ROWS = 1000;

        BookingDetailsVO bookingDetailsVO = new BookingDetailsVO();

        bookingDetailsVO.setLastChange(LocalDate.now().minusDays(1));

        int offset = 0;

        while (true) {
            bookingDetailsVO.setRows(DEFAULT_ROWS);
            bookingDetailsVO.setOffset(offset);

            BookingDetailRS bookingDetailRS = bookingDotComService.bookingDetails(bookingDetailsVO);

            bookingDetailRS.getResult().forEach(booking -> {
                try {
                    if (booking.getStatus().equals("booked") || booking.getStatus().equals("stayed")) {
                        insertBookingDotComBookedInfo(booking);
                    } else if (booking.getStatus().equals("cancelled") || booking.getStatus().equals("no_show")) {
                        insertBookingDotComCancelInfo(booking);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if (bookingDetailRS.getResult().size() < DEFAULT_ROWS) {
                break;
            }
            offset += DEFAULT_ROWS;
        }
    }

    private void insertBookingDotComBookedInfo(Booking booking) {
        String uuid = booking.getAffiliateLabel();
        Long reservationId = booking.getReservationId();

        List<SearchHistory> uuidSearchHistories =
                searchHistoryMapper.getSearchHistoryWithUuid(uuid);

        if (    uuidSearchHistories.isEmpty()
                || uuidSearchHistories.stream()
                .filter(searchHistory ->
                        searchHistory.getReservationId() != null
                        && searchHistory.getReservationId().compareTo(reservationId) == 0
                )
                .findFirst().isPresent()
        ) {
            // already updated
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

        String bookingReservationVendorUrl = String.format(
                BOOKING_RESERVATION_VENDOR_URL,
                booking.getReservationId(),
                booking.getPincode()
        );

        searchHistory.setReservationId(booking.getReservationId());
        searchHistory.setStatus(BookStatus.BOOKED);
        searchHistory.setReservationVendorUrl(
                BOOKING_RESERVATION_VENDOR_HOST + bookingReservationVendorUrl
        );

        String realHotelName = getHotelRealName(bookingReservationVendorUrl, booking.getUrl());

        searchHistory.setRealHotelName(realHotelName);

        if (realHotelName != null) {
            WonderHotel wonderHotel = getWonderHotelIdWithBookingName(realHotelName, searchHistory.getWonderHotelInfoId());
            if (wonderHotel != null) {
                searchHistory.setRealCityName(wonderHotel.getCity());
                searchHistory.setRealHotelAddress(wonderHotel.getAddress());
                searchHistory.setRealLatitude(wonderHotel.getLatitude());
                searchHistory.setRealLongitude(wonderHotel.getLongitude());
                searchHistory.setRealHotelPhon(wonderHotel.getPhone());
            }
        }
        searchHistory.setRealCheckin(booking.getCheckin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        searchHistory.setRealCheckOut(booking.getCheckout().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        searchHistory.setRealPrice(booking.getPriceLocal());

        bookingDotComSearchHistoryInsert(searchHistory, uuid);
    }

    private void insertBookingDotComCancelInfo(Booking booking) {
        String uuid = booking.getAffiliateLabel();
        Long reservationId = booking.getReservationId();

        List<SearchHistory> uuidSearchHistories =
                searchHistoryMapper.getSearchHistoryWithUuid(uuid);

        if ( uuidSearchHistories.isEmpty()
                || uuidSearchHistories.stream()
                .filter(searchHistory ->
                        searchHistory.getReservationId() != null
                                && searchHistory.getReservationId().compareTo(reservationId) == 0
                                && searchHistory.getStatus().equals(BookStatus.CANCEL)
                )
                .findFirst().isPresent()
                ) {
            // already updated
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
            searchHistory.setStatus(BookStatus.CANCEL);
            searchHistory.setReservationId(booking.getReservationId());
        }
        else {
            searchHistory = uuidSearchHistories.get(0);
            searchHistory.setStatus(BookStatus.CANCEL);
            searchHistory.setReservationId(booking.getReservationId());
            searchHistory.setRealCheckin(booking.getCheckin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            searchHistory.setRealCheckOut(booking.getCheckout().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            searchHistory.setRealPrice(booking.getPriceLocal());

            String bookingReservationVendorUrl = String.format(
                    BOOKING_RESERVATION_VENDOR_URL,
                    booking.getReservationId(),
                    booking.getPincode()
            );

            searchHistory.setReservationVendorUrl(
                    BOOKING_RESERVATION_VENDOR_HOST + bookingReservationVendorUrl
            );
        }

        bookingDotComSearchHistoryInsert(searchHistory, uuid);
    }

    private void bookingDotComSearchHistoryInsert(SearchHistory searchHistory, String uuid) {
        searchHistoryMapper.insertSearchHistory(searchHistory);
        searchHistoryMapper.updateBookingDotComStatus(uuid);
    }

    private String getHotelRealName(String reservationUrl, String hotelUrl) {
        RestClient restClient = new RestClient(BOOKING_RESERVATION_VENDOR_HOST);

        String responseBody = restClient.get(reservationUrl);

        Pattern pattern = Pattern.compile(
                "\\<a href\\=\\\""
                        + hotelUrl.replace("html", "ko.html").replaceAll("\\.", "\\.")
                        + "\\?.*\\\".*[\\>]([^<]*)\\<\\/a\\>"
        );

        Matcher matcher = pattern.matcher(responseBody);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    private WonderHotel getWonderHotelIdWithBookingName(String bookingName, Long wonderHotelId) {
        WonderHotel wonderHotel = wonderHotelInfoMapper.getHotelByHotelId(wonderHotelId);

        if (wonderHotel.equals(bookingName)) {
            return wonderHotel;
        }
        AutocompleteVO autocompleteVO = new AutocompleteVO();
        autocompleteVO.setText(bookingName);

        try {
            AutocompleteRS autocompleteRS = bookingDotComService.autocomplete(autocompleteVO);
            for(Result result : autocompleteRS.getResult()) {
                if (result.getName().equals(bookingName)) {
                    String bookingId = result.getId();
                    Long hotelVendorIndexId = hotelVendorMapper.getHotelVendorIdWithBookingId(Long.valueOf(bookingId));
                    wonderHotel = wonderHotelInfoMapper.getHotelByHotelIndexId(hotelVendorIndexId);
                    return wonderHotel;
                }
            }
        }
        catch (IOException e) {
        }
        return wonderHotel;
    }

    public List<MyPageHistory> getMyPageSearchHistory(Long mId, BookStatus status) {
        List<MyPageHistory> resultList = new ArrayList<>();

        if (status == BookStatus.BOOKED) {
            resultList = searchHistoryMapper.getPassedBook(mId);
        } else if (status == BookStatus.BOOKING) {
            resultList = searchHistoryMapper.getProcessingBook(mId);
        }

        return resultList;
    }

    public MypageHotelSearchInfo getHotelUrl (Long mId, HotelUrlInfo hotelUrlInfo) {
        String childrenAgesString = new String();

        for(Integer age: hotelUrlInfo.getChildrenAges()) {
            if (age.compareTo(0) == 0) {
                continue;
            }
            childrenAgesString += age.toString() + "&";
        }
        childrenAgesString = childrenAgesString.length() == 0 ?
                null : childrenAgesString.substring(0, childrenAgesString.length() - 1);

        MypageHotelSearchInfo mypageHotelSearchInfo = new MypageHotelSearchInfo();
        mypageHotelSearchInfo.setShortcut(
            String.format(
                    "%shotelList/%s/%s/%s/%d/%s/%d",
                    wonderHotelFrontUrl,
                    hotelUrlInfo.getCityCode().toUpperCase(),
                    hotelUrlInfo.getCheckin(),
                    hotelUrlInfo.getCheckout(),
                    hotelUrlInfo.getAdults(),
                    childrenAgesString,
                    hotelUrlInfo.getNumRoom()
            )
        );

        return mypageHotelSearchInfo;
    }
}
