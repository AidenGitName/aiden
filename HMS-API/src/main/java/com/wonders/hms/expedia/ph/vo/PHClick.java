package com.wonders.hms.expedia.ph.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PHClick {

    @JsonProperty("campaign_id")
    private String campaignId;

    @JsonProperty("publisher_id")
    private String publisherId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("status")
    private String status;

    @JsonProperty("set_time")
    private String setTime;

    @JsonProperty("set_ip")
    private String setIp;

    @JsonProperty("last_used")
    private String lastUsed;

    @JsonProperty("last_ip")
    private String lastIp;

    @JsonProperty("creative_id")
    private String creativeId;

    @JsonProperty("creative_type")
    private String creativeType;

    @JsonProperty("specific_creative_id")
    private String specificCreativeId;

    @JsonProperty("ref_device_id")
    private String refDeviceId;

    @JsonProperty("ref_partnership_model_id")
    private String refPartnershipModelId;

    @JsonProperty("ref_traffic_source_id")
    private String refTrafficSourceId;
}
