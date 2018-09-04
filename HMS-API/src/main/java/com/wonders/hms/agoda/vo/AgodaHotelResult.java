package com.wonders.hms.agoda.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.room.vo.CommonRoom;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class AgodaHotelResult {

    @JsonProperty("searchid")
    private String searchId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("Hotels")
    private ArrayList<AgodaHotelResponse> hotels;

    @JsonProperty("ErrorMessages")
    private AgodaErrorMessage agodaErrorMessage;

    public List<? extends CommonRoom> getRooms(AgodaAndWonderHotelIndex agodaAndWonderHotelIndex, int stayNights, int numberOfRooms) {
        return hotels.stream()
                .filter(agodaHotelResponse -> agodaHotelResponse.getId().compareTo(agodaAndWonderHotelIndex.getAgodaHotelId()) == 0)
                .findAny()
                .map(hotel -> {
                    List<AgodaRoom> roomList = hotel.getRooms();
                    roomList.forEach(room -> {
                        room.setStayNights(stayNights);
                        room.setNumberOfRooms(numberOfRooms);
                        room.setHotelId(agodaAndWonderHotelIndex.getWonderHotelId());
                    });
                    return roomList;
                })
                .orElse(new ArrayList<>());
    }
}
