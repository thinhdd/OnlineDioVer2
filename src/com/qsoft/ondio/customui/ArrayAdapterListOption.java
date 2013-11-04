package com.qsoft.ondio.customui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.ondio.R;

public class ArrayAdapterListOption extends ArrayAdapter<String>
{
    private String[] slideBarOptions;
    private Context context;
    private TextView tvOption, tvSpec;
    private ImageView ivOption;

    public ArrayAdapterListOption(Context context, int textViewResourceId, String[] slideBarOptions)
    {
        super(context, textViewResourceId, slideBarOptions);
        this.slideBarOptions = slideBarOptions;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.slidebar_listoptions, null);
        }
        setUpViewFindByID(v);
        String option = slideBarOptions[position];
        if (option.equals("Home"))
        {
            tvOption.setText(option);
            ivOption.setImageResource(R.drawable.slidebar_home);
        }
        else if (option.equals("Favorite"))
        {
            tvOption.setText(option);
            ivOption.setImageResource(R.drawable.slidebar_favorite);
        }
        else if (option.equals("Following"))
        {
            tvOption.setText(option);
            ivOption.setImageResource(R.drawable.slidebar_following);
        }
        else if (option.equals("Audience"))
        {
            tvOption.setText(option);
            ivOption.setImageResource(R.drawable.slidebar_audience);
        }
        else if (option.equals("Genres"))
        {
            tvOption.setText(option);
            ivOption.setImageResource(R.drawable.slidebar_genres);
        }
        else if (option.equals("Setting"))
        {
            tvOption.setText(option);
            ivOption.setImageResource(R.drawable.slidebar_setting);
        }
        else if (option.equals("Help Center"))
        {
            tvOption.setText(option);
            tvSpec.setText("Support");
            ivOption.setImageResource(R.drawable.slidebar_helpcenter);
        }
        else
        {
            tvOption.setText(option);
            ivOption.setImageResource(R.drawable.slidebar_logout);
        }
        if (!option.equals("Help Center"))
        {
            tvSpec.setText("");
        }
        return v;
    }

    private void setUpViewFindByID(View v)
    {
        tvOption = (TextView) v.findViewById(R.id.slidebar_tvOption);
        ivOption = (ImageView) v.findViewById(R.id.slidebar_ivOption);
        tvSpec = (TextView) v.findViewById(R.id.slidebar_tvSpecOption);
    }
}
