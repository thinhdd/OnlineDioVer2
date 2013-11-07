package com.qsoft.ondio.data;

import android.accounts.*;
import android.content.ContentResolver;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qsoft.ondio.model.Home;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.model.ProfileResponse;
import com.qsoft.ondio.util.Common;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ParseComServerAccessor
{

    private ContentResolver mContentResolver;

    public ParseComServerAccessor()
    {

    }

    public ArrayList<Home> getShowsFeedHome(Account account, AccountManager mAccountManager, String token)
    {
        ArrayList<Home> homes = new ArrayList<Home>();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/home-rest";
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization", "Bearer " + token);
        try
        {
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());
            if (responseString.contains("cannot access my apis"))
            {
                token = getNewToken(account, mAccountManager, token);
                httpGet.removeHeaders("Authorization");
                httpGet.addHeader("Authorization", "Bearer " + token);
                response = httpClient.execute(httpGet);
                responseString = EntityUtils.toString(response.getEntity());
            }
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(responseString);
            JsonArray jsonArray = jsonObject.getAsJsonArray("data");
            int size = jsonArray.size();
            for (int i = 0; i < size; i++)
            {
                Home home = new Gson().fromJson(jsonArray.get(i), Home.class);
                homes.add(home);
            }

            return homes;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ArrayList<Home>();
    }

    private String getNewToken(Account account, AccountManager mAccountManager, String token) throws OperationCanceledException, IOException, AuthenticatorException
    {
        mAccountManager.invalidateAuthToken(Common.ARG_ACCOUNT_TYPE, token);
        AccountManagerFuture<Bundle> bundle = mAccountManager.getAuthToken(account, Common.AUTHTOKEN_TYPE_FULL_ACCESS, true, null, null);
        token = bundle.getResult().getString(AccountManager.KEY_AUTHTOKEN);
        return token;
    }

    public Profile getShowsProfile(Account account, AccountManager mAccountManager, String token, String user_id)
    {
        Profile profile;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/user-rest/" + user_id;
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization", "Bearer " + token);
        try
        {
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());
            if (responseString.contains("cannot access my apis"))
            {
                token = getNewToken(account, mAccountManager, token);
                httpGet.removeHeaders("Authorization");
                httpGet.addHeader("Authorization", "Bearer " + token);
                response = httpClient.execute(httpGet);
                responseString = EntityUtils.toString(response.getEntity());
            }
            profile = new Gson().fromJson(responseString, ProfileResponse.class).getData();
            Log.d(getClass().toString(), "dad");
            return profile;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new Profile();
    }

    public ProfileResponse updateProfile(Account account, AccountManager mAccountManager, Profile profile, String token, String user_id)
    {
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
        try
        {
            httpPut.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
            HttpResponse response = httpClient.execute(httpPut);
            String responseString = EntityUtils.toString(response.getEntity());
            if (responseString.contains("Form invalid"))
            {
                return null;
            }
            else
            {
                if (responseString.contains("cannot access my apis"))
                {
                    token = getNewToken(account, mAccountManager, token);
                    httpPut.removeHeaders("Authorization");
                    httpPut.addHeader("Authorization", "Bearer " + token);
                    response = httpClient.execute(httpPut);
                    responseString = EntityUtils.toString(response.getEntity());
                }
                ProfileResponse profileResponse = new Gson().fromJson(responseString, ProfileResponse.class);
                return profileResponse;
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (AuthenticatorException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (OperationCanceledException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    private String checkResponse(String response, Account account, AccountManager mAccountManager)
    {
        if (response.contains("cannot access my apis"))
        {

        }
        return null;
    }
}
