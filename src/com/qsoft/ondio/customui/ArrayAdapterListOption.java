package com.qsoft.ondio.customui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.ondio.R;

@EBean
public class ArrayAdapterListOption extends BaseAdapter
{
    final String[] slideBarOptions = {"Home", "Favorite", "Following", "Audience", "Genres", "Setting", "Help Center", "Sign Out"};

    @RootContext
    Context context;

    @ViewById(R.id.slidebar_tvOption)
    TextView tvOption;

    @ViewById(R.id.slidebar_tvSpecOption)
    TextView tvSpec;

    @ViewById(R.id.slidebar_ivOption)
    ImageView ivOption;

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        OptionItemView optionItemView;
        View v = convertView;
        if (v == null)
        {
            optionItemView = OptionItemView_.build(context);
        }
        else
            optionItemView = (OptionItemView) convertView;
        optionItemView.bind(slideBarOptions[position]);
        return optionItemView;
    }

    @Override
    public int getCount()
    {
        return slideBarOptions.length;
    }

    @Override
    public Object getItem(int position)
    {
        return slideBarOptions[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
}
