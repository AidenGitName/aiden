package org.zerock.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.zerock.domain.HotelAmenity;
import org.zerock.persistence.HotelRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

@Log
@Controller
public class HotelAmenityXmlParser {
    @Autowired
    private HotelRepository hotelRepository;

    private int seccese = 0;

    private long statTime;

    private long startTime;
    private Calendar calendar = Calendar.getInstance();

    // tag값의 정보를 가져오는 메소드
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public void setHotelAmenity() {
        getReqUrls().forEach(url -> {
            Document doc = getXmlList(url);
            String result = url.substring(url.lastIndexOf("mhotel_id=")+1);
            log.info(result);

                // root tag
                doc.getDocumentElement().normalize();
                log.info("Root element :" + doc.getDocumentElement().getNodeName());

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("roomtype_facility");

                Set<HotelAmenity> hotelAmenitySet = new HashSet<>();
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        HotelAmenity hotelAmenity = new HotelAmenity();
                        hotelAmenity.setHotelId(Long.valueOf(getTagValue("hotel_id", eElement)));
                        hotelAmenity.setPropertyName(getTagValue("property_name", eElement));
                        hotelAmenity.setTanslateName(getTagValue("translated_name", eElement));

                        hotelAmenitySet.add(hotelAmenity);
                    }
                }
                log.info("Parsing Amenity Size : " + hotelAmenitySet.size());
                hotelRepository.saveAll(hotelAmenitySet);
        });
    }

    public List<String> getReqUrls() {
        List<Integer> hotelIds = hotelRepository.getAllbyHotelIds();
        log.info("Start HotelId : " + hotelIds.get(0));
        log.info("List Size : " + hotelIds.size());
        List<String> reqUrls = new ArrayList<>();

        hotelIds.forEach(hotelId -> {
            String url = "http://xml.agoda.com/datafeeds/Feed.asmx/GetFeed?feed_id=14&apikey=8ba7c83a-72ab-4586-b806-1827f95ed4a6&mhotel_id=" + hotelId;
            reqUrls.add(url);
        });

        return reqUrls;
    }

    public Document getXmlList(String url) {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        int restart = 0;
        this.startTime = System.currentTimeMillis()/1000;

        boolean fail = true;
        while (fail) {
            try {
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

                long runTIme = System.currentTimeMillis()/1000;
                if(startTime+599 < runTIme){
                    log.info("Running 599sec, Wait 20Min...");
                    Thread.sleep(1200*1000);
                    this.startTime = System.currentTimeMillis()/1000;
                }

                doc = docBuilder.parse(url);
                fail = false;
                log.info("성공횟수 : " + ++this.seccese);

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                log.info(this.seccese + " 번 성공 이후 fail");
                try {
                    restart++;
                    if(restart ==2 ){
                        Thread.sleep(1200 * 1000);
                    } else {
                        this.seccese =0;
                        Thread.sleep(300 * 1000);
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return doc;
    }



}
