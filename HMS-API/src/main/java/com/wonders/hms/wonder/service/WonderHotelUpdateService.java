package com.wonders.hms.wonder.service;

import com.wonders.hms.expedia.persistence.ExpediaHotelInfoMapper;
import com.wonders.hms.expedia.service.ExpediaRequestService;
import com.wonders.hms.expedia.vo.searchapi.ExApiHotel;
import com.wonders.hms.expedia.vo.searchapi.ExpediaHotelInfo;
import com.wonders.hms.hotel.domain.HotelVendorIndex;
import com.wonders.hms.hotel.persistence.HotelVendorMapper;
import com.wonders.hms.hotel.service.WonderHotelInfoMigrationService;
import com.wonders.hms.wonder.persistence.WonderHotelInfoMapper;
import com.wonders.hms.wonder.vo.WonderHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WonderHotelUpdateService {

    @Autowired
    private ExpediaHotelInfoMapper expediaHotelInfoMapper;

    @Autowired
    private WonderHotelInfoMapper wonderHotelInfoMapper;

    @Autowired
    private WonderHotelInfoMigrationService wonderHotelInfoMigrationService;

    @Autowired
    private HotelVendorMapper hotelVendorMapper;

    @Autowired
    private ExpediaRequestService expediaRequestService;

    private List<WonderHotel> wonderHotelList;

    public void hotelInfoUpdate() {
        List<ExpediaHotelInfo> newHotelList = expediaHotelInfoMapper.getAllHotels();
        wonderHotelList = wonderHotelInfoMapper.getAllHotelWithVendors();

        newHotelList.stream().forEach(newExpediaHotelInfo -> {
//            WonderHotel oldWonderHoel = wonderHotelInfoMapper.getHotelByExpediaHotelId(newExpediaHotelInfo.getPropertyId());
            WonderHotel oldWonderHotel = getWonderHotelByPropertyId(newExpediaHotelInfo.getPropertyId());

            if (oldWonderHotel == null) {
                try {
                    HotelVendorIndex hotelVendorIndex = insertNewHotelVendorIndex(newExpediaHotelInfo);
                    newHotelInsert(newExpediaHotelInfo, hotelVendorIndex);
                } catch (Exception e) {
                    e.getStackTrace();
                    return;
                }
            } else {
                if (isDifferentHotel(newExpediaHotelInfo, oldWonderHotel)) {
                    oldWonderHotel.setStar(newExpediaHotelInfo.getStarRating());
                    oldWonderHotel.setCheckin(newExpediaHotelInfo.getCheckin());
                    oldWonderHotel.setCheckout(newExpediaHotelInfo.getCheckout());
                    oldWonderHotel.setCategory(newExpediaHotelInfo.getCategory());
                    oldWonderHotel.setPhone(newExpediaHotelInfo.getPhone());
                    wonderHotelInfoMapper.updateChangedWonderHotel(oldWonderHotel);
                }
            }
        });

    }

    private void newHotelInsert(ExpediaHotelInfo newExpediaHotelInfo, HotelVendorIndex hotelVendorIndex) throws IOException {
        WonderHotel wonderHotel = new WonderHotel();
        wonderHotel.setLongitude(newExpediaHotelInfo.getLongitude());
        wonderHotel.setLatitude(newExpediaHotelInfo.getLatitude());
        wonderHotel.setHotelVendorIndex(hotelVendorIndex.getId());
        wonderHotel.setName(newExpediaHotelInfo.getName());
        wonderHotel.setNameEng(newExpediaHotelInfo.getName());
        wonderHotel.setCity(newExpediaHotelInfo.getCity());
        wonderHotel.setCityEng(newExpediaHotelInfo.getCity());
        wonderHotel.setCountry(newExpediaHotelInfo.getCountry());
        wonderHotel.setAddress(newExpediaHotelInfo.getAddress());
        wonderHotel.setCategory(newExpediaHotelInfo.getCategory());
        wonderHotel.setCheckin(newExpediaHotelInfo.getCheckin());
        wonderHotel.setCheckout(newExpediaHotelInfo.getCheckout());
        wonderHotel.setStar(newExpediaHotelInfo.getStarRating());
        wonderHotel.setPostalCode(newExpediaHotelInfo.getPostalCode());

        // api 호출필요. 일일 api 호출 제한으로 인해 추후 추가 필요 18.08.10
//        callApiRequest(newExpediaHotelInfo, wonderHotel);

        wonderHotelInfoMapper.insertWonderHotel(wonderHotel);
        wonderHotelInfoMapper.calculateWeight(wonderHotel);
    }

    private void callApiRequest(ExpediaHotelInfo newExpediaHotelInfo, WonderHotel wonderHotel) throws IOException {
        List<Long> hotelid = new ArrayList();
        hotelid.add(newExpediaHotelInfo.getPropertyId());
        List<ExApiHotel> list = expediaRequestService.callHotelInfoApi(hotelid);
        ExApiHotel newExpediaHotel = list.get(0);

        if (newExpediaHotel != null) {
            wonderHotel.setRating(newExpediaHotel.getGuestRating());
            wonderHotel.setHotelTeaser(newExpediaHotel.getDescription().getHotelTeaser());
            wonderHotel.setNumberOfReviews(newExpediaHotel.getGuestReviewCount());
            wonderHotel.setName((newExpediaHotel.getName()));
            wonderHotel.setCity(newExpediaHotel.getLocation().getAddress().getCity());
        }
    }

    private HotelVendorIndex insertNewHotelVendorIndex(ExpediaHotelInfo newExpediaHotelInfo) throws IOException {
        HotelVendorIndex hotelVendorIndex = new HotelVendorIndex();
        hotelVendorIndex.setBookingId(wonderHotelInfoMigrationService.getBookingId(newExpediaHotelInfo.getName(), newExpediaHotelInfo.getCountry()));
        hotelVendorIndex.setExpediaId(newExpediaHotelInfo.getPropertyId());
        hotelVendorMapper.insertHotelVendorIndex(hotelVendorIndex);

        return hotelVendorIndex;
    }


    private WonderHotel getWonderHotelByPropertyId(Long propertyId) {
        return wonderHotelList.stream().filter(wonderhotel -> {
            return wonderhotel.getExpediaId().compareTo(propertyId) == 0;
        }).findAny()
        .orElse(null);
    }

    private boolean isDifferentHotel(ExpediaHotelInfo newExpediaHotelInfo, WonderHotel oldWonderHoel) {
        try {
            if((newExpediaHotelInfo.getStarRating().compareTo(oldWonderHoel.getStar()) == 0)
                    && newExpediaHotelInfo.getCheckin().equals(oldWonderHoel.getCheckin())
                    && newExpediaHotelInfo.getCheckout().equals(oldWonderHoel.getCheckout())
                    && newExpediaHotelInfo.getCategory().equals(oldWonderHoel.getCategory())
                    && newExpediaHotelInfo.getPhone().equals(oldWonderHoel.getPhone())
                    ) {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }

        return true;
    }
}
