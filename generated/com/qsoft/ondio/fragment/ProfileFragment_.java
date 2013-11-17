//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.ondio.fragment;

import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import com.qsoft.ondio.R.layout;
import com.qsoft.ondio.data.ParseComServerAccessor_;
import com.qsoft.ondio.restservice.Interceptor_;
import com.qsoft.ondio.restservice.Services_;
import com.qsoft.ondio.util.ShareInfoAccount_;

public final class ProfileFragment_
    extends ProfileFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
        mAccountManager = ((AccountManager) getActivity().getSystemService(Context.ACCOUNT_SERVICE));
        services = new Services_();
        infoAccount = ShareInfoAccount_.getInstance_(getActivity());
        interceptor = Interceptor_.getInstance_(getActivity());
        parseCom = ParseComServerAccessor_.getInstance_(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        scroll = ((ScrollView) findViewById(com.qsoft.ondio.R.id.profile_svScroll));
        etDescription = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etDescription));
        btMale = ((Button) findViewById(com.qsoft.ondio.R.id.profile_btMale));
        ivAvatar = ((ImageView) findViewById(com.qsoft.ondio.R.id.profile_ivAvatar));
        etBirthday = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etBirthday));
        etProfileName = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etProfileName));
        etFullName = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etFullName));
        etPhoneNo = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etPhoneNo));
        spCountry = ((Spinner) findViewById(com.qsoft.ondio.R.id.profile_spCountry));
        btMenu = ((Button) findViewById(com.qsoft.ondio.R.id.profile_btMenu));
        ivCoverImage = ((ImageView) findViewById(com.qsoft.ondio.R.id.profile_ivCoverImage));
        btSave = ((Button) findViewById(com.qsoft.ondio.R.id.profile_btSave));
        btFemale = ((Button) findViewById(com.qsoft.ondio.R.id.profile_btFemale));
        etCountry = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etCountry));
        ((ShareInfoAccount_) infoAccount).afterSetContentView_();
        ((Interceptor_) interceptor).afterSetContentView_();
        ((ParseComServerAccessor_) parseCom).afterSetContentView_();
        setUpProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.profile, container, false);
        }
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

    public static ProfileFragment_.FragmentBuilder_ builder() {
        return new ProfileFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public ProfileFragment build() {
            ProfileFragment_ fragment_ = new ProfileFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
