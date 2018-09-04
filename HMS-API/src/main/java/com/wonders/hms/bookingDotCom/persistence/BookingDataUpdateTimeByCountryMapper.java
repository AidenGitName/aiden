package com.wonders.hms.bookingDotCom.persistence;

import com.wonders.hms.bookingDotCom.hotel.domain.BookingDataUpdateTimeByCountry;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface BookingDataUpdateTimeByCountryMapper {
    BookingDataUpdateTimeByCountry getBookingDataUpdateTime(Long countryId);

    void updateBookingDataUpdateTime(@Param("updateTime") LocalDateTime updateTime, @Param("countryId") Long countryId);
}
