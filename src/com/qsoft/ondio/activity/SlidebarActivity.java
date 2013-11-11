package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterListOption;
import com.qsoft.ondio.fragment.HomeFragment;
import com.qsoft.ondio.fragment.ProfileFragment;
import com.qsoft.ondio.util.Common;

/**
 * User: thinhdd
 * Date: 10/16/13
 * Time: 1:55 PM
 */

public class SlidebarActivity extends FragmentActivity
{
    private static final String TAG = "SlidebarActivity";

    private static final int REQUEST_CODE_CAMERA_TAKE_PICTURE = 999;
    private static final int REQUEST_CODE_RESULT_LOAD_IMAGE = 888;
    private static final int REQUEST_CODE_RETURN_COMMENT = 777;

    final String[] listOptionName = {"Home", "Favorite", "Following", "Audience", "Genres", "Setting", "Help Center", "Sign Out"};
    final String[] item = {"com.qsoft.ondio.fragment.HomeFragment"};
    private static final int HOME = 0;
    private static final int SIGN_OUT = 7;
    private static String token;
    private DrawerLayout mDrawerLayout;
    private ListView lvOption;
    private RelativeLayout rlLeftDrawer;
    private RelativeLayout slidebar_rlProfile;
    FragmentTransaction fragmentTransaction;
    AccountManager mAccountManager;
    Boolean lastBack = false;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidebar);
        mAccountManager = AccountManager.get(getBaseContext());
        setUpUI();
        setUpDataListOption(this);
        setUpListenerController();

        Fragment homeFragment = new HomeFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, homeFragment, "HomeFragment");
        fragmentTransaction.commit();

    }

    private void setUpUI()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        lvOption = (ListView) findViewById(R.id.slidebar_listOption);
        rlLeftDrawer = (RelativeLayout) findViewById(R.id.left_drawer);
        slidebar_rlProfile = (RelativeLayout) findViewById(R.id.slidebar_rlProfile);
    }

    private void setUpListenerController()
    {
        slidebar_rlProfile.setOnClickListener(onClickListener);
        lvOption.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onBackPressed()
    {

        if (lastBack)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        Toast toast = Toast.makeText(this, "Press Back again to exit program", Toast.LENGTH_SHORT);
        toast.show();
        lastBack = true;

    }

    private final ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int index, long l)
        {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (index)
            {
                case HOME:
                    ft.replace(R.id.content_frame, new HomeFragment(), "HomeFragment");
                    ft.addToBackStack("HomeFragment");
                    ft.commit();
                    break;
                case SIGN_OUT:
                    doSignOut();
                    break;
            }
            setCloseListOption();
        }
    };

    private void doSignOut()
    {
//        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        String accountName = setting.getString("account", "n/a");
//        Account account = new Account(accountName, Common.ARG_ACCOUNT_TYPE);
//        String token = mAccountManager.peekAuthToken(account, Common.ARG_ACCOUNT_TYPE);
//        mAccountManager.invalidateAuthToken(Common.ARG_ACCOUNT_TYPE, token);
        Account[] accounts = mAccountManager.getAccountsByType(Common.ARG_ACCOUNT_TYPE);
        for (Account account : accounts)
        {
            mAccountManager.removeAccount(account, null, null);
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.putExtra("IS_ADDING_ACCOUNT", true);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.slidebar_rlProfile:
                    doEditProfile();
                    break;
            }
        }
    };

    private void doEditProfile()
    {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new ProfileFragment(), "ProfileFragment");
        ft.addToBackStack("ProfileFragment");
        ft.commit();
        setCloseListOption();
    }

    private void setUpDataListOption(Context context)
    {
        lvOption.setAdapter(new ArrayAdapterListOption(context, R.layout.slidebar_listoptions, listOptionName));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i(TAG, "onActivityResult");
        Fragment fragment = null;
        switch (requestCode)
        {
            case REQUEST_CODE_CAMERA_TAKE_PICTURE:
                fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                break;
            case REQUEST_CODE_RESULT_LOAD_IMAGE:
                fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                break;
            case REQUEST_CODE_RETURN_COMMENT:
                fragment = getSupportFragmentManager().findFragmentById(R.id.program_flInformation);
                break;
        }
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    public void setOpenListOption()
    {
        mDrawerLayout.openDrawer(rlLeftDrawer);
    }

    public void setCloseListOption()
    {
        mDrawerLayout.closeDrawer(rlLeftDrawer);
    }

    public void doBackToPrevious()
    {
        getSupportFragmentManager().popBackStack();
    }

    public void doLockOption()
    {
        getSupportFragmentManager().executePendingTransactions();
    }
}
