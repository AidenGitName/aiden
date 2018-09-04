package com.wonders.hms.agoda.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.wonders.hms.agoda.vo.AgodaHotelResult;
import com.wonders.hms.agoda.vo.AgodaHotelSearchRequest;
import com.wonders.hms.util.HttpRequest;
import com.wonders.hms.util.XMLUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AgodaHotelSearchRequestTest {

    private static final String agodaApiUrl = "http://sandbox-affiliateapi.agoda.com/xmlpartner/xmlservice/search_srv3";
    private static final String siteId = "1807495";
    private static final String apiKey = "94838f93-97f2-4dc1-8f8b-8ec22eae8bd4";
    private static final String xmlns= "http://xml.agoda.com";
    private AgodaHotelSearchRequest agodaHotelSearchRequest;
    private RequestBuilder requestBuilder;
    private HttpRequest httpRequest;

    @Before
    public void setUp() {
        List<Long> hotelIdList = new ArrayList<>();
        hotelIdList.add((long) 1);
        hotelIdList.add((long) 6);

        this.agodaHotelSearchRequest = new AgodaHotelSearchRequest();
        agodaHotelSearchRequest.setSiteId(siteId);
        agodaHotelSearchRequest.setApiKey(apiKey);
        agodaHotelSearchRequest.setXmlns(xmlns);
        agodaHotelSearchRequest.setHotelIds(hotelIdList);
        agodaHotelSearchRequest.setCheckin(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        agodaHotelSearchRequest.setCheckout(LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        requestBuilder = RequestBuilder.post()
            .setUri(agodaApiUrl)
            .setHeader("Accept-encoding", "gzip,deflate")
            .setHeader("Content-Type", "text/xml")
            .setHeader("Authorization", siteId + ":" + apiKey);

        httpRequest = new HttpRequest();
    }



    @Test
    public void getAgodaHotels() throws Exception {
        String body = XMLUtils.ObjectToXML(agodaHotelSearchRequest);
        HttpUriRequest request = requestBuilder.setEntity(new StringEntity(body)).build();
        String result = httpRequest.send(request);

        ObjectMapper xmlMapper = new XmlMapper();
        AgodaHotelResult agodaHotelResult = xmlMapper.readValue(result, AgodaHotelResult.class);
        System.out.println(agodaHotelResult.getHotels().get(0).getRooms().size());

    }

}
