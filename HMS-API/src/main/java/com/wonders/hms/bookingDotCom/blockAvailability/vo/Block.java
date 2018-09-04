package com.wonders.hms.bookingDotCom.blockAvailability.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.bookingDotCom.hotelAvailability.vo.PaymentTerms;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Block {

    @JsonProperty("refundable_until")
    private String refundableUntil;

    private String taxes;

    @JsonProperty("breakfast_cost")
    private BreakfastCost breakfasetCost;

    @JsonProperty("max_children_free_age")
    private int maxChildrenFreeAge;

    @JsonProperty("all_inclusive")
    private boolean allInclusive;

    @JsonProperty("block_policies")
    private ArrayList<BlockPolicy> blockPolicies;

    @JsonProperty("cot_available")
    private boolean cotAvailable;

    @JsonProperty("dinner_included")
    private boolean dinnerIncluded;

    @JsonProperty("room_type_id")
    private int roomTypeId;

    @JsonProperty("rack_rate")
    private RackRate rackRate;

    @JsonProperty("cc_required")
    private boolean ccRequired;

    @JsonProperty("internet_included")
    private boolean internetIncluded;

    @JsonProperty("lunch_included")
    private boolean lunchIncluded;

    @JsonProperty("is_china_pos_rate")
    private boolean isChinaPosRate;

    private ArrayList<Photo> photos;

    @JsonProperty("incremental_price")
    private ArrayList<IncrementalPrice> incrementalPrices;

    private ArrayList<String> facilities;

    @JsonProperty("bed_configurations")
    private ArrayList<BedConfiguration> bedConfigurations;

    @JsonProperty("last_minute_deal_percentage")
    private int lastMinuteDealPercentage;

    private String name;

    @JsonProperty("block_id")
    private String blockId;

    @JsonProperty("cancellation_type")
    private String cancellationType;

    @JsonProperty("free_wifi")
    private boolean freeWifi;

    @JsonProperty("is_last_minute_deal")
    private boolean isLastMinuteDeal;

    private int smoking;

    @JsonProperty("extra_bed_available")
    private ExtraBedAvailable extraBedAvailable;

    @JsonProperty("breakfast_included")
    private boolean breakfastIncluded;

    @JsonProperty("full_board")
    private boolean fullBoard;

    @JsonProperty("half_board")
    private boolean halfBoard;

    @JsonProperty("payment_terms")
    private PaymentTerms paymentTerms;

    @JsonProperty("max_children_free")
    private int maxChildrenFree;

    @JsonProperty("room_surface_in_feet2")
    private double roomSurfaceInFeet2;

    @JsonProperty("is_flash_deal")
    private boolean isFlashDeal;

    @JsonProperty("cancellation_info")
    private ArrayList<CancellationInfo> cancellationInfos;

    @JsonProperty("number_of_rooms_left")
    private int numberOfRoomsLeft;

    @JsonProperty("room_description")
    private String roomDescription;

    @JsonProperty("max_occupancy")
    private int maxOccupancy;

    @JsonProperty("room_id")
    private int roomId;

    private ArrayList<Addon> addons;

    @JsonProperty("deposit_amount")
    private DepositAmount depositAmount;


    @JsonProperty("min_price")
    private MinPrice minPrices;

    @JsonProperty("mealplan_description")
    private String mealplanDescription;

    @JsonProperty("room_surface_in_m2")
    private int roomSurfaceInM2;

    @JsonProperty("just_booked")
    private boolean justBooked;

    private boolean refundable;

    @JsonProperty("is_smart_deal")
    private boolean isSmartDeal;
}
