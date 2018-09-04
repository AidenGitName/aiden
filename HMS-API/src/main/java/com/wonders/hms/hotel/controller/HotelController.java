package com.wonders.hms.hotel.controller;


import com.wonders.hms.config.URIMapping;
import com.wonders.hms.hotel.service.HotelService;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.hotel.vo.HotelSearchRS;
import com.wonders.hms.user.service.AirMyReservationCheck;
import com.wonders.hms.user.service.WonderIdService;
import com.wonders.hms.user.vo.AirMyReservation;
import com.wonders.hms.wonder.vo.WonderHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(URIMapping.BASE_URI + "/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private AirMyReservationCheck airMyReservationCheck;

    @Autowired
    private WonderIdService wonderIdService;

    @GetMapping
    @ResponseBody
    public HotelSearchRS getHotelList(HotelSearch hotelSearch) throws Exception {
        try {
            Long mid = wonderIdService.getMid(hotelSearch.getMidToken());
            AirMyReservation airMyReservation = airMyReservationCheck.getNowTo1MonthAirReservationInfo(mid);

            if (airMyReservation.isValidSpecialCustom(hotelSearch)) {
                hotelSearch.setDestinationCode(airMyReservation.getCityCode());
                return getHotelListWithSpecialPrice(hotelSearch);
            }
        } catch (HttpClientErrorException httpClientErrorException) {
        } catch (IOException ioException) {
        }

        return hotelService.search(hotelSearch);
    }

    @GetMapping("/{hotelId}")
    @ResponseBody
    public WonderHotel getHotel(@PathVariable("hotelId") Long hotelId, HotelSearch hotelSearch) throws Exception {
        try {
            Long mid = wonderIdService.getMid(hotelSearch.getMidToken());
            AirMyReservation airMyReservation = airMyReservationCheck.getNowTo1MonthAirReservationInfo(mid);

            if (airMyReservation.isValidSpecialCustom(hotelSearch)) {
                hotelSearch.setDestinationCode(airMyReservation.getCityCode());
                return getHotelWithSpecialPrice(hotelId, hotelSearch);
            }
        } catch (HttpClientErrorException httpClientErrorException) {
        } catch (IOException ioException) {
        }

        return hotelService.search(hotelId, hotelSearch);
    }

//    @GetMapping("/special")
//    @ResponseBody
    public HotelSearchRS getHotelListWithSpecialPrice(HotelSearch hotelSearch) throws Exception {
        return hotelService.searchSpecialPrice(hotelSearch);
    }

//    @GetMapping("/{hotelId}/special")
//    @ResponseBody
    public WonderHotel getHotelWithSpecialPrice(Long hotelId, HotelSearch hotelSearch) throws Exception {
        return hotelService.searchSpecialPrice(hotelId, hotelSearch);
    }
}
