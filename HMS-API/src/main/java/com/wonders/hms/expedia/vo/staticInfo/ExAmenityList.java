package com.wonders.hms.expedia.vo.staticInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExAmenityList {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("4003")
    private ExAmenity luggageStorage;

    @JsonProperty("324")
    private ExAmenity coffeeInCommonArea;

    @JsonProperty("8")
    private ExAmenity elevator;

    @JsonProperty("361")
    private ExAmenity diningBreakfast;

    @JsonProperty("9")
    private ExAmenity recreationFitness;

    @JsonProperty("2063")
    private ExAmenity unlimitedHoursFrontDesk;

    @JsonProperty("2065")
    private ExAmenity businessCenter;

    @JsonProperty("81")
    private ExAmenity conferenceSpace;

    @JsonProperty("369")
    private ExAmenity laundryFacilities;

    @JsonProperty("2387")
    private ExAmenity toursTicketAssistance;

    @JsonProperty("19")
    private ExAmenity diningRestaurant;

    @JsonProperty("2131")
    private ExAmenity meetingRooms;

    @JsonProperty("2070")
    private ExAmenity dryCleaningService;

    @JsonProperty("2390")
    private ExAmenity freeWifi;

    @JsonProperty("3862")
    private ExAmenity surchargeParkingSelf;

    @JsonProperty("2392")
    private ExAmenity wiredInternetFree;

    @JsonProperty("2137")
    private ExAmenity smokeFreeProperty;

    @JsonProperty("2043")
    private ExAmenity multilingualStaff;

    @JsonProperty("321")
    private ExAmenity barbecueGrill;

    @JsonProperty("4456")
    private ExAmenity reservationParkingOffsite;

    @JsonProperty("2064")
    private ExAmenity limitedHoursFrontDesk;

    // pool amenity start
    @JsonProperty("4438")
    private ExAmenity poolSunLoungers;
    @JsonProperty("4436")
    private ExAmenity poolCabanasFree;
    @JsonProperty("4435")
    private ExAmenity poolCabanasSurcharge;
    @JsonProperty("2352")
    private ExAmenity poolUmbrellas;
    @JsonProperty("24")
    private ExAmenity outdoorPool;
    @JsonProperty("2820")
    private ExAmenity recreationNumberIndoorPools;
    @JsonProperty("2821")
    private ExAmenity recreationNumberOutdoorPools;
    @JsonProperty("2074")
    private ExAmenity recreationPool;
    @JsonProperty("2014")
    private ExAmenity recreationPoolChildren;
    @JsonProperty("14")
    private ExAmenity recreationPoolIndoor;
    @JsonProperty("1073742768")
    private ExAmenity recreationPoolIndoorSurcharge;
    @JsonProperty("1073743549")
    private ExAmenity recreationPoolOpen24hours;
    @JsonProperty("1073742769")
    private ExAmenity recreationPoolOutdoorSurcharge;
    @JsonProperty("2138")
    private ExAmenity outdoorSeasonalPool;
    // pool amenity finisy


    @JsonProperty("2353")
    private ExAmenity airportTransportation;

    @JsonProperty("2129")
    private ExAmenity massageSpa;


    public List<String> getList() {
        List<String> list = new ArrayList<>();

        // 문자열 변환 시키기
        if (luggageStorage != null) list.add("Luggage storage");
        if (coffeeInCommonArea != null) list.add("Coffee/tea in common areas");
        if (elevator != null) list.add("Elevator/lift");
        if (diningBreakfast != null) list.add("Dining-Breakfast");
        if (recreationFitness != null) list.add("Recreation-Fitness facilities");
        if (unlimitedHoursFrontDesk != null) list.add("24-hour front desk");
        if (businessCenter != null) list.add("Business center");
        if (conferenceSpace != null) list.add("Conference space");
        if (laundryFacilities != null) list.add("Laundry Facilities");
        if (toursTicketAssistance != null) list.add("Tours/ticket assistance");
        if (diningRestaurant != null) list.add("Dining-Restaurant");
        if (meetingRooms != null) list.add("Meeting rooms");
        if (dryCleaningService != null) list.add("Dry cleaning/laundry service");
        if (freeWifi != null) list.add("Wireless Internet access-free");
        if (surchargeParkingSelf != null) list.add("Parking - self (surcharge)");
        if (wiredInternetFree != null) list.add("Wired Internet-free");
        if (smokeFreeProperty != null) list.add("Smoke-free property");
        if (multilingualStaff != null) list.add("Multilingual staff");
        if (barbecueGrill != null) list.add("Barbecue grill(s)");
        if (reservationParkingOffsite != null) list.add("Parking - offsite (reservations)");
        if (limitedHoursFrontDesk != null) list.add("Front desk (limited hours)");
        if (isExistPool()) list.add("Pool sun loungers");
        if (airportTransportation != null) list.add("Airport transportation");
        if (massageSpa != null) list.add("Massage-spa treatment room(s)");

        return list;
    }

    private boolean isExistPool() {
        return poolSunLoungers != null
                || outdoorPool != null
                || outdoorSeasonalPool != null
                || poolCabanasFree != null
                || poolCabanasSurcharge != null
                || poolUmbrellas != null
                || recreationNumberIndoorPools != null
                || recreationNumberOutdoorPools != null
                || recreationPool != null
                || recreationPoolChildren != null
                || recreationPoolIndoor != null
                || recreationPoolIndoorSurcharge != null
                || recreationPoolOpen24hours != null
                || recreationPoolOutdoorSurcharge != null;
    }
}
