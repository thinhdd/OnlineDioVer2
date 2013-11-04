package com.qsoft.ondio.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.qsoft.ondio.data.ParseComServerAccessor;
import com.qsoft.ondio.data.dao.HomeContract;
import com.qsoft.ondio.model.Home;
import com.qsoft.ondio.util.Common;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

class SyncAdapter extends AbstractThreadedSyncAdapter
{
    public static final String TAG = "SyncAdapter";

    private static final String FEED_URL = "http://113.160.50.84:1009/testing/ica467/trunk/public/home-rest";
    private final AccountManager mAccountManager;

    private final ContentResolver mContentResolver;
    private static final String[] PROJECTION = new String[]{
            HomeContract._ID,
            HomeContract.ID,
            HomeContract.USER_ID,
            HomeContract.TITLE,
            HomeContract.THUMBNAIL,
            HomeContract.DESCRIPTION,
            HomeContract.SOUND_PATH,
            HomeContract.DURATION,
            HomeContract.PLAYED,
            HomeContract.CREATED_AT,
            HomeContract.UPDATED_AT,
            HomeContract.LIKES,
            HomeContract.VIEWED,
            HomeContract.COMMENTS,
            HomeContract.USERNAME,
            HomeContract.DISPLAY_NAME,
            HomeContract.AVATAR};

