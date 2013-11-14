package com.qsoft.ondio.customui;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.ondio.R;

@EViewGroup(R.layout.slidebar_listoptions)
public class OptionItemView extends RelativeLayout
{
    @ViewById(R.id.slidebar_tvOption)
    TextView tvOption;

    @ViewById(R.id.slidebar_tvSpecOption)
    TextView tvSpec;

    @ViewById(R.id.slidebar_ivOption)
    ImageView ivOption;

    public OptionItemView(Context context)
    {
        super(context);
    }
    public void bind(String option)
    {
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
    }
}
