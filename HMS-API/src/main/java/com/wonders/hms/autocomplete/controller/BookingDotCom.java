package com.wonders.hms.autocomplete.controller;

import com.wonders.hms.bookingDotCom.blockAvailability.domain.BlockAvailabilityRS;
import com.wonders.hms.bookingDotCom.bookingDetail.vo.BookingDetailRS;
import com.wonders.hms.bookingDotCom.cites.vo.CitesRS;
import com.wonders.hms.bookingDotCom.hotelAvailability.domain.AutocompleteRS;
import com.wonders.hms.bookingDotCom.hotel.domain.HotelsRS;
import com.wonders.hms.bookingDotCom.service.BookingDotComService;
import com.wonders.hms.bookingDotCom.vo.*;
import com.wonders.hms.bookingDotCom.hotelAvailability.domain.HotelAvailabilityRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

//@RestController
//@RequestMapping("/booking-com/")
public class BookingDotCom {
    @Autowired
    BookingDotComService bookingDotComService;

    @GetMapping("/hotel-availability")
    @ResponseBody
    public HotelAvailabilityRS hotelAvailability (
            @ModelAttribute("hotelAvailabilityVO") HotelAvailabilityVO hotelAvailabilityVO
    ) throws IOException {
        HotelAvailabilityRS hotelAvailabilityRS = bookingDotComService.hotelAvailability(hotelAvailabilityVO);

        return hotelAvailabilityRS;
    }

    @GetMapping("/block-availability")
    @ResponseBody
    public BlockAvailabilityRS blockAvailability (
            @ModelAttribute("blockAvailabilityVO") BlockAvailabilityVO blockAvailabilityVO
    ) throws IOException {
        BlockAvailabilityRS blockAvailabilityRS = bookingDotComService.blockAvailability(blockAvailabilityVO);

        return blockAvailabilityRS;
    }

    @GetMapping("/hotels")
    @ResponseBody
    public HotelsRS hotels (
            @ModelAttribute("hotelsVO")HotelsVO hotelsVO
            ) throws IOException {
        HotelsRS hotelsRS = bookingDotComService.hotels(hotelsVO);

        return hotelsRS;
    }

    @GetMapping("/autocomplete")
    @ResponseBody
    public AutocompleteRS autoComplete (
            @ModelAttribute("autocompleteVO")AutocompleteVO autocompleteVO
    ) throws IOException {
        AutocompleteRS autocompleteRS = bookingDotComService.autocomplete(autocompleteVO);

        return autocompleteRS;
    }

    @GetMapping("/cities")
    @ResponseBody
    public CitesRS cities (CitesVO citesVO) throws IOException {
        CitesRS citesRS = bookingDotComService.cities(citesVO);

        return citesRS;
    }

    @PostMapping("/booking-details")
    @ResponseBody
    public BookingDetailRS bookingDetail (BookingDetailsVO bookingDetailsVO) throws Exception {
        BookingDetailRS bookingDetailRS = bookingDotComService.bookingDetails(bookingDetailsVO);

        return bookingDetailRS;
    }
}
