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
import com.qsoft.ondio.model.orm.Feed;
import com.qsoft.ondio.model.orm.FeedContract;
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
            updateLocalFeedData(provider,syncResult);
        }
        catch (ParseException e)
        {
            Log.e(TAG, "Error parsing feed: " + e.toString());
            syncResult.stats.numParseExceptions++;
        }
        catch (RemoteException e)
        {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
        }
        catch (OperationApplicationException e)
        {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
        }catch (Exception e)
        {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
        }

    }


    public void updateLocalFeedData(ContentProviderClient provider, final SyncResult syncResult)
            throws RemoteException,
            OperationApplicationException, ParseException
    {
        doSyncHome(syncResult, provider);
        mContentResolver.notifyChange(FeedContract.CONTENT_URI, null, false);

    }


    private void doSyncHome(SyncResult syncResult, ContentProviderClient provider) throws RemoteException, OperationApplicationException
    {
        final ContentResolver contentResolver = getContext().getContentResolver();
        ContentProviderClient contentProviderClient = getContext().getContentResolver().acquireContentProviderClient(FeedContract.CONTENT_URI);
        String account_id = infoAccount.getUser_id();
        final ArrayList<Feed> entries = services.getShowsFeedHome().getHomeList();
        Log.i(TAG, "Parsing complete. Found " + entries.size() + " entries");


        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        HashMap<String, Feed> entryMap = new HashMap<String, Feed>();
        for (Feed e : entries)
        {
            e.setAccount_id(account_id);
            entryMap.put(e.getFeed_id(), e);
        }
        Log.i(TAG, "Fetching local entries for merge");
//        Uri uri = ContentUris.withAppendedId(HomeContract.CONTENT_URI, Long.parseLong(account_id));
        Uri uri1 = FeedContract.CONTENT_URI;
        Cursor c = contentProviderClient.query(uri1, null
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
                _id = c.getInt(c.getColumnIndex(FeedContract._ID));
                id = c.getString(c.getColumnIndex(FeedContract.FEED_ID));
                played = c.getString(c.getColumnIndex(FeedContract.PLAYED));
                updated_at = c.getString(c.getColumnIndex(FeedContract.UPDATED_AT));
                likes = c.getString(c.getColumnIndex(FeedContract.LIKES));
                comments = c.getString(c.getColumnIndex(FeedContract.COMMENTS));
                Feed match = entryMap.get(id);
                if (match != null)
                {
                    entryMap.remove(id);
                    Uri existingUri = FeedContract.CONTENT_URI.buildUpon()
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
                    Uri deleteUri = FeedContract.CONTENT_URI.buildUpon()
                            .appendPath(Integer.toString(_id)).build();
                    Log.i(TAG, "Scheduling delete: " + deleteUri);
                    batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                    syncResult.stats.numDeletes++;
                }
            } while (c.moveToNext());
        }
        c.close();

        // Add new items
        for (Feed e : entryMap.values())
        {
            batch.add(ContentProviderOperation.newInsert(FeedContract.CONTENT_URI)
                    .withValues(e.getContentValues())
                    .build());
            syncResult.stats.numInserts++;
        }
        Log.i(TAG, "Merge solution ready. Applying batch update");
        mContentResolver.applyBatch(FeedContract.AUTHORITY, batch);
    }
}
