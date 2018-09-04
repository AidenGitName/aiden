package com.wonders.hms.hotel.persistence;


import com.wonders.hms.agoda.vo.AgodaAndWonderHotelIndex;
import com.wonders.hms.bookingDotCom.vo.BookingAndWonderHotelIndex;
import com.wonders.hms.expedia.vo.ExpediaAndWonderHotelIndex;
import com.wonders.hms.hotel.domain.HotelVendorIndex;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HotelVendorMapper {

    public void setExpediaIndex(Map<String, Object> hotelindexs);

    public void setAgodaIndex(Map<String, Object> hotelindexs);

    public List<BookingAndWonderHotelIndex> getBookingIds(Map<String, Object> vendorIndex);

    public List<AgodaAndWonderHotelIndex> getAgodaIds(Map<String, Object> vendorIndex);

    public int getCountHotelVendorIndexWithExpedia(Long expediaId);

    public HotelVendorIndex getHotelVendorIndex(Long id);

    public void insertHotelVendorIndex(HotelVendorIndex hotelVendorIndex);

    public void updateHotelVendorIndex(HotelVendorIndex hotelVendorIndex);

    public void setNullHotelVendorId(@Param("id") Long id,@Param("column") String column);

    public List<ExpediaAndWonderHotelIndex> getExpediaIds(Map<String,Object> param);

    public List<BookingAndWonderHotelIndex> getAllBookingIds();

    public HotelVendorIndex getAllByExpediaId(Long id);

    public Long getHotelVendorIdWithBookingId(Long bookingId);
}
