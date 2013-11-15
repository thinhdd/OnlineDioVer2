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
        Intent intent = new Intent(getApplicationContext(), LoginActivity_.class);
        startActivity(intent);
        finish();
    }
}
