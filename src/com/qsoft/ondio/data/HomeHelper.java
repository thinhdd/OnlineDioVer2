package com.qsoft.ondio.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.qsoft.ondio.data.dao.HomeContract;

public class HomeHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "onlinedio.db";
    private static final int DATABASE_VERSION = 1;
    // DB Table consts
    public static final String HOME_TABLE_NAME = "home";

    String createHomeSoundTable =
            "CREATE TABLE " + HOME_TABLE_NAME + " (" +
                    HomeContract._ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

    public HomeHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(createHomeSoundTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(HomeHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + HOME_TABLE_NAME);
        onCreate(db);
    }

}
