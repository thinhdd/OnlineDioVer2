package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterListOption;
import com.qsoft.ondio.fragment.HomeFragment_;
import com.qsoft.ondio.fragment.ProfileFragment_;
import com.qsoft.ondio.util.Common;


@EActivity(R.layout.slidebar)
public class SlidebarActivity extends FragmentActivity
{
    private static final String TAG = "SlidebarActivity";
    @ViewById(R.id.drawer_layout)
    public DrawerLayout mDrawerLayout;
    @ViewById(R.id.slidebar_listOption)
    public ListView lvOption;
    @ViewById(R.id.left_drawer)
    public RelativeLayout rlLeftDrawer;
    @Bean
    ArrayAdapterListOption listOption;

    @AfterViews
    void bindAdapter()
    {
        lvOption.setAdapter(listOption);
    }
    @SystemService
    AccountManager mAccountManager;
    Boolean lastBack = false;

    @AfterViews
    void setUpView()
    {
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        Fragment homeFragment = new HomeFragment_();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, homeFragment, "HomeFragment_");
        fragmentTransaction.commit();

    }

    @Click(R.id.slidebar_rlProfile)
    void doEditProfile()
    {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new ProfileFragment_(), "ProfileFragment_");
        ft.addToBackStack("ProfileFragment");
        ft.commit();
        setCloseListOption();
    }

    @ItemClick(R.id.slidebar_listOption)
    void executeOption(int position)
    {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (position)
        {
            case Common.HOME:
                ft.replace(R.id.content_frame, new HomeFragment_(), "HomeFragment_");
                ft.addToBackStack("HomeFragment");
                ft.commit();
                break;
            case Common.SIGN_OUT:
                doSignOut();
                break;
        }
        setCloseListOption();
    }


    @Override
    public void onBackPressed()
    {

//        if (getFragmentManager().getBackStackEntryCount() == 0)
//        {
            if (lastBack)
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            Toast toast = Toast.makeText(this, "Press Back again to exit program", Toast.LENGTH_SHORT);
            toast.show();
            lastBack = true;
//        }
//        else
//        {
//            getFragmentManager().popBackStack();
//        }
//

    }

    private void doSignOut()
    {
        Account[] accounts = mAccountManager.getAccountsByType(Common.ARG_ACCOUNT_TYPE);
        for (Account account : accounts)
        {
            mAccountManager.removeAccount(account, null, null);
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity_.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i(TAG, "onActivityResult");
        Fragment fragment = null;
        switch (requestCode)
        {
            case Common.REQUEST_CODE_CAMERA_TAKE_PICTURE:
                fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                break;
            case Common.REQUEST_CODE_RESULT_LOAD_IMAGE:
                fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                break;
            case Common.REQUEST_CODE_RETURN_COMMENT:
                fragment = getSupportFragmentManager().findFragmentById(R.id.program_flInformation);
                break;
        }
        if (fragment != null)
        {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setOpenListOption()
    {
        mDrawerLayout.openDrawer(rlLeftDrawer);
    }

    public void setCloseListOption()
    {
        mDrawerLayout.closeDrawer(rlLeftDrawer);
    }
}
