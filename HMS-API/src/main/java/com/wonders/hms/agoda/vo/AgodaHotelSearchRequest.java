package com.wonders.hms.agoda.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.wonders.hms.util.KMSProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@XmlRootElement(name = "AvailabilityRequestV2")
@Component
public class AgodaHotelSearchRequest {
    @JacksonXmlProperty(isAttribute = true, localName = "siteid")
    private String siteId;

    @JacksonXmlProperty(isAttribute = true, localName = "apikey")
    private String apiKey;

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns")
    @Value("${agoda.account.xmlns}")
    private String xmlns;

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns:xsi")
    private final static String xsi = "http://www.w3.org/2001/XMLSchema-instance";

    @JsonProperty("Type")
    private int type = 6;

    @JsonProperty("Id")
    private List<Long> hotelIds;

    @JsonProperty("CheckIn")
    private String checkin;

    @JsonProperty("CheckOut")
    private String checkout;

    @JsonProperty("Rooms")
    private int numberOfRooms = 1;

    @JsonProperty("Adults")
    private int numberOfAdults = 2;

    @JsonProperty("Children")
    private int numberOfChildren = 0;

    @JsonProperty("ChildrenAges")
    @XmlElementWrapper(name = "ChildrenAges")
    @XmlElement(name = "Age")
    private ArrayList<Integer> childrenAges;

    @JsonProperty("Language")
    private String language = "ko-kr";

    @JsonProperty("Currency")
    private String currency = "KRW";

    public String getHotelIds() {
        return StringUtils.arrayToDelimitedString(this.hotelIds.toArray(), ",");
    }

    @Autowired
    @JsonIgnore
    private KMSProperties kmsProperties;

}
