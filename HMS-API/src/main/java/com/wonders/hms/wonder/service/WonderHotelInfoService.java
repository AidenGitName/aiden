package com.wonders.hms.wonder.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.bookingDotCom.hotel.vo.HotelFacility;
import com.wonders.hms.bookingDotCom.hotel.vo.HotelPhoto;
import com.wonders.hms.bookingDotCom.persistence.BookingHotelInfoMapper;
import com.wonders.hms.bookingDotCom.vo.BookingAndWonderHotelIndex;
import com.wonders.hms.hotel.persistence.HotelVendorMapper;
import com.wonders.hms.wonder.persistence.WonderHotelImageMapper;
import com.wonders.hms.wonder.vo.WonderHotelImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WonderHotelInfoService {
    @Autowired
    HotelVendorMapper hotelVendorMapper;

    @Autowired
    BookingHotelInfoMapper bookingHotelInfoMapper;

    @Autowired
    WonderHotelImageMapper wonderHotelImageMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public void wonderHotelImageInsert() {
        List<BookingAndWonderHotelIndex> bookingAndWonderHotelIndices = hotelVendorMapper.getAllBookingIds();

        List<Long> bookingIds = bookingAndWonderHotelIndices.stream().map(bookingAndWonderHotelIndex ->
                bookingAndWonderHotelIndex.getBookingHotelId()
        ).collect(Collectors.toList());

        int bookingIdsSize = bookingIds.size();
        int oneStepPerIdSize = 10000;
        int totalStep = (bookingIdsSize / oneStepPerIdSize) + 1;

        for (int step = 0; step < totalStep; step++) {
            int startIndex = step * oneStepPerIdSize;
            int lastIndex = step + 1 == totalStep ? bookingIdsSize : startIndex + oneStepPerIdSize;

            List<HashMap> bookingImagesList = bookingHotelInfoMapper.getHotelImages(bookingIds.subList(startIndex, lastIndex));

            bookingImagesList.forEach(bookingImages -> {
                Optional<BookingAndWonderHotelIndex> bookingAndWonderHotelIndex = bookingAndWonderHotelIndices.stream()
                .filter(bookingAndWonderHotelIndex1 ->
                        bookingAndWonderHotelIndex1.getBookingHotelId().compareTo(Long.parseLong(bookingImages.get("booking_id").toString())) == 0
                ).findFirst();

                insertBookingImages(bookingImages, bookingAndWonderHotelIndex);
            });
            log.debug(String.format("%d is finish", step + 1));
        }

    }

    private void insertBookingImages(HashMap bookingImages, Optional<BookingAndWonderHotelIndex> bookingAndWonderHotelIndex) {
        try {
            List<HotelPhoto> hotelPhotos =
                    this.objectMapper.readValue(
                            bookingImages.get("hotel_photos").toString(),
                            new TypeReference<List<HotelPhoto>>() { }
                    );

            if(bookingAndWonderHotelIndex.isPresent()) {
                 hotelPhotos.forEach(hotelPhoto -> {
                     WonderHotelImage wonderHotelImage = new WonderHotelImage();
                     wonderHotelImage.setWonderHotelInfoId(bookingAndWonderHotelIndex.get().getWonderHotelId());
                     wonderHotelImage.setImage(hotelPhoto.getUrlOriginal());

                     wonderHotelImageMapper.insertWonderHotelImage(wonderHotelImage);
                 });
            }
        } catch (IOException e) {
            log.error(e.toString());
        }
    }
}
