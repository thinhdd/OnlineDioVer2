package com.qsoft.ondio.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qsoft.ondio.R;

/**
 * User: thinhdd
 * Date: 10/18/13
 * Time: 9:35 AM
 */
public class ThumbnailFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.thumbnail, null);
    }
}
