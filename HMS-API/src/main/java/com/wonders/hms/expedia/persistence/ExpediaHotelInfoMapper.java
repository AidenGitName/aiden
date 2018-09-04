package com.wonders.hms.expedia.persistence;



import com.wonders.hms.expedia.vo.searchapi.ExpediaHotelInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ExpediaHotelInfoMapper {
    List<ExpediaHotelInfo> getAllHotels();

    Boolean isExistHotel(Long propertyId);

    void updateHotelInfoFromApi(Map<String, Object> exApiHotel);

    int updateStaticInfo(com.wonders.hms.expedia.vo.staticInfo.ExpediaHotelInfo expediaHotelInfo);

    void insertNewStaticInfo(com.wonders.hms.expedia.vo.staticInfo.ExpediaHotelInfo expediaHotelInfo);

    ExpediaHotelInfo getHotelByHotelId(@Param("hotelId") Long hotelId);

    int getHotelCount();

    List<ExpediaHotelInfo> getHotelLimit(@Param("start") int start, @Param("end") int end);

    List<Long> getHotelIdsLimit(@Param("limit") int limit, @Param("offset") int offset);

    void updateMinMonPrice(@Param("propertyId") Long propertyId, @Param("minPrice") BigDecimal minPrice);

    void updateMinTuePrice(@Param("propertyId") Long propertyId, @Param("minPrice") BigDecimal minPrice);

    void updateMinWedPrice(@Param("propertyId") Long propertyId, @Param("minPrice") BigDecimal minPrice);

    void updateMinThurPrice(@Param("propertyId") Long propertyId, @Param("minPrice") BigDecimal minPrice);

    void updateMinFriPrice(@Param("propertyId") Long propertyId, @Param("minPrice") BigDecimal minPrice);
}
