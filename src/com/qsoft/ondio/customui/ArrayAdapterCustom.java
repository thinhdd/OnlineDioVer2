package com.qsoft.ondio.customui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.cache.Image;
import com.qsoft.ondio.data.dao.HomeContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/12/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayAdapterCustom extends SimpleCursorAdapter
{
    private TextView home_tvFeed;
    private TextView home_tvUserName;
    private TextView home_tvLike;
    private TextView home_tvComment;
    private TextView home_tvDays;
    private ImageView home_ivAvatar;
    private Image imageLoader;

    public ArrayAdapterCustom(Context context, int layout, Cursor c, String[] from, int[] to)
    {
        super(context, layout, c, from, to);
        imageLoader = new Image(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        super.bindView(view, context, cursor);
        setUpViewFindByID(view);
        home_tvFeed.setText(cursor.getString(cursor.getColumnIndex(HomeContract.TITLE)));
        home_tvUserName.setText(cursor.getString(cursor.getColumnIndex(HomeContract.DISPLAY_NAME)));
        home_tvLike.setText("likes " + cursor.getString(cursor.getColumnIndex(HomeContract.LIKES)));
        home_tvComment.setText("comments " + cursor.getString(cursor.getColumnIndex(HomeContract.COMMENTS)));
        long day = numberOfDay(cursor.getString(cursor.getColumnIndex(HomeContract.UPDATED_AT)));
        home_tvDays.setText("day " + day);
        String url = cursor.getString(cursor.getColumnIndex(HomeContract.AVATAR));
        imageLoader.DisplayImage(url, home_ivAvatar);
    }

    private void setUpViewFindByID(View v)
    {
        home_tvFeed = (TextView) v.findViewById(R.id.home_tvSoundTitle);
        home_tvUserName = (TextView) v.findViewById(R.id.home_tvUserName);
        home_tvLike = (TextView) v.findViewById(R.id.home_tvLike);
        home_tvComment = (TextView) v.findViewById(R.id.home_tvNumberComment);
        home_tvDays = (TextView) v.findViewById(R.id.home_tvDays);
        home_ivAvatar = (ImageView) v.findViewById(R.id.home_ivAvatar);
    }

    private long numberOfDay(String updated_time)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        try
        {
            Date updated_date = dateFormat.parse(updated_time);
            Date currentDate = Calendar.getInstance().getTime();
            ;
            long delta = (currentDate.getTime() - updated_date.getTime())
                    / (1000 * 60 * 60 * 24);
            if (delta > 0)
            {
                return delta;
            }
            else
            {
                return delta *= -1;
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
