package com.wonders.hms.autocomplete.controller;


import com.wonders.hms.bookingDotCom.hotelAvailability.domain.AutocompleteRS;
import com.wonders.hms.bookingDotCom.service.BookingDotComService;
import com.wonders.hms.bookingDotCom.vo.AutocompleteVO;
import com.wonders.hms.config.URIMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URIMapping.BASE_URI + "/autocomplete")
public class AutocompleteController {

    @Autowired
    private BookingDotComService bookingDotComService;

    @GetMapping
    @ResponseBody
    public AutocompleteRS autoComplete(AutocompleteVO autocompleteVO) throws Exception {
        return bookingDotComService.autocomplete(autocompleteVO);
    }
}
