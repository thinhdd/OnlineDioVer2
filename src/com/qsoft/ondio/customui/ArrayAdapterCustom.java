package com.qsoft.ondio.customui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.model.Home;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/12/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayAdapterCustom extends ArrayAdapter<Home>
{
    private TextView home_tvFeed;
    private TextView home_tvUserName;
    private TextView home_tvLike;
    private TextView home_tvComment;
    private TextView home_tvDays;
    private final ArrayList<Home> homes;
    private final Context context;

    public ArrayAdapterCustom(Context context, int textViewResourceId, ArrayList<Home> homes)
    {
        super(context, textViewResourceId, homes);
        this.homes = homes;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.home_listfeeds, null);
        }
        Home home = homes.get(position);
        setUpViewFindByID(v);
        if (home != null)
        {
            home_tvFeed.setText(home.title);
            home_tvUserName.setText(home.username);
            home_tvLike.setText("Likes " + home.likes);
            home_tvComment.setText("Comments " + home.comments);
            home_tvDays.setText(numberOfDay(home.updated_at) + "");
        }
        return v;
    }

    private void setUpViewFindByID(View v)
    {
        home_tvFeed = (TextView) v.findViewById(R.id.home_tvSoundTitle);
        home_tvUserName = (TextView) v.findViewById(R.id.home_tvUserName);
        home_tvLike = (TextView) v.findViewById(R.id.home_tvLike);
        home_tvComment = (TextView) v.findViewById(R.id.home_tvNumberComment);
        home_tvDays = (TextView) v.findViewById(R.id.home_tvDays);
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
