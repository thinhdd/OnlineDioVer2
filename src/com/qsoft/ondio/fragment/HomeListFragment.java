package com.qsoft.ondio.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterCustom;
import com.qsoft.ondio.data.dao.HomeContract;

/**
 * Created with IntelliJ IDEA.
 * User: RuaTre_IT
 * Date: 11/4/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

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

    private String TAG = "HomeListFragment";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText("No data");

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new ArrayAdapterCustom(
                getActivity(),
                R.layout.home_listfeeds,
                null,
                FROM_COLUMNS,
                TO_FIELDS
        );

        setListAdapter(mAdapter);
        setListShown(false);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), HomeContract.CONTENT_URI,
                PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        setListShown(true);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
