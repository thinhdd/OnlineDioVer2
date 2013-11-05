package com.qsoft.ondio.data.dao;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.qsoft.ondio.data.HomeHelper;

public class HomeContentProvider extends ContentProvider
{

    public static final UriMatcher URI_MATCHER = buildUriMatcher();
    public static final String PATH = "home";
    public static final int PATH_TOKEN = 100;
    public static final String PATH_FOR_ID = "home/#";
    public static final int PATH_FOR_ID_TOKEN = 200;
    private HomeHelper homeHelper;

    private static UriMatcher buildUriMatcher()
    {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = HomeContract.AUTHORITY;
        matcher.addURI(authority, PATH, PATH_TOKEN);
        matcher.addURI(authority, PATH_FOR_ID, PATH_FOR_ID_TOKEN);
        return matcher;
    }

    @Override
    public boolean onCreate()
    {
        Context ctx = getContext();
        homeHelper = new HomeHelper(ctx);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteDatabase db = homeHelper.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);
        switch (match)
        {
            // retrieve tv shows list
            case PATH_TOKEN:
            {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(HomeHelper.HOME_TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case PATH_FOR_ID_TOKEN:
            {
                int homeSoundID = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(HomeHelper.HOME_TABLE_NAME);
                builder.appendWhere(HomeContract._ID + "=" + homeSoundID);
                Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), HomeContract.CONTENT_URI);
                return cursor;
            }
            default:
                return null;
        }
    }

    @Override
    public String getType(Uri uri)
    {
        final int match = URI_MATCHER.match(uri);
        switch (match)
        {
            case PATH_TOKEN:
                return HomeContract.CONTENT_TYPE_DIR;
            case PATH_FOR_ID_TOKEN:
                return HomeContract.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported.");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        SQLiteDatabase db = homeHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        switch (token)
        {
            case PATH_TOKEN:
            {
                long id = db.insert(HomeHelper.HOME_TABLE_NAME, null, values);
                if (id != -1)
                {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return HomeContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            default:
            {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return 1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        SQLiteDatabase sqlDB = homeHelper.getWritableDatabase();
        int rowsUpdated = 0;

        String id = uri.getLastPathSegment();
        if (TextUtils.isEmpty(selection))
        {
            rowsUpdated =
                    sqlDB.update(HomeHelper.HOME_TABLE_NAME,
                            values,
                            HomeContract._ID + "=" + id,
                            null);
        }
        else
        {
            rowsUpdated =
                    sqlDB.update(HomeHelper.HOME_TABLE_NAME,
                            values,
                            HomeContract._ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
        }
        getContext().getContentResolver().notifyChange(uri, null, false);
        return rowsUpdated;
    }
}
