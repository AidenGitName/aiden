package com.wonders.hms.expedia.persistence;

import com.wonders.hms.expedia.vo.ExpediaHotelImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ExpediaHotelImageMapper {
    public List<ExpediaHotelImage> getImages(@Param("propertyId") Set<Long> propertyIds);

    public void insertImages(@Param("propertyId") Long propertyId, @Param("images")List<Map<String, String>> images);
}
