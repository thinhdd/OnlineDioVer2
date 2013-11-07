package com.qsoft.ondio.fragment;

import android.content.ContentUris;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterCustom;
import com.qsoft.ondio.data.dao.HomeContract;

public class HomeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    String user_id;
    private static final String[] PROJECTION = new String[]{
            HomeContract._ID,
            HomeContract.USER_ID,
            HomeContract.TITLE,
            HomeContract.DISPLAY_NAME,
            HomeContract.UPDATED_AT,
            HomeContract.LIKES,
            HomeContract.COMMENTS,
            HomeContract.AVATAR
    };

    private static final String[] FROM_COLUMNS = new String[]{
            HomeContract.TITLE,
            HomeContract.DISPLAY_NAME,
            HomeContract.LIKES,
            HomeContract.COMMENTS,
            HomeContract.AVATAR,
    };

    private static final int[] TO_FIELDS = new int[]{
            R.id.home_tvSoundTitle,
            R.id.home_tvUserName,
            R.id.home_tvLike,
            R.id.home_tvNumberComment,
            R.id.home_ivAvatar
    };
    ArrayAdapterCustom mAdapter;

    private String TAG = "HomeListFragment";

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(getActivity());
        user_id = setting.getString("user_id", "n/a");
        setEmptyText("No data");

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
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        if(user_id.equals("586"))
        {
            user_id= "115";
        }
        Uri uri = ContentUris.withAppendedId(HomeContract.CONTENT_URI, Long.parseLong(user_id));
        return new CursorLoader(getActivity(), uri,
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
}
