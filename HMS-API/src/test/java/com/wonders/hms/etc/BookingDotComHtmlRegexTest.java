package com.wonders.hms.etc;

import com.wonders.hms.util.RestClient;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookingDotComHtmlRegexTest {
    @Test
    public void bookingDotComHtmlRegexTest() {
        RestClient restClient = new RestClient("https://secure.booking.com/");

        String responseBody = restClient.get("myreservations.ko.html?bn=#####&pincode=#####");

        Pattern pattern = Pattern.compile(
                "\\<a href\\=\\\"" + "##hotel url##".replaceAll("\\.", "\\.") + "?.*\\\".*[\\>]([^<]*)\\<\\/a\\>"
        );

        Matcher matcher = pattern.matcher(responseBody);

        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }
        System.out.println("find failure");
    }
}
