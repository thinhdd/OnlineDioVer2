//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.ondio.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qsoft.ondio.util.ShareInfoAccount_;

public final class HomeListFragment_
    extends HomeListFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
        infoAccount = ShareInfoAccount_.getInstance_(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        ((ShareInfoAccount_) infoAccount).afterSetContentView_();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        return contentView_;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterSetContentView_();
    }

    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    public static HomeListFragment_.FragmentBuilder_ builder() {
        return new HomeListFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public HomeListFragment build() {
            HomeListFragment_ fragment_ = new HomeListFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
