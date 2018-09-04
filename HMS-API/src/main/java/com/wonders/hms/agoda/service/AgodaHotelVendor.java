package com.wonders.hms.agoda.service;

import com.wonders.hms.agoda.persistence.AgodaHotelInfoMapper;
import com.wonders.hms.agoda.vo.AgodaAndWonderHotelIndex;
import com.wonders.hms.agoda.vo.AgodaHotel;
import com.wonders.hms.agoda.vo.AgodaHotelResult;
import com.wonders.hms.hotel.persistence.HotelVendorMapper;
import com.wonders.hms.hotel.vo.CommonHotel;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.hotel.vo.HotelVendor;
import com.wonders.hms.room.vo.CommonRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AgodaHotelVendor implements HotelVendor {

    @Autowired
    private AgodaRequestService agodaRequestService;

    @Autowired
    private AgodaHotelInfoMapper agodaHotelInfoMapper;

    @Autowired
    private HotelVendorMapper hotelVendorMapper;

    @Override
    public Collection<? extends CommonHotel> getHotels(HotelSearch hotelSearch) throws Exception {
        List<AgodaHotel> hotelList = agodaHotelInfoMapper.getHotels(hotelSearch);

        AgodaHotelResult result = agodaRequestService.getHotels(hotelSearch, hotelList);

        putRooms(hotelList, result);

        return hotelList;

    }

    @Override
    @Transactional(readOnly = true)
    public List<CommonRoom> getRooms(List<Long> vendorIndex, HotelSearch hotelSearch) throws Exception {
        List<AgodaAndWonderHotelIndex> agodaAndWonderIds = getAgodaAndWonderHotelIndex(vendorIndex);

        AgodaHotelResult agodaHotelResult = agodaRequestService.getRooms(getAgodaIdFrom(agodaAndWonderIds), hotelSearch, false);

        List<CommonRoom> resultRoomList = new ArrayList<>();
        agodaAndWonderIds.forEach(agodaNwonderIds -> {
            int stayNights = hotelSearch.getCheckin().until(hotelSearch.getCheckout()).getDays();
            int numberOfRooms = hotelSearch.getNumberOfRooms();
            resultRoomList.addAll(agodaHotelResult.getRooms(agodaNwonderIds, stayNights, numberOfRooms));
        });


        return resultRoomList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommonRoom> getSpecialPriceRooms(List<Long> vendorIndex, HotelSearch hotelSearch) throws Exception {
        List<AgodaAndWonderHotelIndex> agodaAndWonderIds = getAgodaAndWonderHotelIndex(vendorIndex);

        AgodaHotelResult agodaHotelResult = agodaRequestService.getRooms(getAgodaIdFrom(agodaAndWonderIds), hotelSearch, true);

        List<CommonRoom> resultRoomList = new ArrayList<>();
        agodaAndWonderIds.forEach(agodaNwonderIds -> {
            int stayNights = hotelSearch.getCheckin().until(hotelSearch.getCheckout()).getDays();
            int numberOfRooms = hotelSearch.getNumberOfRooms();
            resultRoomList.addAll(agodaHotelResult.getRooms(agodaNwonderIds, stayNights, numberOfRooms));
        });


        return resultRoomList;
    }

    private List<Long> getAgodaIdFrom(List<AgodaAndWonderHotelIndex> agodaAndWonderIds) {
        return agodaAndWonderIds.stream()
                    .map(AgodaAndWonderHotelIndex::getAgodaHotelId)
                    .collect(Collectors.toList());
    }

    private List<AgodaAndWonderHotelIndex> getAgodaAndWonderHotelIndex(List<Long> vendorIndex) {
        Map<String, Object> param = new HashMap<>();
        param.put("vendorIndex", vendorIndex);

        return hotelVendorMapper.getAgodaIds(param);
    }

    @Deprecated
    private void putRooms(List<AgodaHotel> hotelList, AgodaHotelResult result) {
//        hotelList.forEach(hotel -> hotel.setRooms(result.getRooms(hotel.getHotelId())));
    }
}
