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
import com.qsoft.ondio.util.Common;

public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    private Button btMenu;
    private ListView home_lvFeeds;
    private Button btNotifications;
    private ArrayAdapterCustom mAdapter;

    private static final String[] PROJECTION = new String[]{
            HomeContract._ID,
            HomeContract.TITLE,
            HomeContract.DISPLAY_NAME,
            HomeContract.UPDATED_AT,
            HomeContract.LIKES,
            HomeContract.COMMENTS,
    };

    private static final String[] FROM_COLUMNS = new String[]{
            HomeContract.TITLE,
            HomeContract.DISPLAY_NAME,
            HomeContract.LIKES,
            HomeContract.COMMENTS,
    };

    private static final int[] TO_FIELDS = new int[]{
            R.id.home_tvSoundTitle,
            R.id.home_tvUserName,
            R.id.home_tvLike,
            R.id.home_tvNumberComment
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setUpUI(view);
        setUpDataToHomeListView(getActivity());
        setUpListenerController();
        getLoaderManager().initLoader(0, null, this);
    }

    private void setUpListenerController()
    {
        btMenu.setOnClickListener(onClickListener);
        home_lvFeeds.setOnItemClickListener(onItemClickListener);
        btNotifications.setOnClickListener(onClickListener);
    }

    private void setUpDataToHomeListView(Context context)
    {
//        Bundle extras = getArguments();
//        String token = extras.getString("token");
//        homes = doSyncDataByToken();
//        if (homes.size() != 0)
//        {
        mAdapter = new ArrayAdapterCustom(
                context,
                R.layout.home_listfeeds,
                null,
                FROM_COLUMNS,
                TO_FIELDS
        );
        home_lvFeeds.setAdapter(mAdapter);
//            home_lvFeeds.setAdapter(new ArrayAdapterCustom(context, R.layout.home_listfeeds, homes));
//        }
    }

//    private ArrayList<Home> doSyncDataByToken()
//    {
//        return getDatabase();
//    }

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
        btNotifications = (Button) view.findViewById(R.id.home_btNotifications);
    }

//    public ArrayList<Home> getDatabase()
//    {
//        ArrayList<Home> homesList = new ArrayList<Home>();
//        ContentResolver contentResolver = getActivity().getContentResolver();
//        Cursor c = contentResolver.query(HomeContract.CONTENT_URI, null, null, null, null);
//        if (c.moveToFirst())
//        {
//            do
//            {
//                Home home = Home.fromCursor(c);
//                homesList.add(home);
//            } while (c.moveToNext());
//        }
//        return homesList;
//    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new CursorLoader(getActivity(), HomeContract.CONTENT_URI, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        mAdapter.changeCursor(cursor);
        Log.d("HomeFragment", ">>onLoadFinished : " + mAdapter.getCount());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {
        mAdapter.changeCursor(null);
    }
}

