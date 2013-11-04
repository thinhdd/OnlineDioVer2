package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.qsoft.ondio.R;
import com.qsoft.ondio.util.Common;

public class MainActivity extends Activity
{
    private Button btLogin;
    private AccountManager mAccountManager;
    private static String authToken = null;
    private Account mConnectedAccount;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mAccountManager = AccountManager.get(this);
        setupUI();
        setUpListenerController();
    }

    private void setupUI()
    {
        btLogin = (Button) findViewById(R.id.btLogin);
    }


    private void setUpListenerController()
    {
        btLogin.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.btLogin:
                    doLogin();
                    break;
            }
        }
    };

    private void doLogin()
    {
        Boolean accountConnect = doCheckTokenCurrent();
        if (accountConnect)
        {
            Intent intent = new Intent(this, SlidebarActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("IS_ADDING_ACCOUNT", true);
            startActivity(intent);
        }
    }

    private Boolean doCheckTokenCurrent()
    {
        getTokenForAccountCreateIfNeeded(Common.ARG_ACCOUNT_TYPE, Common.AUTHTOKEN_TYPE_FULL_ACCESS);
        if (null != authToken)
        {
            SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = setting.edit();
            editor.putString("authToken", authToken);
            editor.commit();
            return true;
        }
        else
        {
            return false;
        }
    }

    private String getTokenForAccountCreateIfNeeded(String accountType, String authTokenType)
    {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>()
                {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future)
                    {
                        Bundle bnd = null;
                        try
                        {
                            bnd = future.getResult();
                            authToken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            if (authToken != null)
                            {
                                String accountName = bnd.getString(AccountManager.KEY_ACCOUNT_NAME);
                                mConnectedAccount = new Account(accountName, Common.ARG_ACCOUNT_TYPE);

                                ContentResolver.setIsSyncable(mConnectedAccount, Common.CONTENT_AUTHORITY, 1);
                                ContentResolver.setSyncAutomatically(mConnectedAccount, Common.CONTENT_AUTHORITY, true);
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                }
                , null);
        return authToken;
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
