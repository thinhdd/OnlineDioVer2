package com.qsoft.ondio.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.api.Scope;

@EBean(scope = Scope.Singleton)
public class ShareInfoAccount
{
    @SystemService
    AccountManager accountManager;

    private Account account;

    public Account getAccount()
    {
        return account;
    }

    public void setAccount(Account account)
    {
        this.account = account;
    }

    public String getUser_id()
    {
        if (account != null)
        {
            return accountManager.getUserData(account, Common.USERDATA_USER_OBJ_ID);
        }
        else
            return null;
    }

    public String getPassword()
    {
        return accountManager.getPassword(account);
    }

    public void refreshTokenToSystem(String authToken)
    {
        accountManager.setAuthToken(account, Common.AUTHTOKEN_TYPE_FULL_ACCESS, authToken);
    }
}
