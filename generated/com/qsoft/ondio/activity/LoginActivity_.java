//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.ondio.activity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import com.qsoft.ondio.R.id;
import com.qsoft.ondio.R.layout;
import com.qsoft.ondio.controller.LoginController_;
import com.qsoft.ondio.data.ParseComServerAccessor_;
import com.qsoft.ondio.util.ShareInfoAccount_;

public final class LoginActivity_
    extends LoginActivity
{

    private Handler handler_ = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.login);
    }

    private void init_(Bundle savedInstanceState) {
        accountManager = ((AccountManager) this.getSystemService(Context.ACCOUNT_SERVICE));
        shareInfoAccount = ShareInfoAccount_.getInstance_(this);
        loginController = LoginController_.getInstance_(this);
        parseCom = ParseComServerAccessor_.getInstance_(this);
    }

    private void afterSetContentView_() {
        etEmail = ((EditText) findViewById(id.login_etEmail));
        btLogin = ((Button) findViewById(id.login_btNext));
        tvForgotPassword = ((TextView) findViewById(id.login_tvForgotPassword));
        etPassword = ((EditText) findViewById(id.login_etPassword));
        btBack = ((Button) findViewById(id.login_btBack));
        ((ShareInfoAccount_) shareInfoAccount).afterSetContentView_();
        ((LoginController_) loginController).afterSetContentView_();
        ((ParseComServerAccessor_) parseCom).afterSetContentView_();
        {
            final TextView view = ((TextView) findViewById(id.login_etEmail));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void afterTextChanged(Editable s) {
                        LoginActivity_.this.doEnableNextButton();
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                }
                );
            }
        }
        {
            final TextView view = ((TextView) findViewById(id.login_etPassword));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void afterTextChanged(Editable s) {
                        LoginActivity_.this.doEnableNextButton();
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                }
                );
            }
        }
        setUpView();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView_();
    }

    public static LoginActivity_.IntentBuilder_ intent(Context context) {
        return new LoginActivity_.IntentBuilder_(context);
    }

    @Override
    public void updateLogin(final Intent intent) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    LoginActivity_.super.updateLogin(intent);
                } catch (RuntimeException e) {
                    Log.e("LoginActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void doSignIn(final String userName, final String password) {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    LoginActivity_.super.doSignIn(userName, password);
                } catch (RuntimeException e) {
                    Log.e("LoginActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, LoginActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public LoginActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (context_ instanceof Activity) {
                ((Activity) context_).startActivityForResult(intent_, requestCode);
            } else {
                context_.startActivity(intent_);
            }
        }

    }

}
