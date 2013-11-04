package com.qsoft.ondio.data;

import android.content.ContentResolver;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qsoft.ondio.model.Home;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class ParseComServerAccessor
{

    private ContentResolver mContentResolver;

    public ParseComServerAccessor()
    {
    }

    public ArrayList<Home> getShows(String token)
    {
        ArrayList<Home> homes = new ArrayList<Home>();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://192.168.1.222/testing/ica467/trunk/public/home-rest";
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization", "Bearer " + token);
        try
        {
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());
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
}
