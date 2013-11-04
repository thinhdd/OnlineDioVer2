package com.qsoft.ondio.accountmanager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.util.HashStringToMD5;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URL;

public class ParseComServer implements ServerAuthenticate
{
    @Override
    public User userSignIn(String username, String pass, String authType) throws Exception
    {

        String url = "http://192.168.1.222/testing/ica467/trunk/public/auth-rest";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        pass = new HashStringToMD5().doConvert(pass);

        try
        {
            URL realUrl = new URL(url);
            HttpPost httpPost = new HttpPost(realUrl.toURI());

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", username);
            jsonObject.addProperty("password", pass);
            jsonObject.addProperty("grant_type", "password");
            jsonObject.addProperty("client_id", "123456789");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            User user = new Gson().fromJson(responseString, User.class);
            return user;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
