package com.qsoft.ondio.controller;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;
import com.qsoft.ondio.activity.LoginActivity;
import com.qsoft.ondio.activity.MainActivity_;
import com.qsoft.ondio.activity.SlidebarActivity_;
import com.qsoft.ondio.dialog.MyDialog;
import com.qsoft.ondio.util.Common;
import com.qsoft.ondio.util.NetworkAvailable;
import com.qsoft.ondio.util.ShareInfoAccount;

/**
 * User: thinhdd
 * Date: 11/13/13
 * Time: 9:23 AM
 */

@EBean
public class LoginController
{
    @RootContext
    Activity activity;

    @Bean
    ShareInfoAccount shareInfoAccount;

    @SystemService
    AccountManager accountManager;

    public void finishLogin(Intent intentContent, String mAuthTokenType)
    {

        String accountName = intentContent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intentContent.getStringExtra(Common.PARAM_USER_PASS);
        final Account account = new Account(accountName, intentContent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        shareInfoAccount.setAccount(account);
        String authToken = intentContent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        String authTokenType = mAuthTokenType;
        Intent intent = new Intent(activity, SlidebarActivity_.class);
        activity.startActivity(intent);
        activity.finish();
        accountManager.addAccountExplicitly(account, accountPassword, intentContent.getBundleExtra(AccountManager.KEY_USERDATA));
        accountManager.setAuthToken(account, authTokenType, authToken);

        ((AccountAuthenticatorActivity) activity).setAccountAuthenticatorResult(intentContent.getExtras());
        activity.setResult(activity.RESULT_OK, intentContent);
    }
    public void syncAccount()
    {
        Account[] accounts = accountManager.getAccountsByType(Common.ARG_ACCOUNT_TYPE);
        for (Account account : accounts)
        {
            accountManager.removeAccount(account, null, null);
        }
    }

    @Click(R.id.login_btBack)
    void doBack()
    {
        activity.startActivity(new Intent(activity, MainActivity_.class));
    }

    @Click(R.id.login_btNext)
    void doLogin()
    {
        if (!checkNetwork())
        {
            MyDialog.showMessageDialog(activity, activity.getString(R.string.tittle_login_error)
                    , activity.getString(R.string.error_connect_network));
        }
        else
        {
            ((LoginActivity)activity).checkLogin();
        }
    }

    private boolean checkNetwork()
    {
        NetworkAvailable network = new NetworkAvailable(activity);
        return network.isEnabled();
    }

}
