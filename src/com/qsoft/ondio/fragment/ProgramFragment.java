package com.qsoft.ondio.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import com.qsoft.ondio.R;

/**
 * User: thinhdd
 * Date: 10/17/13
 * Time: 3:25 PM
 */
public class ProgramFragment extends Fragment
{
    private static final String TAG = "ProgramFragment";
    private static final int REQUEST_CODE = 1;
    private RadioGroup rgInfo;
    private Button btBack;
    private FragmentActivity activity;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        this.activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.program, null);
        setUpUI(view);
        doShowThumbnal();
        setUpListenerController();
        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.
//        Log.i(TAG, "onActivityResult");
////        fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.program_flInformation);
////        fragment.onActivityResult(requestCode, resultCode, data);
//    }

    private void setUpUI(View view)
    {
        rgInfo = (RadioGroup) view.findViewById(R.id.program_rgSelectInfo);
        btBack = (Button) view.findViewById(R.id.program_btBack);
    }

    private void setUpListenerController()
    {
        rgInfo.setOnCheckedChangeListener(onCheckChangeListener);
        btBack.setOnClickListener(onClickListener);
    }

    private RadioGroup.OnCheckedChangeListener onCheckChangeListener = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            switch (checkedId)
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
    };

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.program_btBack:
                    doBack();
                    break;
            }
        }
    };

    private void doBack()
    {
        getFragmentManager().popBackStack();
    }

    private void doShowThumbnal()
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.program_flInformation, new ThumbnailFragment(), "ThumbnailFragment");
        ft.commit();
    }

    private void doShowDetail()
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.program_flInformation, new DetailFragment(), "DetailFragment");
        ft.commit();
    }

    private void doShowComment()
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.program_flInformation, new CommentFragment(), "CommentFragment");
        ft.commit();
    }
}
