package com.qsoft.ondio.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.ondio.data.ParseComServerAccessor;
import com.qsoft.ondio.data.dao.HomeContract;
import com.qsoft.ondio.model.Home;
import com.qsoft.ondio.restservice.Interceptor;
import com.qsoft.ondio.restservice.Services;
import com.qsoft.ondio.util.Common;
import com.qsoft.ondio.util.ShareInfoAccount;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EBean
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
    @RestService
    Services services;

    @Bean
    Interceptor interceptor;

    @Bean
    ShareInfoAccount infoAccount;

    @SystemService
    AccountManager accountManager;

    @AfterInject
    public void init() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(interceptor);
        services.getRestTemplate().setInterceptors(interceptors);
    }

    public static final String TAG = "SyncAdapter";

    private static final String FEED_URL = "http://113.160.50.84:1009/testing/ica467/trunk/public/home-rest";

    private final ContentResolver mContentResolver;

    public SyncAdapter(Context context)
    {
        super(context, true);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult)
    {
        Log.i(TAG, "Beginning network synchronization");


        try
        {
            Log.i(TAG, "Streaming data from network: " + FEED_URL);
            updateLocalFeedData(account, syncResult);
        }
        catch (ParseException e)
        {
            Log.e(TAG, "Error parsing feed: " + e.toString());
            syncResult.stats.numParseExceptions++;
            return;
        }
        catch (RemoteException e)
        {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
            return;
        }
        catch (OperationApplicationException e)
        {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
            return;
        }catch (Exception e)
        {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
            return;
        }

    }


    public void updateLocalFeedData(Account account, final SyncResult syncResult)
            throws RemoteException,
            OperationApplicationException, ParseException
    {
        doSyncHome(account, syncResult);
        mContentResolver.notifyChange(HomeContract.CONTENT_URI, null, false);

    }


    private void doSyncHome(Account account, SyncResult syncResult) throws RemoteException, OperationApplicationException
    {
        final ContentResolver contentResolver = getContext().getContentResolver();
        String account_id = infoAccount.getUser_id();
        final ArrayList<Home> entries = services.getShowsFeedHome().getHomeList();
        Log.i(TAG, "Parsing complete. Found " + entries.size() + " entries");


        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        HashMap<String, Home> entryMap = new HashMap<String, Home>();
        for (Home e : entries)
        {
            e.setAccount_id(account_id);
            entryMap.put(e.getId(), e);
        }
        Log.i(TAG, "Fetching local entries for merge");
        Uri uri = ContentUris.withAppendedId(HomeContract.CONTENT_URI, Long.parseLong(account_id));
        Cursor c = contentResolver.query(uri, null
                , null, null, null);
        Log.i(TAG, "Found " + c.getCount() + " local entries. Computing merge solution...");

        // Find stale data
        int _id;
        String id; // feed's id
        String played;
        String updated_at;
        String likes;
        String comments;
        if (c.moveToFirst())
        {
            do
            {
                syncResult.stats.numEntries++;
                _id = c.getInt(Common.COLUMN_ID);
                id = c.getString(Common.COLUMN_FEED_ID);
                played = c.getString(Common.COLUMN_PLAYED);
                updated_at = c.getString(Common.COLUMN_UPDATED_AT);
                likes = c.getString(Common.COLUMN_LIKES);
                comments = c.getString(Common.COLUMN_COMMENTS);
                Home match = entryMap.get(id);
                if (match != null)
                {
                    entryMap.remove(id);
                    Uri existingUri = HomeContract.CONTENT_URI.buildUpon()
                            .appendPath(Integer.toString(_id)).build();
                    if ((match.updated_at != null && !match.updated_at
                            .equals(updated_at))
                            || match.likes.equals(likes)
                            || match.comments.equals(comments) || match.played.equals(played))
                    {
                        // Update existing record
                        Log.i(TAG, "Scheduling update: " + existingUri);
                        batch.add(ContentProviderOperation.newUpdate(existingUri)
                                .withValues(match.getContentValues())
                                .build());
                        syncResult.stats.numUpdates++;
                    }
                    else
                    {
                        Log.i(TAG, "No action: " + existingUri);
                    }
                }
                else
                {
                    // Entry doesn't exist. Remove it from the database.
                    Uri deleteUri = HomeContract.CONTENT_URI.buildUpon()
                            .appendPath(Integer.toString(_id)).build();
                    Log.i(TAG, "Scheduling delete: " + deleteUri);
                    batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                    syncResult.stats.numDeletes++;
                }
            } while (c.moveToNext());
        }
        c.close();

        // Add new items
        for (Home e : entryMap.values())
        {
            batch.add(ContentProviderOperation.newInsert(HomeContract.CONTENT_URI)
                    .withValues(e.getContentValues())
                    .build());
            syncResult.stats.numInserts++;
        }
        Log.i(TAG, "Merge solution ready. Applying batch update");
        mContentResolver.applyBatch(HomeContract.AUTHORITY, batch);
    }
}
