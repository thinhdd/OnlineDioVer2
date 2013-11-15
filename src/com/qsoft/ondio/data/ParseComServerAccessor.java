package com.qsoft.ondio.data;

import android.accounts.*;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.ondio.model.Home;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.model.ProfileResponse;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.restservice.Interceptor;
import com.qsoft.ondio.restservice.Services;
import com.qsoft.ondio.util.Common;
import com.qsoft.ondio.util.HashStringToMD5;
import com.qsoft.ondio.util.ShareInfoAccount;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EBean
public class ParseComServerAccessor {

    @RestService
    Services services;

    @Bean
    Interceptor interceptor;

    @Bean
    ShareInfoAccount infoAccount;

    @AfterInject
    public void init() {
        if(infoAccount.getUser_id()!=null)
        {
            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
            interceptors.add(interceptor);
            services.getRestTemplate().setInterceptors(interceptors);
        }
    }

    public User signIn(String username, String password) {
        try {
            password = new HashStringToMD5().doConvert(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap urlVariables = new HashMap();
        urlVariables.put("username", username);
        urlVariables.put("password", password);
        urlVariables.put("grant_type", "password");
        urlVariables.put("client_id", "123456789");
        return services.signIn(urlVariables);
    }

    private String getNewToken(Account account, AccountManager mAccountManager, String token) throws OperationCanceledException, IOException, AuthenticatorException {
        mAccountManager.invalidateAuthToken(Common.ARG_ACCOUNT_TYPE, token);
        AccountManagerFuture<Bundle> bundle = mAccountManager.getAuthToken(account, Common.AUTHTOKEN_TYPE_FULL_ACCESS, true, null, null);
        token = bundle.getResult().getString(AccountManager.KEY_AUTHTOKEN);
        return token;
    }

    public Profile getShowsProfile() {

        try{
            Profile profile = services.getProfile(infoAccount.getUser_id()).getData();
            return profile;
        }catch (Exception e)
        {
            refreshToken();
        }
        return null;
    }

    private void refreshToken() {
        User user = this.signIn(infoAccount.getAccount().name,infoAccount.getPassword());
        infoAccount.refreshTokenToSystem(user.getAccess_token());
    }

    public ProfileResponse updateProfile(Account account, AccountManager mAccountManager, Profile profile, String token, String user_id) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/user-rest/" + user_id;
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-type", "application/json");
        httpPut.addHeader("Authorization", "Bearer " + token);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("display_name", profile.getDisplay_name());
        jsonObject.addProperty("full_name", profile.getFull_name());
        jsonObject.addProperty("phone", profile.getPhone());
        jsonObject.addProperty("birthday", profile.getBirthday());
        jsonObject.addProperty("gender", profile.getGender());
        jsonObject.addProperty("country_id", profile.getCountry_id());
        jsonObject.addProperty("description", profile.getDescription());
        try {
            httpPut.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
            HttpResponse response = httpClient.execute(httpPut);
            String responseString = EntityUtils.toString(response.getEntity());
            if (responseString.contains("Form invalid")) {
                return null;
            } else {
                if (responseString.contains("cannot access my apis")) {
                    token = getNewToken(account, mAccountManager, token);
                    httpPut.removeHeaders("Authorization");
                    httpPut.addHeader("Authorization", "Bearer " + token);
                    response = httpClient.execute(httpPut);
                    responseString = EntityUtils.toString(response.getEntity());
                }
                return new Gson().fromJson(responseString, ProfileResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
