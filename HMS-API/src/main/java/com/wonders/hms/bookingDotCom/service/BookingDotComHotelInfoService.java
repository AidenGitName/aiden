package com.wonders.hms.bookingDotCom.service;

import com.wonders.hms.bookingDotCom.changedHotel.domain.ChangedHotelsRS;
import com.wonders.hms.bookingDotCom.changedHotel.vo.ChangedHotel;
import com.wonders.hms.bookingDotCom.hotel.domain.BookingDataUpdateTimeByCountry;
import com.wonders.hms.bookingDotCom.hotel.domain.HotelsRS;
import com.wonders.hms.bookingDotCom.persistence.BookingDataUpdateTimeByCountryMapper;
import com.wonders.hms.bookingDotCom.persistence.BookingHotelInfoMapper;
import com.wonders.hms.bookingDotCom.vo.ChangedHotelsVO;
import com.wonders.hms.bookingDotCom.vo.HotelsVO;
import com.wonders.hms.hotel.persistence.HotelVendorMapper;
import com.wonders.hms.place.domain.Country;
import com.wonders.hms.place.persistence.CountryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BookingDotComHotelInfoService {
    @Autowired
    BookingDotComService bookingDotComService;

    @Autowired
    BookingHotelInfoMapper bookingHotelInfoMapper;

    @Autowired
    CountryMapper countryMapper;

    @Autowired
    BookingDataUpdateTimeByCountryMapper bookingDataUpdateTimeByCountryMapper;

    @Autowired
    HotelVendorMapper hotelVendorMapper;

    public void insertHotelData() throws Exception {
        List<Country> countries = countryMapper.getCountries();

        countries.forEach(country -> {
            try {
                BookingDataUpdateTimeByCountry bookingDataUpdateTimeByCountry =
                        bookingDataUpdateTimeByCountryMapper.getBookingDataUpdateTime(country.getId());
                if (bookingDataUpdateTimeByCountry.getUpdateTime() != null) {
                    return;
                }

                int rows = 1000;
                int offeset = 1000;
                HotelsVO hotelsVO = getHotelsVO(country.getCountryCode().toLowerCase(), rows);

                bookingDataUpdateTimeByCountryMapper.updateBookingDataUpdateTime(LocalDateTime.now(), country.getId());

                while (true) {
                    hotelsVO.setOffset(offeset);
                    HotelsRS hotelsRS = null;
                    try {
                        hotelsRS = this.bookingDotComService.hotels(hotelsVO);
                    } catch (Exception e) {
                        System.out.println(country.getCountryCode() + "," + rows + "," + offeset);
                    }

                    hotelsRS.getResult().forEach(result -> {
                        try {
                            bookingHotelInfoMapper.insertHotel(result.getHotelId(), result.getHotelData());
                        } catch (DuplicateKeyException e) {
                            System.out.println("booking.com hotel id duplicate : " + result.getHotelId());
                        }
                    });

                    if (hotelsRS.getResult().size() < 1000) {
                        break;
                    }
                    offeset += rows;
                }

                System.out.println(country.getCountry() + "is finish!! ");
            }
            catch (Exception e) {
                System.out.println(e.toString());
                System.out.println(country.getCountry() + "is error!!!");
            }
        });
    }

    public void updateBookingHotelInfo() {
        List<Country> countries = countryMapper.getCountries();

        countries.forEach(country -> {
            BookingDataUpdateTimeByCountry bookingDataUpdateTimeByCountry =
                    bookingDataUpdateTimeByCountryMapper.getBookingDataUpdateTime(country.getId());

            // changedHotels api : There is a limit to this timestamp to two days in the past.
            if(twoDaysPrevious(bookingDataUpdateTimeByCountry.getUpdateTime())) {
                updateNewHotelData(country);
            }
            else {
                updateChangeHotelData(country, bookingDataUpdateTimeByCountry.getUpdateTime());
            }
        });
    }

    private boolean twoDaysPrevious(LocalDateTime updateDateTime) {
        int betweenDay = (int) ChronoUnit.DAYS.between(updateDateTime, LocalDateTime.now());

        return betweenDay > 2;
    }

    private void updateNewHotelData(Country country) {
        int rows = 1000;
        int offeset = 1000;
        int restClinetExceptionTry = 0;

        HotelsVO hotelsVO = getHotelsVO(country.getCountryCode().toLowerCase(), rows);

        while (true) {
            hotelsVO.setOffset(offeset);
            HotelsRS hotelsRS = null;
            try {
                hotelsRS = this.bookingDotComService.hotels(hotelsVO);
                if (hotelsRS.getResult().isEmpty()) {
                    break;
                }

                hotelsRS.getResult().forEach(result -> {
                    bookingHotelInfoMapper.updateHotel(result.getHotelId(), result.getHotelData());
                });

                if (hotelsRS.getResult().size() < 1000) {
                    break;
                }

                offeset += rows;
            }
            catch (RestClientException rest) {
                if (restClinetExceptionTry == 0) {
                    restClinetExceptionTry++;
                    continue;
                }
                break;

            }
            catch (Exception e) {
                System.out.println(country.getCountryCode() + "," + rows + "," + offeset);
            }
        }

        bookingDataUpdateTimeByCountryMapper.updateBookingDataUpdateTime(LocalDateTime.now(), country.getId());
    }

    private HotelsVO getHotelsVO(String countryCodeLowerCase, int rows) {
        HotelsVO hotelsVO = new HotelsVO();

        ArrayList<String> countryCodes = new ArrayList<>();
        countryCodes.add(countryCodeLowerCase);

        hotelsVO.setCountryIds(countryCodes);

        hotelsVO.setRows(rows);

        return hotelsVO;
    }

    private void updateChangeHotelData(Country country, LocalDateTime previousUpdateTime) {
        ChangedHotelsVO changedHotelsVO = new ChangedHotelsVO();

        ArrayList<String> countryCodes = new ArrayList<>();
        countryCodes.add(country.getCountryCode().toLowerCase());

        changedHotelsVO.setCountries(countryCodes);
        changedHotelsVO.setLastChange(previousUpdateTime);

        ChangedHotelsRS changedHotelsRS = null;
        try {
            changedHotelsRS = bookingDotComService.changedHotels(changedHotelsVO);
        } catch (IOException e) {
            System.out.println("io exception " + e.toString());
        }

        changeHotelUpdate(changedHotelsRS.getResult().getChangedHotels());

        closeHotelsDelete(changedHotelsRS.getResult().getClosedHotels());

        bookingDataUpdateTimeByCountryMapper.updateBookingDataUpdateTime(LocalDateTime.now(), country.getId());
    }

    private void changeHotelUpdate(List<ChangedHotel> changedHotels) {
        if (changedHotels.isEmpty()) {
            return;
        }
        changedHotels.forEach(changedHotel -> {
            Long changedHotelId = changedHotel.getHotelId();
            HotelsVO hotelsVO = new HotelsVO();

            ArrayList<Long> hotels = new ArrayList();
            hotels.add(changedHotelId);

            hotelsVO.setHotelIds(hotels);
            hotelsVO.setExtras(changedHotel.getChanges());

            try {
                HotelsRS hotelsRS = bookingDotComService.hotels(hotelsVO);
                if (hotelsRS.getResult().get(0).getHotelData() != null) {
                    bookingHotelInfoMapper.updateHotel(changedHotelId, hotelsRS.getResult().get(0).getHotelData());
                }
            } catch (IOException e) {
                System.out.println("change hotel requets exception" + changedHotelId);
            }
        });
    }

    private void closeHotelsDelete(List<Long> closedHotels){
        if (closedHotels.isEmpty()) {
            return;
        }
        closedHotels.forEach(closedHotel -> {
            bookingHotelInfoMapper.deleteHotel(closedHotel);
//                    hotelVendorMapper.setNullHotelVendorId(closedHotel, "boooking_id");
        });
    }
}
