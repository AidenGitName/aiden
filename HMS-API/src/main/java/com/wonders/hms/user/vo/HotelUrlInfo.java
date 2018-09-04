package com.wonders.hms.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HotelUrlInfo {
    @ApiModelProperty(required = true)
    @Size(min=3, max=3, message="City Code should have 3 characters")
    private String cityCode;

    @ApiModelProperty(required = true)
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkin;

    @ApiModelProperty(required = true)
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkout;

    @Min(1)
    @Max(18)
    private Integer adults = 1;

    @Min(1)
    @Max(9)
    private Integer numRoom = 1;

    private List<Integer> childrenAges = new ArrayList<>();

    @AssertTrue(message = "Checkout must after checkin.")
    public boolean isValidCheckinAndCheckoutDate() {
        return this.checkout.isAfter(this.checkin);
    }
}
