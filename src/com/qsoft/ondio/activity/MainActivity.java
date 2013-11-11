package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;
import com.qsoft.ondio.util.Common;


@EActivity(R.layout.main)
public class MainActivity extends Activity
{
    @ViewById(R.id.btLogin)
    public Button btLogin;

    @SystemService
    AccountManager accountManager;

    @AfterViews
    void setUpView()
    {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0)
        {
            finish();
            return;
        }
    }

    @Click(R.id.btLogin)
    void doLogin()
    {
        getTokenForAccountCreateIfNeeded(Common.ARG_ACCOUNT_TYPE, Common.AUTHTOKEN_TYPE_FULL_ACCESS);
        finish();
    }

    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType)
    {
        final AccountManagerFuture<Bundle> future = accountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>()
                {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future)
                    {
                        Bundle bnd = null;
                        try
                        {
                            bnd = future.getResult();
                            String authToken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            if (null != authToken)
                            {
                                String accountName = bnd.getString(AccountManager.KEY_ACCOUNT_NAME);
                                Account account = new Account(accountName, Common.ARG_ACCOUNT_TYPE);
                                String user_id = accountManager.getUserData(account,Common.USERDATA_USER_OBJ_ID);
                                SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = setting.edit();
                                editor.putString("accountName", accountName);
                                editor.putString("user_id", user_id);
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), SlidebarActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity_.class);
//                                intent.putExtra("IS_ADDING_ACCOUNT", true);
                                startActivity(intent);
                            }
                            finish();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                }
                , null);
    }

    private void showMessage(final String msg)
    {
        if (msg == null || msg.trim().equals(""))
        {
            return;
        }

        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
