package com.wonders.hms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.wonders.hms.agoda.vo.AgodaHotelResult;

import java.io.IOException;

public class XMLUtils {

    public static String ObjectToXML(Object object) throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.registerModule(new JaxbAnnotationModule());

        return xmlMapper.writeValueAsString(object);
    }

    public static <T> T toObject(String xml, Class<T> valueType) throws IOException {
        ObjectMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xml, valueType);
    }
}
