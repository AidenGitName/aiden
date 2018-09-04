package com.wonders.hms.hotel.service;

import com.wonders.hms.agoda.service.AgodaHotelVendor;
import com.wonders.hms.bookingDotCom.BookingDotComHotelVendor;
import com.wonders.hms.exception.LambdaException;
import com.wonders.hms.expedia.service.ExpediaHotelVendor;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.hotel.vo.HotelSearchFilter;
import com.wonders.hms.hotel.vo.HotelVendor;
import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.service.WonderHotelSearchService;
import com.wonders.hms.wonder.service.WonderRoomInfoService;
import com.wonders.hms.wonder.vo.HotelWeightComparator;
import com.wonders.hms.wonder.vo.WonderHotel;
import com.wonders.hms.wonder.vo.WonderRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TotalHotelSearcher {

    @Autowired
    private WonderHotelSearchService wonderHotelSearchService;

    @Autowired
    private WonderRoomInfoService wonderRoomInfoService;

    @Autowired
    private AgodaHotelVendor agodaHotelVendor;

    @Autowired
    private BookingDotComHotelVendor bookingDotComHotelVendor;

    @Autowired
    private ExpediaHotelVendor expediaHotelVendor;

    private List<HotelVendor> hotelVendors = new ArrayList<>();

    @PostConstruct
    public void init() {
        hotelVendors.add(agodaHotelVendor);
        hotelVendors.add(bookingDotComHotelVendor);
        hotelVendors.add(expediaHotelVendor);
    }

    public List<WonderHotel> search(HotelSearch hotelSearch) throws Exception {
        List<WonderHotel> wonderHotelList = wonderHotelSearchService.getHotels(hotelSearch);

        if (wonderHotelList == null || wonderHotelList.size() == 0) {
            wonderHotelList = new ArrayList<>();
            return wonderHotelList;
        }

        // db wonder_hotel_info 테이블에서 hotel_vendor_index.id 값 가져옴
        List<Long> vendorIndexList = wonderHotelList.stream()
                .map(WonderHotel::getHotelVendorIndex)
                .collect(Collectors.toList());

        // 각 밴더에 위에서 구한 id값으로 방정보 가져오기
        wonderHotelList = setWonderHotelListRoom(wonderHotelList, vendorIndexList, hotelSearch, false);

        return wonderHotelList;
    }

    public WonderHotel search(Long hotelId, HotelSearch hotelSearch) throws Exception {
        WonderHotel wonderHotel = wonderHotelSearchService.getHotelByHotelId(hotelId);

        wonderHotel = setWonderHotelRoom(wonderHotel, hotelSearch, false);

        return wonderHotel;
    }

    public List<WonderHotel> searchSpecial(HotelSearch hotelSearch) throws Exception {
        List<WonderHotel> wonderHotelList = wonderHotelSearchService.getHotels(hotelSearch);

        if (wonderHotelList == null || wonderHotelList.size() == 0) {
            wonderHotelList = new ArrayList<>();
            return wonderHotelList;
        }

        // db wonder_hotel_info 테이블에서 hotel_vendor_index.id 값 가져옴
        List<Long> vendorIndexList = wonderHotelList.stream()
                .map(WonderHotel::getHotelVendorIndex)
                .collect(Collectors.toList());

        // 각 밴더에 위에서 구한 id값으로 방정보 가져오기
        wonderHotelList = setWonderHotelListRoom(wonderHotelList, vendorIndexList, hotelSearch, true);

        return wonderHotelList;
    }

    public WonderHotel searchSpecial(Long hotelId, HotelSearch hotelSearch) throws Exception {
        WonderHotel wonderHotel = wonderHotelSearchService.getHotelByHotelId(hotelId);

        wonderHotel = setWonderHotelRoom(wonderHotel, hotelSearch, true);

        return wonderHotel;
    }

    private WonderHotel setWonderHotelRoom(
            WonderHotel wonderHotel, HotelSearch hotelSearch, boolean isSpecialPrice) {

        List<Long> vendorIndexList = new ArrayList<>();
        vendorIndexList.add(wonderHotel.getHotelVendorIndex());

        hotelVendors.forEach(LambdaException.rethrow(vendor -> {
            try {
                List<CommonRoom> rooms;
                if (isSpecialPrice) {
                    rooms = vendor.getSpecialPriceRooms(vendorIndexList, hotelSearch);
                }
                else {
                    rooms = vendor.getRooms(vendorIndexList, hotelSearch);
                }

                rooms.stream().forEach(room -> {
                    wonderHotel.add(room);
                });
//                wonderRoomInfoService.insertWonderRoomsInfo(rooms);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        return wonderHotel;
    }

    private List<WonderHotel> setWonderHotelListRoom(
            List<WonderHotel> wonderHotelList, List<Long> vendorIndexList, HotelSearch hotelSearch, boolean isSpecialPrice) {
        // 각 밴더에 위에서 구한 id값으로 방정보 가져오기
        hotelVendors.parallelStream().forEach(vendor -> {
            try {
                List<CommonRoom> rooms;
                if (isSpecialPrice) {
                    rooms = vendor.getSpecialPriceRooms(vendorIndexList, hotelSearch);
                }
                else {
                    rooms = vendor.getRooms(vendorIndexList, hotelSearch);
                }

                rooms.stream().forEach(room -> {
                    wonderHotelList.stream().forEach(wonderHotel -> {
                        wonderHotel.add(room);
                    });
                });
//                wonderRoomInfoService.insertWonderRoomsInfo(rooms);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return wonderHotelList;
    }

    public List<WonderHotel> filter(List<WonderHotel> wonderHotelList, HotelSearchFilter hotelSearchFilter) {
        return wonderHotelList.stream().filter(wonderHotel -> {
            if (hotelSearchFilter == null) {
                return true;
            }

            if (hotelSearchFilter.getAmenity() != null) {
                for(String facility : hotelSearchFilter.getAmenity()){
                    if (!wonderHotel.getFacilities().contains(facility)) {
                        return false;
                    }
                }
            }

            // sold out hotel 일단은 출력
            if (wonderHotel.getRooms().size() == 0) {
                return true;
            }

            BigDecimal minRoomPrice = Collections.min(wonderHotel.getRooms(), new Comparator<WonderRoom>() {
                @Override
                public int compare(WonderRoom o1, WonderRoom o2) {
                    return o1.getTotalPrice().compareTo(o2.getTotalPrice());
                }
            }).getTotalPrice();

            if (hotelSearchFilter.getMinPrice() != null && minRoomPrice.compareTo(hotelSearchFilter.getMinPrice()) == -1) {
                return false;
            }
            if (hotelSearchFilter.getMaxPrice() != null && minRoomPrice.compareTo(hotelSearchFilter.getMaxPrice()) == 1) {
                return false;
            }

            return true;

        }).collect(Collectors.toList());
    }

    public Integer getTotalCount(HotelSearch hotelSearch) {
        return wonderHotelSearchService.getTotalCount(hotelSearch);
    }

    // sort database에서 처리
    @Deprecated
    public void sort(List<WonderHotel> wonderHotelList) {
        Collections.sort(wonderHotelList, Collections.reverseOrder(new HotelWeightComparator()));
    }

    public List<WonderHotel>  sortWithRealPrice(List<WonderHotel> wonderHotelList, boolean isDesc) {
        Comparator<WonderHotel> hotelComparator = new Comparator<WonderHotel>() {
            @Override
            public int compare(WonderHotel hotel1, WonderHotel hotel2) {
                BigDecimal minHotel1RoomPrice = Collections.min(hotel1.getRooms(), new Comparator<WonderRoom>() {
                    @Override
                    public int compare(WonderRoom o1, WonderRoom o2) {
                        return o1.getTotalPrice().compareTo(o2.getTotalPrice());
                    }
                }).getTotalPrice();

                BigDecimal minHotel2RoomPrice = Collections.min(hotel2.getRooms(), new Comparator<WonderRoom>() {
                    @Override
                    public int compare(WonderRoom o1, WonderRoom o2) {
                        return o1.getTotalPrice().compareTo(o2.getTotalPrice());
                    }
                }).getTotalPrice();

                return minHotel1RoomPrice.compareTo(minHotel2RoomPrice);
            }
        };
        List<WonderHotel> wonderHotelEmptyList =
                wonderHotelList.stream().filter(wonderHotel -> wonderHotel.getRooms().isEmpty()).collect(Collectors.toList());
        List<WonderHotel> wonderHotelNotEmptyList =
                wonderHotelList.stream().filter(wonderHotel -> !wonderHotel.getRooms().isEmpty()).collect(Collectors.toList());
        if (isDesc) {
            Collections.sort(wonderHotelNotEmptyList, Collections.reverseOrder(hotelComparator));
        }
        else {
            Collections.sort(wonderHotelNotEmptyList, hotelComparator);
        }
        wonderHotelNotEmptyList.addAll(wonderHotelEmptyList);
        return wonderHotelNotEmptyList;
    }
}
