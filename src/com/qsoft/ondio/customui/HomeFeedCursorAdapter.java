package com.qsoft.ondio.customui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;

public class HomeFeedCursorAdapter extends SimpleCursorAdapter
{
    public HomeFeedCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags)
    {
        super(context, layout, c, from, to, flags);
    }
}
