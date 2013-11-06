package com.qsoft.ondio.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.qsoft.ondio.R;
import com.qsoft.ondio.activity.SlidebarActivity;
import com.qsoft.ondio.data.dao.HomeContract;
import com.qsoft.ondio.util.Common;

public class HomeFragment extends Fragment
{
    private Button btMenu;
    private Button btNotifications;
    private String TAG = "HomeFragment";
    private String accountName;
    private AccountManager accountManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home, null);
        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(getActivity());
        accountName = setting.getString("account", "n/a");
        accountManager = AccountManager.
                doSyncData();
        setUpUI(view);
        setUpListenerController();
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.home_lvFeeds, new HomeListFragment(), "HomeListFragment");
        ft.commit();
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.home_lvFeeds, new HomeListFragment(), "HomeListFragment");
        ft.commit();
    }

    private void setUpListenerController()
    {
        btMenu.setOnClickListener(onClickListener);
        btNotifications.setOnClickListener(onClickListener);
    }

    private ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            doShowProgram();
        }
    };
    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
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
    };

    private void doSyncData()
    {
        Toast.makeText(getActivity(), "Notification", Toast.LENGTH_LONG).show();

        final Account account = new Account(accountName, Common.ARG_ACCOUNT_TYPE);
        Bundle bundle = new Bundle();
        getActivity().getContentResolver().setIsSyncable(account, Common.CONTENT_AUTHORITY, 1);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true); // Performing a sync no matter if it's off
        bundle.putString(Common.TYPE_SYNC, TAG);
        getActivity().getContentResolver().requestSync(account, HomeContract.AUTHORITY, bundle);
    }

    private void showMenu()
    {
        ((SlidebarActivity) getActivity()).setOpenListOption();
    }

    private void doShowProgram()
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new ProgramFragment(), "ProgramFragment");
        ft.addToBackStack("ProgramFragment");
        ft.commit();
    }

    private void setUpUI(View view)
    {
        btMenu = (Button) view.findViewById(R.id.home_btMenu);
        btNotifications = (Button) view.findViewById(R.id.home_btNotifications);
    }
}

