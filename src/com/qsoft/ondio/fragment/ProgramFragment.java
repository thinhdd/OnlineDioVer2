package com.qsoft.ondio.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;

@EFragment(R.layout.program)
public class ProgramFragment extends Fragment
{
    @ViewById(R.id.program_rgSelectInfo)
    public RadioGroup rgInfo;
    @ViewById(R.id.program_btBack)
    public Button btBack;

    @AfterViews
    void setUpProgramFragment()
    {
        doShowThumbnal();
    }

    @Click(R.id.program_btBack)
    void doExecuteEvent(View view)
    {
        switch (view.getId())
        {
            case R.id.program_btBack:
                doBack();
                break;
        }
    }

    @Click({R.id.program_rbThumbnail, R.id.program_rbDetail, R.id.program_rbComments})
    void doShowSubFragmentInfo(View view)
    {
        switch (view.getId())
        {
            case R.id.program_rbThumbnail:
                doShowThumbnal();
                break;
            case R.id.program_rbDetail:
                doShowDetail();
                break;
            case R.id.program_rbComments:
                doShowComment();
                break;
        }
    }

    private void doBack()
    {
        getFragmentManager().popBackStack();
    }

    private void doShowThumbnal()
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.program_flInformation, new ThumbnailFragment_(), "ThumbnailFragment_");
        ft.commit();
    }

    private void doShowDetail()
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.program_flInformation, new DetailFragment_(), "DetailFragment_");
        ft.commit();
    }

    private void doShowComment()
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.program_flInformation, new CommentFragment_(), "CommentFragment_");
        ft.commit();
    }
}
