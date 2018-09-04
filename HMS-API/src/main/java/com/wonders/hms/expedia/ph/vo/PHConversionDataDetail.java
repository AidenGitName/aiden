package com.wonders.hms.expedia.ph.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PHConversionDataDetail {

    @JsonProperty("conversion_id")
    private String conversionId;

    @JsonProperty("publisher_id")
    private String publisherId;

    @JsonProperty("campaign_id")
    private String campaignId;

    @JsonProperty("conversion_time")
    private String conversionTime;

    @JsonProperty("creative_type")
    private String creativeType;

    @JsonProperty("creative_id")
    private String creativeId;

    @JsonProperty("specific_creative_id")
    private String specificCreativeId;

    @JsonProperty("customer_type")
    private String customerType;

    @JsonProperty("referer_ip")
    private String refererIp;

    @JsonProperty("last_modified")
    private String lastModified;

    @JsonProperty("conversion_type")
    private String conversionType;

    @JsonProperty("ref_device_id")
    private String refDeviceId;

    @JsonProperty("ref_partnership_model_id")
    private String refPartnershipModelId;

    @JsonProperty("ref_traffic_source_id")
    private String refTrafficSourceId;

    @JsonProperty("ref_conversion_metric_id")
    private String refConversionMetricId;

    @JsonProperty("ref_user_context_id")
    private String refUserContextId;

    @JsonProperty("campaign_title")
    private String campaignTitle;

    @JsonProperty("was_disputed")
    private Boolean wasDisputed;

    @JsonProperty("publisher_name")
    private String publisherName;

    @JsonProperty("conversion_items")
    private List<PHConversionItem> conversionItems;

    @JsonProperty("publisher_reference")
    private String publisherReference;

    @JsonProperty("advertiser_reference")
    private String advertiserReference;

    @JsonProperty("conversion_reference")
    private String conversionReference;

    @JsonProperty("customer_reference")
    private String customerReference;

    @JsonProperty("source_referer")
    private String sourceReferer;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("country")
    private String country;

    @JsonProperty("ref_device")
    private String refDevice;

    @JsonProperty("ref_partnership_model")
    private String refPartnershipModel;

    @JsonProperty("ref_traffic_source")
    private String refTrafficSource;

    @JsonProperty("ref_user_context")
    private String refUserContext;

    @JsonProperty("ref_conversion_metric")
    private String refConversionMetric;

    @JsonProperty("conversion_value")
    private PHVonversionValue conversionValue;

    @JsonProperty("click")
    private PHClick click;
}
