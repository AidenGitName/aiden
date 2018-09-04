package com.wonders.hms.hotel.vo;

import com.wonders.hms.wonder.translateMap.HotelTypeMap;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class HotelSearchFilter {
    private List<String> categories;
    private List<Double> stars;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<String> amenity;

    public List<String> getCategories() {
        if (categories == null) {
            return null;
        }
        
        return categories.stream().map(category ->
            HotelTypeMap.typeKindToEnName.get(HotelTypeMap.koNameToTypeKind.get(category))
        ).collect(Collectors.toList());
    }
}
