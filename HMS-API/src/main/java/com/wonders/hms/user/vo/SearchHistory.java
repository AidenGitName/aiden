package com.wonders.hms.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wonders.hms.user.type.BookStatus;
import com.wonders.hms.wonder.type.HotelVendorKind;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonPropertyOrder(value = {
        "uuid", "mId", "status", "exitedDate", "statusUpdatedDate",
        "price", "searchedPlace", "checkin", "checkout", "numAdult", "numRoom",
        "numChildren", "childrenAge", "resultUrl",
        "vendor", "reservationId", "reservationVendorUrl",
        "wonderHotelInfoId", "realHotelName", "realCityName",
        "realCheckOut", "realCheckin", "realPrice"
})
@ToString
public class SearchHistory {
    @JsonIgnore
    private Long id;

    private String uuid;
    private Long mId;
    @JsonIgnore
    private BookStatus status;
    private LocalDateTime exitedDate;
    private LocalDateTime statusUpdatedDate;
    private BigDecimal price;
    private String searchedPlace;
    private String checkin;
    private String checkout;
    private Integer numAdult;
    private Integer numRoom;
    private Integer numChildren;
    private String childrenAge;
    private String resultUrl;
    private HotelVendorKind vendor;
    private Long reservationId;
    private String reservationVendorUrl;
    private Long wonderHotelInfoId;

    // real fild: 사용자가 실제로 결제한 정보. searching한 결과와 실제 결제한 정보가 다를 수 있음
    private String realHotelName;
    private String realCityName;
    private String realCheckOut;
    private String realCheckin;
    private BigDecimal realPrice;
    private String realHotelAddress;
    private String realHotelPhon;
    private Double realLatitude;
    private Double realLongitude;

    @JsonIgnore
    private LocalDateTime exitedDateFrom;

    @JsonIgnore
    private LocalDateTime exitedDateTo;

}
