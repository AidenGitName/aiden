package com.wonders.hms.bookingDotCom.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.autocomplete.vo.Meta;
import com.wonders.hms.autocomplete.vo.Result;
import com.wonders.hms.bookingDotCom.blockAvailability.domain.BlockAvailabilityRS;
import com.wonders.hms.bookingDotCom.bookingDetail.vo.BookingDetailRS;
import com.wonders.hms.bookingDotCom.changedHotel.domain.ChangedHotelsRS;
import com.wonders.hms.bookingDotCom.cites.vo.CitesRS;
import com.wonders.hms.bookingDotCom.hotelAvailability.domain.AutocompleteRS;
import com.wonders.hms.bookingDotCom.hotelAvailability.domain.HotelAvailabilityRS;
import com.wonders.hms.bookingDotCom.hotel.domain.HotelsRS;
import com.wonders.hms.bookingDotCom.vo.*;
import com.wonders.hms.util.RestClient;
import com.wonders.hms.util.UriFormat;
import com.wonders.hms.util.vo.api.agent.Booking;
import com.wonders.hms.util.vo.api.agent.booking.Affiliate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



@Service
@Slf4j
public class BookingDotComService {

    private static final String HOTEL_AVAILABILITY_PATH = "2.2/json/hotelAvailability";
    private static final String BLOCK_AVAILABILITY_PATH = "2.2/json/blockAvailability";
    private static final String AUTOCOMPLETE_PATH = "2.2/json/autocomplete";
    private static final String HOTELS_PATH = "2.2/json/hotels";
    private static final String CHANGED_HOTELS_PATH = "2.2/json/changedHotels";
    private static final String CITIES_HOTELS_PATH = "2.2/json/cities";
    private static final String BOOKING_DETAILS_PATH = "2.2/json/bookingDetails";

    private static final int EMTPY_AUTOCOMPLETE_RESULT_EXPIRE_SECOND = 60 * 10;

    private String BOOKING_DOT_COM_ID;

    private String BOOKING_DOT_COM_PASSWORD;

    @Value("${booking.com.server}")
    private String BOOKING_DOT_COM_SERVER;

    @Value("${booking.com.secure.server}")
    private String BOOKING_DOT_COM_SECURE_SERVER;

    private int BOOKING_DOT_COM_AFFILIATE_ROOM;

    @Autowired
    private RedisTemplate<String, Object> autocompleteRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private RestClient restClient;
    private RestClient bookingSecureRestClinet;


    @Autowired
    private Booking bookingProp;

    @Autowired
    private Affiliate bookingAffilate;


    @PostConstruct
    public void init() {

        this.BOOKING_DOT_COM_ID = bookingProp.getId();
        this.BOOKING_DOT_COM_PASSWORD = bookingProp.getPassword();

        this.BOOKING_DOT_COM_AFFILIATE_ROOM = bookingAffilate.getRoom();


        this.restClient = new RestClient(this.BOOKING_DOT_COM_SERVER);
        this.restClient.setAuthorization(this.getAuthentication());

        this.bookingSecureRestClinet = new RestClient(this.BOOKING_DOT_COM_SECURE_SERVER);
        this.bookingSecureRestClinet.setAuthorization(this.getAuthentication());
    }

    public HotelAvailabilityRS hotelAvailability(HotelAvailabilityVO hotelAvailabilityVO) throws IOException, HttpClientErrorException {
        String hotelAvailabilityResponseBody =
                this.restClient.get(this.HOTEL_AVAILABILITY_PATH + "?" + this.objectMapper.convertValue(hotelAvailabilityVO, UriFormat.class));

        return this.objectMapper.readValue(hotelAvailabilityResponseBody, HotelAvailabilityRS.class);
    }

    public BlockAvailabilityRS blockAvailability(BlockAvailabilityVO blockAvailabilityVO) throws IOException, HttpClientErrorException {
        blockAvailabilityVO.setAffiliateId(BOOKING_DOT_COM_AFFILIATE_ROOM);

        String blockAvailabilityResponseBody =
                this.restClient.get(this.BLOCK_AVAILABILITY_PATH + "?" + this.objectMapper.convertValue(blockAvailabilityVO, UriFormat.class));

        return this.objectMapper.readValue(blockAvailabilityResponseBody, BlockAvailabilityRS.class);
    }

