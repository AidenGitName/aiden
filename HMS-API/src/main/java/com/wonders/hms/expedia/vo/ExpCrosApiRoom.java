package com.wonders.hms.expedia.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.room.vo.CommonRoom;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Setter
@Getter
public class ExpCrosApiRoom {

    @JsonProperty("success")
    private String success;

    @JsonProperty("destinationName")
    private String destinationName;

    @JsonProperty("destinationDeepLink")
    private String destinationDeepLink;

    @JsonProperty("numOfSecondsLeft")
    private String numOfSecondsLeft;

    @JsonProperty("xsellHotels")
    private List<ExXSellHotel> exXSellHotel;

    @JsonProperty("xSellError")
    private ExXSellError xSellError;

    private LocalDate checkin;

    private LocalDate checkout;

    public void setTotalPrice() {
        exXSellHotel.stream().forEach(xSellHotel -> {
            int days = Period.between(checkin, checkout).getDays();
            BigDecimal oneNight = xSellHotel.getPricePerNight();

            BigDecimal totalPrice  = oneNight.multiply(BigDecimal.valueOf(days));
            xSellHotel.setTotalPrice(totalPrice);
        });
    }


}
