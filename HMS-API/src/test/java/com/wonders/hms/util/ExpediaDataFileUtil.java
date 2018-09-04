package com.wonders.hms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.expedia.vo.staticInfo.ExpediaHotelInfo;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class ExpediaDataFileUtil{

    @Test
//    @Ignore
    public void parse() throws Exception {
        for (int i = 1; i <=5; i++) {
            BufferedReader in = new BufferedReader(new FileReader("/Users/we/Documents/expedia/hotel db/Expedia_Static_Hotel_Content_" + i + ".jsonl"));

            String s = null;

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                while ((s = in.readLine()) != null) {
                    ExpediaHotelInfo obj = objectMapper.readValue(s, ExpediaHotelInfo.class);
//                    ExpediaDBInsert.amenityInsert(obj);
//                    ExpediaDBInsert.imageInsert(obj);
//                System.out.println(obj);
//                    break;
                }
            } catch (Exception e) {
//            System.out.println(s);
//            System.out.println();
//            System.out.println(e.getMessage());
                throw e;

            } finally {
                in.close();
            }
            System.out.println("file " + i + " done");
        }


    }

    @Test
    @Ignore
    public void arrayListParse() throws Exception {
        ArrayList<Integer> test = new ArrayList();

        for(Integer i = 0; i < 10; i++){
            test.add(i);
        }

        ObjectMapper mapper = new ObjectMapper();

        String testToJsonString = mapper.writeValueAsString(test);

        System.out.println(testToJsonString);
    }

}
