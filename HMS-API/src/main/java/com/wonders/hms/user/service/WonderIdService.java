package com.wonders.hms.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.user.vo.WAuthMe;
import com.wonders.hms.util.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@Service
public class WonderIdService {
    @Value("${wonder.id.url}")
    private String WONDER_ID_SERVER;

    @Autowired
    private ObjectMapper objectMapper;

    public Long getMid(String token) {
        if (token == null) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

        WAuthMe wAuthMe = getMemberInfo(token);

        if (wAuthMe == null) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

        return wAuthMe.getMid();
    }

    public WAuthMe getMemberInfo(String token) {
        final String memberInfoPath = "wauth/me";

        RestClient restClient = new RestClient(WONDER_ID_SERVER);
        restClient.setAuthorization("Bearer " + token);

        String wAuthMeResponseBody = restClient.get(memberInfoPath);
        WAuthMe wAuthMe = null;
        try {
            wAuthMe = objectMapper.readValue(wAuthMeResponseBody, WAuthMe.class);
        } catch (IOException e) {
        }
        return wAuthMe;
    }
}
