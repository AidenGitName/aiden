package com.wonders.hms.util;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


public class RestClient {
    private String server;

    private RestTemplate restTemplate;

    private HttpHeaders httpHeaders;

    private HttpStatus status;

    public RestClient(String server) {
        this.server = server;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "*/*");

        this.httpHeaders = httpHeaders;

        RestTemplateBuilder builder = new RestTemplateBuilder();
        this.restTemplate = builder.build();
    }

    public void setAuthorization(String authorization) {
        this.httpHeaders.set("Authorization", authorization);
    }

    public void setHeader(String key, String value) {
        this.httpHeaders.set(key, value);
    }

    public String get(String uri) throws HttpClientErrorException {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", this.httpHeaders);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(this.server + uri, HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public String post(String uri, String json) throws HttpClientErrorException {
        return post(this.server + uri, json, this.httpHeaders);
    }

    public String post(String uri, String body, HttpHeaders httpHeaders) throws HttpClientErrorException {
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, httpHeaders);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public void put(String uri, String json) throws HttpClientErrorException {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, this.httpHeaders);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(this.server + uri, HttpMethod.PUT, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
    }

    public void delete(String uri) throws HttpClientErrorException {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", this.httpHeaders);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(this.server + uri, HttpMethod.DELETE, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
