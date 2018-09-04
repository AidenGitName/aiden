package com.wonders.hms.bookingDotCom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.bookingDotCom.blockAvailability.domain.BlockAvailabilityRS;
import com.wonders.hms.bookingDotCom.blockAvailability.vo.Block;
import com.wonders.hms.bookingDotCom.hotelAvailability.domain.HotelAvailabilityRS;
import com.wonders.hms.bookingDotCom.hotelAvailability.vo.Result;
import com.wonders.hms.bookingDotCom.roomType.vo.RoomType;
import com.wonders.hms.bookingDotCom.service.BookingDotComService;
import com.wonders.hms.bookingDotCom.vo.BlockAvailabilityVO;
import com.wonders.hms.bookingDotCom.vo.BookingAndWonderHotelIndex;
import com.wonders.hms.bookingDotCom.vo.HotelAvailabilityVO;
import com.wonders.hms.hotel.persistence.HotelVendorMapper;
import com.wonders.hms.hotel.vo.CommonHotel;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.hotel.vo.HotelVendor;
import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.util.vo.api.agent.booking.Affiliate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BookingDotComHotelVendor implements HotelVendor {
    @Value("${booking.com.room.type.resource}")
    private String BOOKING_DOT_COM_ROOM_TYPE_RESOURCE;

    private int BOOKING_DOT_COM_AFFILIATE_RESERVATION;

    @Autowired
    private BookingDotComService bookingDotComService;

    @Autowired
    private HotelVendorMapper hotelVendorMapper;

    @Autowired
    private Affiliate bookingAffliate;


    private Map<Integer, String> roomTypes;

    @PostConstruct
    public void init() {


        this.BOOKING_DOT_COM_AFFILIATE_RESERVATION = bookingAffliate.getReservation();

        try {
            ClassPathResource classPathResource = new ClassPathResource(BOOKING_DOT_COM_ROOM_TYPE_RESOURCE);
            File bookingDotComRoomTypesJsonFile = classPathResource.getFile();
            ObjectMapper objectMapper = new ObjectMapper();

            RoomType roomType = objectMapper.readValue(bookingDotComRoomTypesJsonFile, RoomType.class);
            roomTypes = roomType.getResult().stream().collect(Collectors.toMap(
                    com.wonders.hms.bookingDotCom.roomType.vo.Result::getRoomTypeId,
                    result -> result.getTranslations().get(0).getName()
                    )
            );
        } catch (IOException ioException) {
            log.error("booking.com room type json file parse error");
        }
    }

    @Override
    public Collection<? extends CommonHotel> getHotels(HotelSearch hotelSearch) throws IOException, HttpClientErrorException {
        HotelAvailabilityVO hotelAvailabilityVO = this.convertHotelSearch(hotelSearch);
        HotelAvailabilityRS hotelAvailabilityRS = this.bookingDotComService.hotelAvailability(hotelAvailabilityVO);

        List<BookingDotComHotel> bookingDotComWonderCommonHotels = this.convertHotelAvailability(hotelAvailabilityRS);

        bookingDotComWonderCommonHotels = this.setRoomData(bookingDotComWonderCommonHotels, hotelSearch);

        return bookingDotComWonderCommonHotels;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommonRoom> getRooms(List<Long> vendorIndex, HotelSearch hotelSearch) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("vendorIndex", vendorIndex);
        List<BookingAndWonderHotelIndex> bookingAndWonderHotelIndices = hotelVendorMapper.getBookingIds(param);

        List<Long> bookingIds = bookingAndWonderHotelIndices.stream()
                .map(bookingAndWonderHotelIndex -> bookingAndWonderHotelIndex.getBookingHotelId())
                .collect(Collectors.toList());

        BlockAvailabilityVO blockAvailabilityVO = this.convertBlockSearch(hotelSearch, (ArrayList) bookingIds);
        BlockAvailabilityRS blockAvailabilityRS = this.bookingDotComService.blockAvailability(blockAvailabilityVO);

        int stayNight = (int) ChronoUnit.DAYS.between(hotelSearch.getCheckin(), hotelSearch.getCheckout());

        List<CommonRoom> commonRooms = new ArrayList<>();

        for(com.wonders.hms.bookingDotCom.blockAvailability.vo.Result result : blockAvailabilityRS.getResults()) {
            for (Block block: result.getBlocks()) {
                // single room only one person
                int notFreeChildren = 0;
                for (Integer age : hotelSearch.getAgesOfChildren()) {
                    if (age.compareTo(block.getMaxChildrenFree()) == 1) {
                        notFreeChildren++;
                    }
                }

                int roomPerCustom = ( hotelSearch.getNumberOfAdults() + notFreeChildren )
                        / hotelSearch.getNumberOfRooms();
                if (( hotelSearch.getNumberOfAdults() + notFreeChildren ) % hotelSearch.getNumberOfRooms() != 0) {
                    roomPerCustom++;
                }

                int roomPerChildren = (hotelSearch.getNumberOfChildren() - notFreeChildren)
                        / hotelSearch.getNumberOfRooms();
                if ((hotelSearch.getNumberOfChildren() - notFreeChildren) % hotelSearch.getNumberOfRooms() != 0) {
                    roomPerChildren++;
                }

                if (block.getMaxOccupancy() < roomPerCustom || block.getMaxChildrenFree() < roomPerChildren ) {
                    continue;
                }
                Optional<BookingAndWonderHotelIndex> hotelVendorIndex = searchHotelVendorIndex(bookingAndWonderHotelIndices, result.getHotelId());

                if (hotelVendorIndex.isPresent()) {
                    String detailUrl = createDetailUrl(
                            result.getHotelId(),
                            result.getCheckin(),
                            stayNight,
                            block.getBlockId(),
                            hotelSearch.getNumberOfRooms()
                    );
                    String listUrl = result.getHotelUrl();

                    commonRooms.add(
                            new BookingDotComWonderCommonRoom(
                                    block,
                                    hotelVendorIndex.get().getWonderHotelId(),
                                    detailUrl,
                                    listUrl,
                                    stayNight,
                                    roomTypes.get(block.getRoomTypeId()),
                                    hotelSearch.getNumberOfRooms()
                            )
                    );
                }
            }
        }

        return commonRooms;
    }

    // booking.com은 특가 없음
    public List<CommonRoom> getSpecialPriceRooms(List<Long> vendorIndex, HotelSearch hotelSearch) throws Exception {
        return getRooms(vendorIndex, hotelSearch);
    }

    private static Optional<BookingAndWonderHotelIndex> searchHotelVendorIndex(List<BookingAndWonderHotelIndex> bookingAndWonderHotelIndices, Long bookingHotelId) {
        return bookingAndWonderHotelIndices.stream()
                .filter(
                        bookingAndWonderHotelIndex ->
                                bookingAndWonderHotelIndex.getBookingHotelId().compareTo(bookingHotelId) == 0
                ).findFirst();
    }

    @Deprecated
    private String createListUrl(Long hotelId, LocalDate checkin, int stayNight) {
        return String.format(
                "https://secure.booking.com/book.html?" +
                        "aid=%d&hostname=www.booking.com"+
                        "&hotel_id=%d&stage=0&checkin=%s"+
                        "&interval=%d&label="
                , this.BOOKING_DOT_COM_AFFILIATE_RESERVATION, hotelId
                , checkin, stayNight
        );
    }

    private String createDetailUrl(Long hotelId, LocalDate checkin, int stayNight, String blockId, int numberOfRooms) {
        return String.format(
                "https://secure.booking.com/book.html?" +
                        "aid=%d&hostname=www.booking.com"+
                        "&hotel_id=%d&stage=1&checkin=%s"+
                        "&interval=%d&nr_rooms_%s=%d&label="
                , this.BOOKING_DOT_COM_AFFILIATE_RESERVATION, hotelId
                , checkin, stayNight, blockId, numberOfRooms
        );
    }

    @Deprecated
    private List<BookingDotComHotel> setRoomData(List<BookingDotComHotel> bookingDotComWonderCommonHotels, HotelSearch hotelSearch) throws IOException{
        ArrayList<Long> hotelIds = new ArrayList();

        for(BookingDotComHotel bookingDotComWonderCommonHotel: bookingDotComWonderCommonHotels) {
            hotelIds.add(bookingDotComWonderCommonHotel.getHotelId());
        }

        BlockAvailabilityVO blockAvailabilityVO = this.convertBlockSearch(hotelSearch, hotelIds);
        BlockAvailabilityRS blockAvailabilityRS = this.bookingDotComService.blockAvailability(blockAvailabilityVO);

        for(com.wonders.hms.bookingDotCom.blockAvailability.vo.Result result : blockAvailabilityRS.getResults()) {
            int hotelIdIndex = hotelIds.indexOf( new Long(result.getHotelId()) );
            List<CommonRoom> bookingDotComWonderCommonRooms = new ArrayList<>();

            for (Block block: result.getBlocks()) {

                bookingDotComWonderCommonRooms.add(
                        new BookingDotComWonderCommonRoom(
                                block, null, null, null,0, null, 0)
                );
            }

            bookingDotComWonderCommonHotels.get(hotelIdIndex).setRooms(bookingDotComWonderCommonRooms);
        }

        return bookingDotComWonderCommonHotels;
    }

    private HotelAvailabilityVO convertHotelSearch(HotelSearch hotelSearch) {
        HotelAvailabilityVO hotelAvailabilityVO = new HotelAvailabilityVO();

        hotelAvailabilityVO.setPlaceInformation(hotelSearch.getPlace());

        hotelAvailabilityVO.setCheckin(hotelSearch.getCheckin());
        hotelAvailabilityVO.setCheckout(hotelSearch.getCheckout());

        hotelAvailabilityVO.setRooms(
                hotelSearch.getNumberOfAdults(),
                hotelSearch.getAgesOfChildren(),
                hotelSearch.getNumberOfRooms()
        );

        return hotelAvailabilityVO;
    }

    private static BlockAvailabilityVO convertBlockSearch(HotelSearch hotelSearch, ArrayList hotelIds) {
        BlockAvailabilityVO blockAvailabilityVO = new BlockAvailabilityVO();

        blockAvailabilityVO.setHotelIds(hotelIds);
        blockAvailabilityVO.setCheckin(hotelSearch.getCheckin());
        blockAvailabilityVO.setCheckout(hotelSearch.getCheckout());
        blockAvailabilityVO.setRooms(
                hotelSearch.getNumberOfAdults(),
                hotelSearch.getAgesOfChildren(),
                hotelSearch.getNumberOfRooms()
        );

        return blockAvailabilityVO;
    }

    private static List convertHotelAvailability(HotelAvailabilityRS hotelAvailabilityRS) {
        ArrayList<Result> results = hotelAvailabilityRS.getResult();
        ArrayList<BookingDotComHotel> bookingDotComHotels = new ArrayList<>();

        for(Result result : results) {
            bookingDotComHotels.add(new BookingDotComHotel(result));
        }
        return bookingDotComHotels;
    }
}
