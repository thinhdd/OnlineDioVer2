package com.qsoft.ondio.data.dao;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.qsoft.ondio.data.DatabaseHelper;

public class HomeContentProvider extends ContentProvider
{

    public static final UriMatcher URI_MATCHER = buildUriMatcher();
    public static final String PATH_HOMES = "home";
    public static final String PATH_PROFILES = "profile";
    public static final int HOMES = 100;
    public static final int PROFILES = 101;
    public static final String PATH_HOME_ID = "home/#";
    public static final String PATH_PROFILE_ID = "profile/#";
    public static final int HOME_ID = 200;
    public static final int PROFILE_ID = 201;

    private DatabaseHelper dbHelper;

    private static UriMatcher buildUriMatcher()
    {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = HomeContract.AUTHORITY;
        matcher.addURI(authority, PATH_HOMES, HOMES);
        matcher.addURI(authority, PATH_HOME_ID, HOME_ID);
        matcher.addURI(authority, PATH_PROFILES, PROFILES);
        matcher.addURI(authority, PATH_PROFILE_ID, PROFILE_ID);
        return matcher;
    }

    @Override
    public boolean onCreate()
    {
        Context ctx = getContext();
        dbHelper = new DatabaseHelper(ctx);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);
        switch (match)
        {
            // retrieve tv shows list
            case HOMES:
            {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DatabaseHelper.HOME_TABLE_NAME);
                Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), HomeContract.CONTENT_URI);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case HOME_ID:
            {
                int homeSoundID = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DatabaseHelper.HOME_TABLE_NAME);
                builder.appendWhere(HomeContract._ID + "=" + homeSoundID);
                Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), HomeContract.CONTENT_URI);
                return cursor;
            }
            case PROFILES:
            {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DatabaseHelper.PROFILE_TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case PROFILE_ID:
            {
                int profileID = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DatabaseHelper.PROFILE_TABLE_NAME);
                builder.appendWhere(ProfileContract._ID + "=" + profileID);
                Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), ProfileContract.CONTENT_URI);
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
            case HOMES:
                return HomeContract.CONTENT_TYPE_DIR;
            case HOME_ID:
                return HomeContract.CONTENT_ITEM_TYPE;
            case PROFILES:
                return ProfileContract.CONTENT_TYPE_DIR;
            case PROFILE_ID:
                return ProfileContract.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported.");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        switch (token)
        {
            case HOMES:
            {
                long id = db.insert(DatabaseHelper.HOME_TABLE_NAME, null, values);
                if (id != -1)
                {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return HomeContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case PROFILES:
            {
                long id = db.insert(DatabaseHelper.PROFILE_TABLE_NAME, null, values);
                if (id != -1)
                {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return ProfileContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
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
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        int rowsUpdated = 0;

        String id = uri.getLastPathSegment();
        int token = URI_MATCHER.match(uri);
        switch (token)
        {
            case HOME_ID:
                if (TextUtils.isEmpty(selection))
                {
                    rowsUpdated =
                            sqlDB.update(DatabaseHelper.HOME_TABLE_NAME,
                                    values,
                                    HomeContract._ID + "=" + id,
                                    null);
                }
                else
                {
                    rowsUpdated =
                            sqlDB.update(DatabaseHelper.HOME_TABLE_NAME,
                                    values,
                                    HomeContract._ID + "=" + id
                                            + " and "
                                            + selection,
                                    selectionArgs);
                }
                break;
            case PROFILE_ID:
                if (TextUtils.isEmpty(selection))
                {
                    rowsUpdated =
                            sqlDB.update(DatabaseHelper.PROFILE_TABLE_NAME,
                                    values,
                                    ProfileContract._ID + "=" + id,
                                    null);
                }
                else
                {
                    rowsUpdated =
                            sqlDB.update(DatabaseHelper.PROFILE_TABLE_NAME,
                                    values,
                                    ProfileContract._ID + "=" + id
                                            + " and "
                                            + selection,
                                    selectionArgs);
                }
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