    // Constants representing column positions from PROJECTION.
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_FEED_ID = 1;
    public static final int COLUMN_USER_ID = 2;
    public static final int COLUMN_TITLE = 3;
    public static final int COLUMN_THUMBNAIL = 4;
    public static final int COLUMN_DESCRIPTION = 5;
    public static final int COLUMN_SOUND_PATH = 6;
    public static final int COLUMN_DURATION = 7;
    public static final int COLUMN_PLAYED = 8;
    public static final int COLUMN_CREATED_AT = 9;
    public static final int COLUMN_UPDATED_AT = 10;
    public static final int COLUMN_LIKES = 11;
    public static final int COLUMN_VIEWED = 12;
    public static final int COLUMN_COMMENTS = 13;
    public static final int COLUMN_USERNAME = 14;
    public static final int COLUMN_DISPLAY_NAME = 15;
    public static final int COLUMN_AVATAR = 16;

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);

    }

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs)
    {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);
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
        }
        Log.i(TAG, "Network synchronization complete");
    }

    public void updateLocalFeedData(Account account, final SyncResult syncResult)
            throws RemoteException,
            OperationApplicationException, ParseException
    {
        final ParseComServerAccessor feedParser = new ParseComServerAccessor();
        final ContentResolver contentResolver = getContext().getContentResolver();

        String authToken = mAccountManager.peekAuthToken(account,
                Common.AUTHTOKEN_TYPE_FULL_ACCESS);
        final ArrayList<Home> entries = feedParser.getShows(authToken);
        Log.i(TAG, "Parsing complete. Found " + entries.size() + " entries");


        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        // Build hash table of incoming entries
        HashMap<String, Home> entryMap = new HashMap<String, Home>();
        for (Home e : entries)
        {
            entryMap.put(e.getId(), e);
        }

        // Get list of all items
        Log.i(TAG, "Fetching local entries for merge");
        Uri uri = HomeContract.CONTENT_URI; // Get all entries
        Cursor c = contentResolver.query(uri, PROJECTION, null, null, null);
        Log.i(TAG, "Found " + c.getCount() + " local entries. Computing merge solution...");

        // Find stale data
        int _id;
        String id; // feed's id
        String user_id;
        String title;
        String thumbnail;
        String description;
        String sound_path;
        String duration;
        String played;
        String created_at;
        String updated_at;
        String likes;
        String views;
        String comments;
        String username;
        String display_name;
        String avatar;
        if (c.moveToFirst())
        {
            do
            {
                syncResult.stats.numEntries++;
                _id = c.getInt(COLUMN_ID);
                id = c.getString(COLUMN_FEED_ID);
                user_id = c.getString(COLUMN_USER_ID);
                title = c.getString(COLUMN_TITLE);
                thumbnail = c.getString(COLUMN_THUMBNAIL);
                description = c.getString(COLUMN_DESCRIPTION);
                sound_path = c.getString(COLUMN_SOUND_PATH);
                duration = c.getString(COLUMN_DURATION);
                played = c.getString(COLUMN_PLAYED);
                created_at = c.getString(COLUMN_CREATED_AT);
                updated_at = c.getString(COLUMN_UPDATED_AT);
                likes = c.getString(COLUMN_LIKES);
                views = c.getString(COLUMN_VIEWED);
                comments = c.getString(COLUMN_COMMENTS);
                username = c.getString(COLUMN_USERNAME);
                display_name = c.getString(COLUMN_DISPLAY_NAME);
                avatar = c.getString(COLUMN_AVATAR);
                Home match = entryMap.get(id);
                if (match != null)
                {
                    // Entry exists. Remove from entry map to prevent insert later.
                    entryMap.remove(id);
                    // Check to see if the entry needs to be updated
                    Uri existingUri = HomeContract.CONTENT_URI.buildUpon()
                            .appendPath(Integer.toString(_id)).build();
                    if ((match.updated_at != null && !match.updated_at
                            .equals(updated_at))
                            || match.likes != likes
                            || match.comments != comments || match.played != played)
                    {
                        // Update existing record
                        Log.i(TAG, "Scheduling update: " + existingUri);
                        batch.add(ContentProviderOperation.newUpdate(existingUri)
                                .withValue(HomeContract.ID, match.id)
                                .withValue(HomeContract.USER_ID,
                                        match.user_id)
                                .withValue(HomeContract.TITLE,
                                        match.title)
                                .withValue(
                                        HomeContract.THUMBNAIL,
                                        match.thumbnail)
                                .withValue(
                                        HomeContract.DESCRIPTION,
                                        match.description)
                                .withValue(
                                        HomeContract.SOUND_PATH,
                                        match.sound_path)
                                .withValue(HomeContract.DURATION,
                                        match.duration)
                                .withValue(HomeContract.PLAYED,
                                        match.played)
                                .withValue(
                                        HomeContract.CREATED_AT,
                                        match.created_at)
                                .withValue(
                                        HomeContract.UPDATED_AT,
                                        match.updated_at)
                                .withValue(HomeContract.LIKES,
                                        match.likes)
                                .withValue(HomeContract.VIEWED,
                                        match.viewed)
                                .withValue(HomeContract.COMMENTS,
                                        match.comments)
                                .withValue(HomeContract.USERNAME,
                                        match.username)
                                .withValue(
                                        HomeContract.DISPLAY_NAME,
                                        match.display_name)
                                .withValue(HomeContract.AVATAR,
                                        match.avatar).build());
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
                    .withValue(HomeContract.ID, e.id)
                    .withValue(HomeContract.USER_ID,
                            e.user_id)
                    .withValue(HomeContract.TITLE, e.title)
                    .withValue(HomeContract.THUMBNAIL,
                            e.thumbnail)
                    .withValue(HomeContract.DESCRIPTION,
                            e.description)
                    .withValue(HomeContract.SOUND_PATH,
                            e.sound_path)
                    .withValue(HomeContract.DURATION,
                            e.duration)
                    .withValue(HomeContract.PLAYED, e.played)
                    .withValue(HomeContract.CREATED_AT,
                            e.created_at)
                    .withValue(HomeContract.UPDATED_AT,
                            e.updated_at)
                    .withValue(HomeContract.LIKES, e.likes)
                    .withValue(HomeContract.VIEWED, e.viewed)
                    .withValue(HomeContract.COMMENTS,
                            e.comments)
                    .withValue(HomeContract.USERNAME,
                            e.username)
                    .withValue(HomeContract.DISPLAY_NAME,
                            e.display_name)
                    .withValue(HomeContract.AVATAR, e.avatar)
                    .build());
            syncResult.stats.numInserts++;
        }
        Log.i(TAG, "Merge solution ready. Applying batch update");
        mContentResolver.applyBatch(HomeContract.AUTHORITY, batch);
        mContentResolver.notifyChange(
                HomeContract.CONTENT_URI, // URI where data was modified
                null,                           // No local observer
                false);                         // IMPORTANT: Do not sync to network
    }
}
