package com.qsoft.ondio.fragment;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import com.qsoft.ondio.R;
import com.qsoft.ondio.activity.SlidebarActivity;
import com.qsoft.ondio.customui.ArrayAdapterCustom;
import com.qsoft.ondio.data.dao.HomeContract;
import com.qsoft.ondio.model.Home;
import com.qsoft.ondio.util.Common;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    private Button btMenu;
    private ListView home_lvFeeds;
    ArrayList<Home> homes = null;
    private Account mConnectedAccount;
    private ArrayList<Home> database;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home, null);
        setUpUI(view);
        setUpDataToHomeListView(getActivity());
        setUpListenerController();
        return view;
    }

    private void setUpListenerController()
    {
        btMenu.setOnClickListener(onClickListener);
        home_lvFeeds.setOnItemClickListener(onItemClickListener);
    }

    private void setUpDataToHomeListView(Context context)
    {
        Bundle extras = getArguments();
        String token = extras.getString("token");
        homes = doSyncDataByToken(token);
        if (homes != null)
        {
            home_lvFeeds.setAdapter(new ArrayAdapterCustom(context, R.layout.home_listfeeds, homes));
        }
    }

    private ArrayList<Home> doSyncDataByToken(String token)
    {
        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String accountName = setting.getString("account", "n/a");
        final Account account = new Account(accountName, Common.ARG_ACCOUNT_TYPE);
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true); // Performing a sync no matter if it's off
        ContentResolver.requestSync(account, HomeContract.AUTHORITY, bundle);

        return getDatabase();
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
            }
        }
    };

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
        home_lvFeeds = (ListView) view.findViewById(R.id.home_lvFeeds);
    }

    public ArrayList<Home> getDatabase()
    {
        ArrayList<Home> homesList = new ArrayList<Home>();
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor c = contentResolver.query(HomeContract.CONTENT_URI, null, null, null, null);
        if (c.moveToFirst())
        {
            do
            {
                Home home = Home.fromCursor(c);
                homesList.add(home);
            } while (c.moveToNext());
        }
        return homesList;
    }

}

