package com.qsoft.ondio.accountmanager;

import android.accounts.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.qsoft.ondio.activity.LoginActivity_;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.util.Common;

import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;

public class UdinicAuthenticator extends AbstractAccountAuthenticator
{

    private String TAG = "UdinicAuthenticator";
    private final Context mContext;

    public UdinicAuthenticator(Context context)
    {
        super(context);
        this.mContext = context;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException
    {
        Log.d("udinic", TAG + "> addAccount");

        final Intent intent = new Intent(mContext, LoginActivity_.class);
        intent.putExtra(Common.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(Common.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(Common.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException
    {

        Log.d(TAG, "> getAuthToken");

        // If the caller requested an authToken type we don't support, then
        // return an error
        if (!authTokenType.equals(Common.AUTHTOKEN_TYPE_READ_ONLY) && !authTokenType.equals(Common.AUTHTOKEN_TYPE_FULL_ACCESS))
        {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager am = AccountManager.get(mContext);

        String authToken = am.peekAuthToken(account, authTokenType);
        String userId = null;

        Log.d("udinic", TAG + "> peekAuthToken returned - " + authToken);

        // Lets give another try to authenticate the user
        if (TextUtils.isEmpty(authToken))
        {
            final String password = am.getPassword(account);
            if (password != null)
            {
                try
                {
                    Log.d("udinic", TAG + "> re-authenticating with the existing password");
                    User user = Common.sServerAuthenticate.userSignIn(account.name, password, authTokenType);
                    if (user != null)
                    {
                        authToken = user.getAccess_token();
                        userId = user.getUser_id();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken))
        {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            result.putString(Common.PARAM_USER_ID, userId);
            return result;
        }

        final Intent intent = new Intent(mContext, LoginActivity_.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(Common.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(Common.ARG_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }


    @Override
    public String getAuthTokenLabel(String authTokenType)
    {
        if (Common.AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType))
        {
            return Common.AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
        }
        else if (Common.AUTHTOKEN_TYPE_READ_ONLY.equals(authTokenType))
        {
            return Common.AUTHTOKEN_TYPE_READ_ONLY_LABEL;
        }
        else
        {
            return authTokenType + " (Label)";
        }
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException
    {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType)
    {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException
    {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException
    {
        return null;
    }
}
