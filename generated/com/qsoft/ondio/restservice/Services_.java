//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.ondio.restservice;

import java.util.Collections;
import com.qsoft.ondio.model.Homes;
import com.qsoft.ondio.model.ProfileResponse;
import com.qsoft.ondio.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Services_
    implements Services
{

    private RestTemplate restTemplate;
    private String rootUrl;

    public Services_() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rootUrl = "http://113.160.50.84:1009/testing/ica467/trunk/public";
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Override
    public Homes getShowsFeedHome() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/json")));
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        return restTemplate.exchange(rootUrl.concat("/home-rest"), HttpMethod.GET, requestEntity, Homes.class).getBody();
    }

    @Override
    public ProfileResponse getProfile(String account_id) {
        java.util.HashMap<String, Object> urlVariables = new java.util.HashMap<String, Object>();
        urlVariables.put("account_id", account_id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/json")));
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        return restTemplate.exchange(rootUrl.concat("/user-rest/{account_id}"), HttpMethod.GET, requestEntity, ProfileResponse.class, urlVariables).getBody();
    }

    @Override
    public User signIn(java.util.HashMap nameValuePairs) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/json")));
        HttpEntity<java.util.HashMap> requestEntity = new HttpEntity<java.util.HashMap>(nameValuePairs, httpHeaders);
        return restTemplate.exchange(rootUrl.concat("/auth-rest"), HttpMethod.POST, requestEntity, User.class).getBody();
    }

}
