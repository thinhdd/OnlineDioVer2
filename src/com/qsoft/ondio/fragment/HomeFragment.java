package com.qsoft.ondio.fragment;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.qsoft.ondio.R;
import com.qsoft.ondio.activity.SlidebarActivity;
import com.qsoft.ondio.customui.ArrayAdapterCustom;
import com.qsoft.ondio.data.dao.HomeContract;
import com.qsoft.ondio.model.Home;
import com.qsoft.ondio.util.Common;

public class HomeFragment extends Fragment {
    private Button btMenu;
    private ListView home_lvFeeds;
    private Button btNotifications;
    private ArrayAdapterCustom mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, null);
        setUpUI(view);
        setUpListenerController();
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.home_lvFeeds, new HomeListFragment(), "HomeListFragment");
        ft.commit();
        return view;
    }

    private void setUpListenerController() {
        btMenu.setOnClickListener(onClickListener);
        btNotifications.setOnClickListener(onClickListener);
    }

    private ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            doShowProgram();
        }
    };
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.home_btMenu:
                    showMenu();
                    break;
                case R.id.home_btNotifications:
                    doSyncData();
                    break;
            }
        }
    };

    private void doSyncData() {
        getActivity().getContentResolver().notifyChange(HomeContract.CONTENT_URI,null);
        Toast.makeText(getActivity(), "Notification", Toast.LENGTH_LONG).show();
        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String accountName = setting.getString("account", "n/a");
        final Account account = new Account(accountName, Common.ARG_ACCOUNT_TYPE);
        Bundle bundle = new Bundle();
        ContentResolver.setIsSyncable(account, Common.CONTENT_AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(account, Common.CONTENT_AUTHORITY, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true); // Performing a sync no matter if it's off
        ContentResolver.requestSync(account, HomeContract.AUTHORITY, bundle);
    }

    private void showMenu() {
        ((SlidebarActivity) getActivity()).setOpenListOption();
    }

    private void doShowProgram() {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new ProgramFragment(), "ProgramFragment");
        ft.addToBackStack("ProgramFragment");
        ft.commit();
    }

    private void setUpUI(View view) {
        btMenu = (Button) view.findViewById(R.id.home_btMenu);
        btNotifications = (Button) view.findViewById(R.id.home_btNotifications);
    }
}

