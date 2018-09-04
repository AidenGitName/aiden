package com.wonders.hms.expedia.ph.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.user.type.BookStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PHConversionItem {

    @JsonProperty("conversion_item_id")
    private String conversionItemId;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("category")
    private String category;

    @JsonProperty("item_value")
    private BigDecimal itemValue;

    @JsonProperty("item_publisher_commission")
    private BigDecimal itemPublisherCommission;

    @JsonProperty("item_status")
    private String itemStatus;

    @JsonProperty("last_update")
    private String lastUpdate;

    @JsonProperty("publisher_self_bill_id")
    private String publisherSelfBillId;

    @JsonProperty("reject_reason")
    private String rejectReason;

    @JsonProperty("item_status_id")
    private String itemStatusId;

    @JsonProperty("approved_at")
    private String approvedAt;

    @JsonProperty("payable")
    private Boolean payble;

    @JsonProperty("meta_data")
    private PHMetaData metaData;

    public BookStatus getItemStatus() {
        if ("approved".equals(this.itemStatus)) return BookStatus.BOOKED;
        else if ("pending".equals(this.itemStatus)) return BookStatus.BOOKED;
        else if ("rejected".equals(this.itemStatus)) return BookStatus.CANCEL;

        return BookStatus.BOOKING;
    }
}
