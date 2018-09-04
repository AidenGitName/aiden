package com.wonders.hms.expedia.service;

import com.wonders.hms.expedia.vo.ExXSellHotel;
import com.wonders.hms.expedia.vo.ExpCrosApiRoom;
import com.wonders.hms.expedia.vo.ExpediaAndWonderHotelIndex;
import com.wonders.hms.expedia.vo.searchapi.ExApiHotel;
import com.wonders.hms.expedia.vo.searchapi.ExApiRoomType;
import com.wonders.hms.expedia.vo.searchapi.ExHotelSearchApiResponse;
import com.wonders.hms.hotel.domain.HotelVendorIndex;
import com.wonders.hms.hotel.persistence.HotelVendorMapper;
import com.wonders.hms.hotel.vo.CommonHotel;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.hotel.vo.HotelVendor;
import com.wonders.hms.room.vo.CommonRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExpediaHotelVendor implements HotelVendor {

    @Autowired
    private HotelVendorMapper hotelVendorMapper;

    @Autowired
    private ExpediaRequestService expediaRequestService;

    @Autowired
    private ExpediaCrossSellRequestService expediaCrossSellRequestService;

    @Override
    public Collection<? extends CommonHotel> getHotels(HotelSearch hotelSearch) throws Exception {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommonRoom> getRooms(List<Long> vendorIndex, HotelSearch hotelSearch) throws Exception {
        List<ExpediaAndWonderHotelIndex> expediaAndWonderIds = getExpediaAndWonderHotelIndex(vendorIndex);

        List<Long> expediaIds = getExpediaIdFrom(expediaAndWonderIds);

        List<ExHotelSearchApiResponse> searchedList = expediaRequestService.getRoom(expediaIds, hotelSearch);

        List<CommonRoom> resultRoomList = new ArrayList<>();
        searchedList.forEach(exHotelSearchApiResponse -> {

            List<ExApiHotel> hotels = exHotelSearchApiResponse.getHotels();

            hotels.forEach(exApiHotel -> {
                List<CommonRoom> roomList = (List<CommonRoom>) exApiHotel.getRoomType(expediaAndWonderIds);
                if (roomList == null) {
                    return;
                }
                resultRoomList.addAll(roomList);
            });
        });


        return resultRoomList;
    }

    public List<CommonRoom> getSpecialPriceRooms(List<Long> vendorIndex, HotelSearch hotelSearch) throws Exception {
        List<ExpediaAndWonderHotelIndex> expediaAndWonderIds = getExpediaAndWonderHotelIndex(vendorIndex);

        List<CommonRoom> resultRoomList = getRooms(vendorIndex, hotelSearch);

        List<CommonRoom> specialRoomlist = getSpecialRooms(hotelSearch, expediaAndWonderIds);

        if (specialRoomlist != null && specialRoomlist.size() != 0) {
            resultRoomList.addAll(specialRoomlist);
        }

        return resultRoomList;
    }

    private List<CommonRoom> getSpecialRooms(HotelSearch hotelSearch, List<ExpediaAndWonderHotelIndex> expediaAndWonderIds) throws Exception {
        ExpCrosApiRoom expCrosApiRoom = expediaCrossSellRequestService.getSpecialRooms(hotelSearch);
        if (expCrosApiRoom == null) {
            return null;
        }
        expCrosApiRoom.setCheckin(hotelSearch.getCheckin());
        expCrosApiRoom.setCheckout(hotelSearch.getCheckout());
        expCrosApiRoom.setTotalPrice();

        return expCrosApiRoom.getExXSellHotel().stream()
                .map(exXSellHotel -> {
                    exXSellHotel.setHotelId(findWonderHotelId(expediaAndWonderIds, exXSellHotel.getHotelId()));
                    return exXSellHotel;
                }).
                collect(Collectors.toList());
    }

    private Long findWonderHotelId(List<ExpediaAndWonderHotelIndex> expediaAndWonderIds, Long expediaHotelId ) {
        Long wonderId = null;

        for (int i = 0; i < expediaAndWonderIds.size(); i++) {
            if (expediaAndWonderIds.get(i).getExpediaId().compareTo(expediaHotelId) == 0) {
                wonderId = expediaAndWonderIds.get(i).getWonderHotelId();
            } else {
                HotelVendorIndex hotelVendorIndex = hotelVendorMapper.getAllByExpediaId(expediaHotelId);
                wonderId = hotelVendorIndex.getId();
            }
        }

        return wonderId;
    }

    private List<Long> getExpediaIdFrom(List<ExpediaAndWonderHotelIndex> expediaAndWonderIds) {
        return expediaAndWonderIds.stream()
                .map(ExpediaAndWonderHotelIndex::getExpediaId)
                .collect(Collectors.toList());
    }

    private List<ExpediaAndWonderHotelIndex> getExpediaAndWonderHotelIndex(List<Long> vendorIndex) {
        Map<String, Object> param = new HashMap<>();
        param.put("vendorIndex", vendorIndex);

        return hotelVendorMapper.getExpediaIds(param);
    }
}
