package com.qsoft.ondio.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.qsoft.ondio.data.dao.HomeContract;
import com.qsoft.ondio.data.dao.ProfileContract;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "onlinedio.db";
    private static final int DATABASE_VERSION = 1;
    // DB Table consts
    public static final String HOME_TABLE_NAME = "home";
    public static final String PROFILE_TABLE_NAME = "profile";


    String createHomeSoundTable =
            "CREATE TABLE " + HOME_TABLE_NAME + " (" +
                    HomeContract._ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HomeContract.ACCOUNT_ID +" TEXT, " +
                    HomeContract.ID + " TEXT, " +
                    HomeContract.USER_ID + " TEXT, " +
                    HomeContract.TITLE + " TEXT, " +
                    HomeContract.THUMBNAIL + " TEXT," +
                    HomeContract.DESCRIPTION + " TEXT," +
                    HomeContract.SOUND_PATH + " TEXT," +
                    HomeContract.DURATION + " TEXT, " +
                    HomeContract.PLAYED + " TEXT, " +
                    HomeContract.CREATED_AT + " TEXT ," +
                    HomeContract.UPDATED_AT + " TEXT ," +
                    HomeContract.LIKES + " TEXT ," +
                    HomeContract.VIEWED + " TEXT ," +
                    HomeContract.COMMENTS + " TEXT ," +
                    HomeContract.USERNAME + " TEXT ," +
                    HomeContract.DISPLAY_NAME + " TEXT ," +
                    HomeContract.AVATAR + " TEXT" +
                    ");";

    String createProfileTable =
            "CREATE TABLE " + PROFILE_TABLE_NAME + " (" +
                    ProfileContract._ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProfileContract.ID + " TEXT, " +
                    ProfileContract.FACEBOOK_ID + " TEXT, " +
                    ProfileContract.USERNAME + " TEXT, " +
                    ProfileContract.PASSWORD + " TEXT," +
                    ProfileContract.AVATAR + " TEXT," +
                    ProfileContract.COVER_IMAGE + " TEXT," +
                    ProfileContract.DISPLAY_NAME + " TEXT, " +
                    ProfileContract.FULL_NAME + " TEXT, " +
                    ProfileContract.PHONE + " TEXT ," +
                    ProfileContract.BIRTHDAY + " TEXT ," +
                    ProfileContract.GENDER + " TEXT ," +
                    ProfileContract.COUNTRY_ID + " TEXT ," +
                    ProfileContract.STORAGE_PLAN_ID + " TEXT ," +
                    ProfileContract.DESCRIPTION + " TEXT ," +
                    ProfileContract.CREATED_AT + " TEXT ," +
                    ProfileContract.UPDATE_AT + " TEXT" +
                    ");";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(createHomeSoundTable);
        database.execSQL(createProfileTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + HOME_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME);
        onCreate(db);
    }

}
