package com.wonders.hms.expedia.persistence;

import com.wonders.hms.expedia.vo.ExpediaHotelAmenity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ExpediaHotelAmenityMapper {
    public List<ExpediaHotelAmenity> getAmenity(@Param("propertyId") Set<Long> propertyIds);

    public void insertAmenity(@Param("propertyId") Long propertyId, @Param("amenities") List<String> amenities);
}
