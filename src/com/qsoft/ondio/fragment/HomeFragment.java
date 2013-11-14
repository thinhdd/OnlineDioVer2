package com.qsoft.ondio.fragment;

import android.accounts.Account;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;
import com.qsoft.ondio.activity.SlidebarActivity;
import com.qsoft.ondio.data.dao.HomeContract;
import com.qsoft.ondio.util.Common;
import com.qsoft.ondio.util.ShareInfoAccount;

@EFragment(R.layout.home)
public class HomeFragment extends Fragment
{
    final String TAG = "HomeFragment";
    @ViewById(R.id.home_btMenu)
    protected Button btMenu;
    @ViewById(R.id.home_btNotifications)
    protected Button btNotifications;

    @Bean
    ShareInfoAccount shareInfoAccount;

    @AfterViews
    void afterViews()
    {
        doSyncData();
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.home_lvFeeds, new HomeListFragment_(), "HomeListFragment_");
        ft.commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.home_lvFeeds, new HomeListFragment_(), "HomeListFragment_");
        ft.commit();
    }

    @Click({R.id.home_btMenu, R.id.home_btNotifications})
    void onClickFeatures(View view)
    {
        switch (view.getId())
        {
            case R.id.home_btMenu:
                showMenu();
                break;
            case R.id.home_btNotifications:
                doSyncData();
                break;
        }
    }

    private void doSyncData()
    {
        Account account = shareInfoAccount.getAccount();
        Bundle bundle = new Bundle();
        ContentResolver.setIsSyncable(account, Common.CONTENT_AUTHORITY, 1);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true); // Performing a sync no matter if it's off
        bundle.putString(Common.TYPE_SYNC, TAG);
        ContentResolver.requestSync(account, HomeContract.AUTHORITY, bundle);
    }

    private void showMenu()
    {
        ((SlidebarActivity) getActivity()).setOpenListOption();
    }

}

