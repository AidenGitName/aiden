package com.wonders.hms.expedia.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.hms.expedia.ph.vo.PHConversionData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PHReportResponse {
    @JsonProperty("total_conversion_count")
    private PHPrice totalConversionCount;

    @JsonProperty("total_publisher_commission")
    private PHPrice totalPublisherCommission;

    @JsonProperty("total_value")
    private PHPrice totalValue;

    @JsonProperty("start_date_time_utc")
    private String startDateTimeUtc;

    @JsonProperty("end_date_time_utc")
    private String endDateTimeUtc;

    @JsonProperty("start_date_time")
    private String startDateTime;

    @JsonProperty("end_date_time")
    private String endDateTime;

    @JsonProperty("limit")
    private int limit;

    @JsonProperty("count")
    private int count;

    @JsonProperty("offset")
    private int offset;

    @JsonProperty("execution_time")
    private String executionTime;

    @JsonProperty("conversions")
    private List<PHConversionData> conversions;

    @JsonProperty("meta_data")
    private List<String> medaData;
}
