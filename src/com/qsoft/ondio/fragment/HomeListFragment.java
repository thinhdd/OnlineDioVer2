package com.qsoft.ondio.fragment;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterCustom;
import com.qsoft.ondio.data.dao.HomeContract;
import com.qsoft.ondio.util.Common;

/**
 * Created with IntelliJ IDEA.
 * User: RuaTre_IT
 * Date: 11/4/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{

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
    ArrayAdapterCustom mAdapter;
    private Object mSyncObserverHandle;

    private String TAG = "HomeListFragment";
    private String accountName;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        setEmptyText("No data");
        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(getActivity());
        accountName = setting.getString("account", "n/a");

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new ArrayAdapterCustom(
                getActivity(),
                R.layout.home_listfeeds,
                null,
                FROM_COLUMNS,
                TO_FIELDS
        );
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder()
        {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int i)
            {
                return false;
            }
        });

        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mSyncStatusObserver.onStatusChanged(0);

        // Watch for sync state changes
        final int mask = ContentResolver.SYNC_OBSERVER_TYPE_PENDING |
                ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
        mSyncObserverHandle = ContentResolver.addStatusChangeListener(mask, mSyncStatusObserver);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mSyncObserverHandle != null)
        {
            ContentResolver.removeStatusChangeListener(mSyncObserverHandle);
            mSyncObserverHandle = null;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return new CursorLoader(getActivity(), HomeContract.CONTENT_URI,
                PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        mAdapter.swapCursor(null);
    }

    private SyncStatusObserver mSyncStatusObserver = new SyncStatusObserver()
    {
        @Override
        public void onStatusChanged(int which)
        {
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    final Account account = new Account(accountName, Common.ARG_ACCOUNT_TYPE);
                    boolean syncActive = ContentResolver.isSyncActive(
                            account, Common.CONTENT_AUTHORITY);
                    boolean syncPending = ContentResolver.isSyncPending(
                            account, Common.CONTENT_AUTHORITY);
                    if (syncPending)
                    {
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.home_lvFeeds, new HomeListFragment(), "HomeListFragment");
                        ft.commit();
                    }
                }
            });
        }
    };
}
