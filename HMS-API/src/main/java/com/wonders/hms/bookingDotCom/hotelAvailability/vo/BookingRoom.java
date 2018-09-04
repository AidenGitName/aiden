package com.wonders.hms.bookingDotCom.hotelAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.type.HotelVendorKind;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;

@Getter
@Setter
public class BookingRoom {

    @JsonProperty("payment_terms")
    private PaymentTerms paymentTerms;

    @JsonProperty("refundable_until")
    private String refundableUntil;

    private boolean refundable;

    @JsonProperty("room_id")
    private Long roomId;

    @JsonProperty("breakfast_included")
    private boolean breakfastIncluded;

    @JsonProperty("room_policies")
    private ArrayList<RoomPolicy> roomPolicies;

    @JsonProperty("block_id")
    private String blockId;

    private int adults;

    @JsonProperty("cancellation_type")
    private String cancellationType;

    private ArrayList<String> children;

    @JsonProperty("breakfast_cost")
    private BigDecimal breakfastCost;

    private BigDecimal price;

    @JsonProperty("room_amenities")
    private ArrayList<String> roomAmenities;

    @JsonProperty("num_rooms_available_at_this_price")
    private BigDecimal numRoomsAvailableAtThisPrice;

    @JsonProperty("deposit_required")
    private boolean depositRequired;

    @JsonProperty("room_name")
    private String roomName;

    @JsonProperty("half_board")
    private boolean halfBoard;

    @JsonProperty("net_price")
    private BigDecimal netPrice;

    @JsonProperty("room_type_id")
    private int roomTypeId;

    @JsonProperty("all_inclusive")
    private boolean allInclusive;

    @JsonProperty("full_board")
    private boolean fullBoard;

    @JsonProperty("extra_charges")
    private ArrayList<ExtraCharge> extraCharges;

}
