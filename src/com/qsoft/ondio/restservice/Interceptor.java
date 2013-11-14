package com.qsoft.ondio.restservice;

import android.accounts.AccountManager;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.SystemService;
import com.qsoft.ondio.util.Common;
import com.qsoft.ondio.util.ShareInfoAccount;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * User: thinhdd
 * Date: 11/13/13
 * Time: 11:18 AM
 */
@EBean
public class Interceptor implements ClientHttpRequestInterceptor
{
    @SystemService
    AccountManager accountManager;

    @Bean
    ShareInfoAccount infoAccount;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException
    {
        String token = accountManager.peekAuthToken(infoAccount.getAccount(), Common.AUTHTOKEN_TYPE_FULL_ACCESS);
        httpRequest.getHeaders().add("Content-type", "application/json");
        httpRequest.getHeaders().add("Authorization", "Bearer "+token);
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