    public HotelsRS hotels(HotelsVO hotelsVO) throws IOException, HttpClientErrorException {
        String hotelsResponseBody =
                this.restClient.get(this.HOTELS_PATH + "?" + this.objectMapper.convertValue(hotelsVO, UriFormat.class));

        return this.objectMapper.readValue(hotelsResponseBody, HotelsRS.class);
    }

    public AutocompleteRS autocomplete(AutocompleteVO autocompleteVO) throws IOException, HttpClientErrorException {

        if (autocompleteVO.getText().equals("괌")) {
            return getGuamAutocompleteRS();
        }

        String requestQueryString = this.objectMapper.convertValue(autocompleteVO, UriFormat.class).toString();
        String autocompleteRedisKey = autocompleteVO.getText();

        ValueOperations valueOperations = this.autocompleteRedisTemplate.opsForValue();

        String autocompleteResponseBody = null;
        AutocompleteRS autocompleteRS;
        try {
            autocompleteResponseBody = (String) valueOperations.get(autocompleteRedisKey);
        } catch (RedisConnectionFailureException exception) {
            log.error(exception.toString());
        }

        if (autocompleteResponseBody == null) {

            autocompleteResponseBody =
                    this.restClient.get(this.AUTOCOMPLETE_PATH + "?" + requestQueryString);

            autocompleteRS = this.objectMapper.readValue(autocompleteResponseBody, AutocompleteRS.class);
            try {
                valueOperations.set(autocompleteRedisKey, autocompleteResponseBody);
                if (autocompleteRS.getResult().isEmpty()) {
                    autocompleteRedisTemplate.expire(
                            autocompleteRedisKey,
                            this.EMTPY_AUTOCOMPLETE_RESULT_EXPIRE_SECOND,
                            TimeUnit.SECONDS
                    );
                }
            } catch (RedisConnectionFailureException exception) {
            }
        }
        else {
            autocompleteRS = this.objectMapper.readValue(autocompleteResponseBody, AutocompleteRS.class);
        }

        return autocompleteRS;
    }

    public ChangedHotelsRS changedHotels(ChangedHotelsVO changedHotelsVO) throws IOException, HttpClientErrorException {
        String changedHotelsResponseBody =
                this.restClient.get(this.CHANGED_HOTELS_PATH + "?" + this.objectMapper.convertValue(changedHotelsVO, UriFormat.class));

        return this.objectMapper.readValue(changedHotelsResponseBody, ChangedHotelsRS.class);
    }

    public CitesRS cities(CitesVO citesVO) throws IOException, HttpClientErrorException {
        String citiesResponseBody =
                this.restClient.get(this.CITIES_HOTELS_PATH + "?" + this.objectMapper.convertValue(citesVO, UriFormat.class));

        return this.objectMapper.readValue(citiesResponseBody, CitesRS.class);
    }

    public BookingDetailRS bookingDetails(BookingDetailsVO bookingDetailsVO) throws IOException, HttpClientErrorException {
        String bookingDetailsResponseBody =
                this.bookingSecureRestClinet.post(
                        BOOKING_DETAILS_PATH,
                        this.objectMapper.writeValueAsString(bookingDetailsVO)
                );

        return this.objectMapper.readValue(bookingDetailsResponseBody, BookingDetailRS.class);
    }
    private String getAuthentication() {
        return "Basic " + Base64Utils.encodeToString((BOOKING_DOT_COM_ID + ":" + BOOKING_DOT_COM_PASSWORD).getBytes());
    }

    private AutocompleteRS getGuamAutocompleteRS() {
        AutocompleteRS autocompleteRSGuam = new AutocompleteRS();

        Meta meta = new Meta();
        meta.setRuid("");
        autocompleteRSGuam.setMeta(meta);

        ArrayList results = new ArrayList();
        Result result = new Result();

        result.setName("괌");
        result.setType("country");
        result.setId("87");
        result.setLatitude("13.4443");
        result.setLongitude("144.794");
        result.setCountryName("Guam");
        result.setCountry("gu");
        result.setCityName("");
        result.setCityUfi(null);
        results.add(result);

        autocompleteRSGuam.setResult(results);

        return autocompleteRSGuam;
    }




}

