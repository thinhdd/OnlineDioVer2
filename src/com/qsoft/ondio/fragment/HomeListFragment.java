package com.qsoft.ondio.fragment;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterCustom;
import com.qsoft.ondio.model.orm.FeedContract;
import com.qsoft.ondio.util.ShareInfoAccount;

@EFragment
public class HomeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    String account_id;
    @Bean
    ShareInfoAccount infoAccount;

    private static final String[] PROJECTION = new String[]{
            FeedContract.ACCOUNT_ID,
            FeedContract._ID,
            FeedContract.USER_ID,
            FeedContract.TITLE,
            FeedContract.DISPLAY_NAME,
            FeedContract.UPDATED_AT,
            FeedContract.LIKES,
            FeedContract.COMMENTS,
            FeedContract.AVATAR
    };

    private static final String[] FROM_COLUMNS = new String[]{
            FeedContract.TITLE,
            FeedContract.DISPLAY_NAME,
            FeedContract.LIKES,
            FeedContract.COMMENTS,
            FeedContract.AVATAR,
    };

    private static final int[] TO_FIELDS = new int[]{
            R.id.home_tvSoundTitle,
            R.id.home_tvUserName,
            R.id.home_tvLike,
            R.id.home_tvNumberComment,
            R.id.home_ivAvatar
    };
    ArrayAdapterCustom mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        account_id = infoAccount.getUser_id();
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
        setListShown(false);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new ProgramFragment_(), "ProgramFragment_");
        ft.addToBackStack("ProgramFragment_");
        ft.commit();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        String selection = FeedContract.ACCOUNT_ID+" = ? ";
        String[] selectionArgs = new String[]{account_id};
        return new CursorLoader(getActivity(), FeedContract.CONTENT_URI,
                PROJECTION, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        mAdapter.swapCursor(data);
        if(isResumed()){
            setListShown(true);
        }
        else{
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        mAdapter.swapCursor(null);
    }


}
