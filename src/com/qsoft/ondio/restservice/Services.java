package com.qsoft.ondio.restservice;

import com.google.gson.JsonObject;
import com.googlecode.androidannotations.annotations.rest.*;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.qsoft.ondio.model.Homes;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.model.ProfileResponse;
import com.qsoft.ondio.model.User;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


import java.net.URL;
import java.util.HashMap;

/**
 * User: thinhdd
 * Date: 11/13/13
 * Time: 4:38 PM
 */

@Rest(rootUrl = "http://113.160.50.84:1009/testing/ica467/trunk/public", converters = {StringHttpMessageConverter.class})
public interface Services
{
    RestTemplate getRestTemplate();

    @Get("/home-rest")
    @Accept(MediaType.APPLICATION_JSON)
    Homes getShowsFeedHome();

    @Post("/auth-rest")
    @Accept(MediaType.APPLICATION_JSON)
    User signIn(HashMap nameValuePairs);

    @Get("/user-rest/{account_id}")
    @Accept(MediaType.APPLICATION_JSON)
    ProfileResponse getProfile(String account_id);

//    @Put("/user-rest/{account_id}")
//    @Accept(MediaType.APPLICATION_JSON)
//    ProfileResponse updateProfile(Profile profile,String account_id);

}
