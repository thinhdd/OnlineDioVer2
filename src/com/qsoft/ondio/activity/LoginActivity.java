package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;
import com.qsoft.ondio.controller.LoginController;
import com.qsoft.ondio.data.ParseComServerAccessor;
import com.qsoft.ondio.dialog.MyDialog;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.util.Common;
import com.qsoft.ondio.util.NetworkAvailable;
import com.qsoft.ondio.util.ShareInfoAccount;

@EActivity(R.layout.login)
public class LoginActivity extends AccountAuthenticatorActivity
{
    private static final String TAG = "LoginActivity";
    @SystemService
    AccountManager accountManager;

    @Bean
    ShareInfoAccount shareInfoAccount;

    @Bean
    LoginController loginController;

    @Bean
    ParseComServerAccessor parseCom;

    private String mAuthTokenType;

    @ViewById(R.id.login_btNext)
    public Button btLogin;
    @ViewById(R.id.login_btBack)
    public Button btBack;
    @ViewById(R.id.login_tvForgotPassword)
    public TextView tvForgotPassword;
    @ViewById(R.id.login_etEmail)
    public EditText etEmail;
    @ViewById(R.id.login_etPassword)
    public EditText etPassword;

    @AfterViews
    void setUpView()
    {
        mAuthTokenType = getIntent().getStringExtra(Common.ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
        {
            mAuthTokenType = Common.AUTHTOKEN_TYPE_FULL_ACCESS;
        }
        loginController.syncAccount();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @AfterTextChange({R.id.login_etEmail, R.id.login_etPassword})
    void doEnableNextButton()
    {
        if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty())
        {
            btLogin.setBackgroundResource(R.drawable.login_login_visible);
            btLogin.setEnabled(false);
        }
        else
        {
            btLogin.setBackgroundResource(R.drawable.login_login);
            btLogin.setEnabled(true);
        }
    }

    public void checkLogin()
    {
        Log.d(TAG, "> Submit");

        final String userName = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        doSignIn(userName, password);
    }

    @Background
    void doSignIn(String userName, String password)
    {
        Log.d(TAG, "> Started authenticating");

        Bundle data = new Bundle();
        try
        {

            User user = parseCom.signIn(userName, password);
            if (user.getAccess_token() != null)
            {
                data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                data.putString(AccountManager.KEY_ACCOUNT_TYPE, Common.ARG_ACCOUNT_TYPE);
                data.putString(AccountManager.KEY_AUTHTOKEN, user.getAccess_token());

//                saveInfo(user.getAccess_token(), userName, user.getUser_id());
                Log.d(TAG, "Show token" + user.getAccess_token());
                Bundle userData = new Bundle();
                userData.putString(Common.USERDATA_USER_OBJ_ID, user.getUser_id());
                data.putBundle(AccountManager.KEY_USERDATA, userData);

                data.putString(Common.PARAM_USER_PASS, password);
            }
            else
            {
                data.putString(Common.KEY_ERROR_MESSAGE, "Account is not exists");
            }
        }
        catch (Exception e)
        {
            data.putString(Common.KEY_ERROR_MESSAGE, e.getMessage());
        }
        final Intent res = new Intent();
        res.putExtras(data);
        updateLogin(res);
    }

    @UiThread
    void updateLogin(Intent intent)
    {
        if (intent.hasExtra(Common.KEY_ERROR_MESSAGE))
        {
            Toast.makeText(getBaseContext(), intent.getStringExtra(Common.KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
        }
        else
        {
            loginController.finishLogin(intent, mAuthTokenType);
        }
    }

}