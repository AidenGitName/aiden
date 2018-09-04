package com.wonders.hms.expedia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.expedia.vo.ExpCrosApiRoom;
import com.wonders.hms.hotel.vo.HotelSearch;
import com.wonders.hms.util.HttpRequest;
import com.wonders.hms.util.vo.api.agent.expedia.Crosssell;
import com.wonders.hms.util.vo.api.agent.expedia.Ph;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Optional;

@Service
public class ExpediaCrossSellRequestService {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    @Value("${expedia.account.search.special.domain.hotellist}")
    private String expediaXsellApiDomain;

    @Value("${expedia.account.search.special.uri.hotellist}")
    private String expediaXsellApiUri;

    private String expediaPartnerId;

    private String crossSellKey;

    @Autowired
    private Crosssell expediaCrosssell;

    @Autowired
    private Ph expediaPhProp;

    @PostConstruct
    public void init(){

        this.crossSellKey = expediaCrosssell.getKey();
        this.expediaPartnerId = expediaPhProp.getPublisherId();

    }

    public ExpCrosApiRoom getSpecialRooms(HotelSearch search) throws Exception{
        if (search.getDestinationCode() == null) return null;

        String param = buildXsellParam(search);
        String signature = makeSignature(param);
        String requestUrl = expediaXsellApiDomain + param + "&signature=" + signature;
        String body = sendRequest(requestUrl);

        ObjectMapper objectMapper = new ObjectMapper();
        ExpCrosApiRoom expCrosApiRoom = objectMapper.readValue(body, ExpCrosApiRoom.class);

        return expCrosApiRoom;
    }


    private String sendRequest(String requestUrl) throws IOException {
        HttpUriRequest request = RequestBuilder.get()
                .setUri(requestUrl)
                .build();

        HttpRequest httpRequest = new HttpRequest();
        return httpRequest.send(request);
    }


    private String buildXsellParam(HotelSearch search) throws Exception{
        String uri = expediaXsellApiUri + "?";
        String partnerId = expediaPartnerId;
        String checkin = URLEncoder.encode(search.getCheckin().toString(), "UTF-8");
        String checkout = URLEncoder.encode(search.getCheckout().toString(), "UTF-8");
        String fencedResponse = "true";
        String numOfAdults = "2";
        String numOfChildren = "2";
        String destinationTla=search.getDestinationCode();
        String currencyCode="KRW";
        String locale="ko-KR";

        return
                uri +
                "partnerId=" + partnerId +
                "&outboundEndDateTime=" + checkin +
                "&returnStartDateTime=" + checkout +
                "&currencyCode=" + currencyCode +
                "&fencedResponse=" + fencedResponse +
                "&numOfAdults=" + numOfAdults +
                "&numOfChildren=" + numOfChildren +
                "&destinationTla=" + destinationTla +
                "&locale=" + locale;

    }

    private String makeSignature(String params) throws Exception {
        String computedHMAC = computeHMAC(params);

        return toBase64Encoding(computedHMAC);
    }

    private String computeHMAC(String params) throws InvalidKeyException {

        SecretKeySpec signingKey = new SecretKeySpec(crossSellKey.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mac.init(signingKey);
        return toHexString(mac.doFinal(params.getBytes()));
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    private String toBase64Encoding(String param) {
        BigInteger bigint = new BigInteger(param, 16);

        StringBuilder sb = new StringBuilder();
        byte[] ba = Base64.encodeInteger(bigint);
        for (byte b : ba) {
            sb.append((char)b);
        }
        String s = sb.toString();
        s = s.replace("+", "-");
        s = s.replace("/", "_");
        s = s.replace("=", "");

        return s;
    }
}
