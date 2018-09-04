package com.wonders.hms.expedia.vo.searchapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.expedia.vo.ExpediaAndWonderHotelIndex;
import com.wonders.hms.room.vo.CommonRoom;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExApiHotel {

    @JsonProperty("Id")
    private Long propertyId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Location")
    private ExApiLocation location;

    @JsonProperty("GeoLocation")
    private ExApiGeoLocation geoLocation;

    @JsonProperty("Distance")
    private ExApiDistance distance;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("ThumbnailUrl")
    private String thumbnailUrl;

    @JsonProperty("Description")
    private ExApiDescription description;

    @JsonProperty("StarRating")
    private Double starRating;

    @JsonProperty("GuestRating")
    private Double guestRating;

    @JsonProperty("GuestReviewCount")
    private int guestReviewCount;

    @JsonProperty("Links")
    private ExApiLink link;

    @JsonProperty("RoomTypes")
    private List<ExApiRoomType> roomType;

    @JsonProperty("Media")
    private List<ExApiMedia> media;

    @JsonProperty("LocalCurrencyCode")
    private String localCurrencyCode;

    @JsonProperty("HotelAmenities")
    private List<ExApiHotelAmenity> hotelAmenity;


    public List<? extends CommonRoom> getRoomType(List<ExpediaAndWonderHotelIndex> expediaAndWonderIds) {
        if (this.roomType == null) {
            return null;
        }

        roomType.forEach(room -> {
            room.setHotelId(findWonderHotelId(expediaAndWonderIds));
        });

        return roomType;
    }

    private Long findWonderHotelId(List<ExpediaAndWonderHotelIndex> expediaAndWonderIds ) {
            Long wonderId = null;

            for (int i = 0; i < expediaAndWonderIds.size(); i++) {
                if (expediaAndWonderIds.get(i).getExpediaId().compareTo(this.propertyId) == 0) {
                    wonderId = expediaAndWonderIds.get(i).getWonderHotelId();
                }
            }

            return wonderId;
    }

}
