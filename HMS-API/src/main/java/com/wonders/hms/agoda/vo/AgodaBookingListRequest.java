package com.wonders.hms.agoda.vo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Setter
@Getter
@XmlRootElement(name = "BookingListRequestV2")
public class AgodaBookingListRequest {
    @JacksonXmlProperty(isAttribute = true, localName = "siteid")
    private String siteId;

    @JacksonXmlProperty(isAttribute = true, localName = "apikey")
    private String apiKey;

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns")
    private String xmlns;

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns:xsi")
    private String xsi = "http://www.w3.org/2001/XMLSchema-instance";

    @JacksonXmlProperty(localName = "Tag")
    @JacksonXmlElementWrapper(localName = "Tags")
    private List tags;

}
