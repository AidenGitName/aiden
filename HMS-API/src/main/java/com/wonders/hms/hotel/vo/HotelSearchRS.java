package com.wonders.hms.hotel.vo;

import com.wonders.hms.wonder.vo.WonderHotel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HotelSearchRS {
    private Integer totalCount;
    private List<WonderHotel> searchedCommonHotels;
}
