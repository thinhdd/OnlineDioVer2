package com.qsoft.ondio.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;
import com.qsoft.ondio.activity.SlidebarActivity;
import com.qsoft.ondio.cache.Image;
import com.qsoft.ondio.cache.ProfileAvatarLoader;
import com.qsoft.ondio.data.ParseComServerAccessor;
import com.qsoft.ondio.data.dao.ProfileContract;
import com.qsoft.ondio.dialog.MyDialog;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.model.ProfileResponse;
import com.qsoft.ondio.util.Common;
import com.qsoft.ondio.util.ShareInfoAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@EFragment(R.layout.profile)
public class ProfileFragment extends Fragment
{
    private static final String TAG = "ProfileFragment";

    @ViewById(R.id.profile_etProfileName)
    public EditText etProfileName;
    @ViewById(R.id.profile_etFullName)
    public EditText etFullName;
    @ViewById(R.id.profile_etPhoneNo)
    public EditText etPhoneNo;
    @ViewById(R.id.profile_etBirthday)
    public EditText etBirthday;
    @ViewById(R.id.profile_btMale)
    public Button btMale;
    @ViewById(R.id.profile_btFemale)
    public Button btFemale;
    @ViewById(R.id.profile_etCountry)
    public EditText etCountry;
    @ViewById(R.id.profile_spCountry)
    public Spinner spCountry;
    @ViewById(R.id.profile_etDescription)
    public EditText etDescription;
    @ViewById(R.id.profile_ivAvatar)
    public ImageView ivAvatar;
    @ViewById(R.id.profile_ivCoverImage)
    public ImageView ivCoverImage;
    @ViewById(R.id.profile_svScroll)
    public ScrollView scroll;
    @ViewById(R.id.profile_btSave)
    public Button btSave;
    @ViewById(R.id.profile_btMenu)
    public Button btMenu;


    private static int gender;

    private static int code;
    private String token;
    private String user_id;
    @SystemService
    AccountManager mAccountManager;

    @Bean
    ShareInfoAccount infoAccount;

    private Account account;

    private Image image;

    @AfterViews
    void setUpProfileFragment()
    {
        setAccountCurrent();
        image = new Image(getActivity());
        getDataToLocalDB();
        doSetupDataToView();
        setUpListenerController();
    }

    private void setAccountCurrent()
    {
        account = infoAccount.getAccount();
        user_id= infoAccount.getUser_id();
    }

    private void doSetupDataToView()
    {
        Cursor cursor = getActivity().getContentResolver().query(ProfileContract.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst())
        {
            int gender = cursor.getInt(cursor.getColumnIndex(ProfileContract.GENDER));
            setGender(gender);
            etProfileName.setText(cursor.getString(cursor.getColumnIndex(ProfileContract.DISPLAY_NAME)));
            etFullName.setText(cursor.getString(cursor.getColumnIndex(ProfileContract.FULL_NAME)));
            etPhoneNo.setText(cursor.getString(cursor.getColumnIndex(ProfileContract.PHONE)));
            etBirthday.setText(cursor.getString(cursor.getColumnIndex(ProfileContract.BIRTHDAY)));
            String code = cursor.getString(cursor.getColumnIndex(ProfileContract.COUNTRY_ID));
            String name = convertToCountryName(code);
            etCountry.setText(name);
            etDescription.setText(cursor.getString(cursor.getColumnIndex(ProfileContract.DESCRIPTION)));
            String urlAvatar = cursor.getString(cursor.getColumnIndex(ProfileContract.AVATAR));
            String urlCover = cursor.getString(cursor.getColumnIndex(ProfileContract.COVER_IMAGE));
            image.DisplayImage(urlCover, ivCoverImage);
            new ProfileAvatarLoader(getActivity(), this).DisplayImage(urlAvatar, ivAvatar);

        }
    }

    private void setUpListenerController()
    {
        etBirthday.setOnClickListener(onClickListener);
        etCountry.setOnClickListener(onClickListener);
        btMale.setOnClickListener(onClickListener);
        btFemale.setOnClickListener(onClickListener);
        ivAvatar.setOnClickListener(onClickListener);
        ivCoverImage.setOnClickListener(onClickListener);
        etDescription.setOnClickListener(onClickListener);
        btSave.setOnClickListener(onClickListener);
        btMenu.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.profile_ivAvatar:
                    setAvatar();
                    break;
                case R.id.profile_rlCoverImage:
                    setCoverImage();
                    break;
                case R.id.profile_etBirthday:
                    setBirthday();
                    break;
                case R.id.profile_etCountry:
                    setCountry();
                    break;
                case R.id.profile_btMale:
                    setGender(Common.MALE);
                    break;
                case R.id.profile_btFemale:
                    setGender(Common.FEMALE);
                    break;
                case R.id.profile_etDescription:
                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                    break;
                case R.id.profile_btSave:
                    doSave();
                    break;
                case R.id.profile_btMenu:
                    showMenu();
                    break;
            }
        }
    };

    private void setBirthday()
    {
        MyDialog.showDatePickerDialog(getActivity(), etBirthday);
    }

    private void showMenu()
    {
        ((SlidebarActivity) getActivity()).setOpenListOption();
    }

    private void doSave()
    {
        Profile profile = new Profile();
        if (checkFieldEmpty())
        {
            profile.setDisplay_name(etProfileName.getText().toString());
        }
        profile.setFull_name(etFullName.getText().toString());
        profile.setPhone(etPhoneNo.getText().toString());
        profile.setBirthday(etBirthday.getText().toString());
        profile.setGender(gender);
        String name = etCountry.getText().toString();
        String code = convertToCountryCode(name);
        profile.setCountry_id(code);
        profile.setDescription(etDescription.getText().toString());
        ParseComServerAccessor parseComServerAccessor = new ParseComServerAccessor();
        ProfileResponse profileResponse = parseComServerAccessor.updateProfile(account, mAccountManager, profile, token, user_id);
        if (profileResponse != null)
        {
            doSaveProfileToDB(profileResponse.getData());
            doSetupDataToView();
        }
        else
        {
            Toast.makeText(getActivity(), "Input is invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean checkFieldEmpty()
    {
        View[] viewList = {etProfileName, etFullName, etPhoneNo, etBirthday, etCountry, etDescription};
        for (View view : viewList)
        {
            EditText editView = (EditText) view;
            if (editView.getText().toString() == null)
            {
                Toast.makeText(getActivity(), editView.getHint().toString() + " must not empty", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void setCoverImage()
    {
        code = Common.COVER_IMAGE_CODE;
        MyDialog.showSetImageDialog(getActivity(), getString(R.string.dialog_tittle_coverimage));
    }

    private void setAvatar()
    {
        code = Common.AVATAR_CODE;
        MyDialog.showSetImageDialog(getActivity(), getString(R.string.dialog_tittle_avatar));
    }

    private void setGender(int gender)
    {
        switch (gender)
        {
            case Common.MALE:
                ProfileFragment.gender = 2;
                btMale.setBackgroundResource(R.drawable.profile_male);
                btFemale.setBackgroundResource(R.drawable.profile_female_visible);
                break;
            case Common.FEMALE:
                ProfileFragment.gender = 1;
                btFemale.setBackgroundResource(R.drawable.profile_female);
                btMale.setBackgroundResource(R.drawable.profile_male_visible);
                break;
        }
    }

    private void setCountry()
    {
        spCountry.performClick();
        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String country = adapterView.getSelectedItem().toString();
                showCountry(country);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        });
    }

    private String convertToCountryName(String code)
    {
        String[] countryName = getResources().getStringArray(R.array.countries);
        String[] countryCode = getResources().getStringArray(R.array.countries_code);
        List<String> listCode = new ArrayList<String>(Arrays.asList(countryCode));
        return countryName[listCode.indexOf(code.toUpperCase())];
    }

    private String convertToCountryCode(String name)
    {
        String[] countryName = getResources().getStringArray(R.array.countries);
        String[] countryCode = getResources().getStringArray(R.array.countries_code);
        List<String> listName = new ArrayList<String>(Arrays.asList(countryName));
        return countryCode[listName.indexOf(name)];
    }

    private void showCountry(String address)
    {
        etCountry.setText(address);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i(TAG, "onActivityResult");
        if (resultCode == Activity.RESULT_OK && null != data)
        {
            if (requestCode == Common.REQUEST_CODE_CAMERA_TAKE_PICTURE)
            {
                setImageFromCamera(data);
            }
            if (requestCode == Common.REQUEST_CODE_RESULT_LOAD_IMAGE)
            {
                setImageFromAlbum(data);
            }
        }
    }

    private void setImageFromAlbum(Intent data)
    {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        Bitmap photo = BitmapFactory.decodeFile(picturePath);
        switch (code)
        {
            case Common.AVATAR_CODE:
                makeMaskImage(ivAvatar, photo);
                break;
            case Common.COVER_IMAGE_CODE:
                Drawable cover = new BitmapDrawable(photo);
                ivCoverImage.setBackgroundDrawable(cover);
                break;
        }
    }

    private void setImageFromCamera(Intent data)
    {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        switch (code)
        {
            case Common.AVATAR_CODE:
                makeMaskImage(ivAvatar, photo);
                break;
            case Common.COVER_IMAGE_CODE:
                Drawable cover = new BitmapDrawable(photo);
                ivCoverImage.setBackgroundDrawable(cover);
                break;
        }
        Log.d("CameraDemo", "Pic saved");
    }

    public void makeMaskImage(ImageView mImageView, Bitmap photoBitmap)
    {
        Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.profile_mask);

        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);

        Bitmap photoBitmapScale = Bitmap.createScaledBitmap(photoBitmap, mask.getWidth(), mask.getHeight(), false);

        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(photoBitmapScale, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        mImageView.setImageBitmap(result);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mImageView.setBackgroundResource(R.drawable.profile_frame);
    }

    public void getDataToLocalDB()
    {
        token = mAccountManager.peekAuthToken(account, Common.AUTHTOKEN_TYPE_FULL_ACCESS);
        ParseComServerAccessor parseComServerAccessor = new ParseComServerAccessor();
        Profile profile = parseComServerAccessor.getShowsProfile(account, mAccountManager, token, user_id);
        doSaveProfileToDB(profile);
    }

    private void doSaveProfileToDB(Profile profile)
    {
        Cursor c = getActivity().getContentResolver().query(ProfileContract.CONTENT_URI, null
                , null, null, null);
        if (c.moveToFirst())
        {
            Uri updateUri = ProfileContract.CONTENT_URI.buildUpon()
                    .appendPath("1").build();
            profile = checkAvatarAndCover(c, profile);
            getActivity().getContentResolver().update(updateUri, profile.getContentValues(), null, null);
        }
        else
        {
            getActivity().getContentResolver().insert(ProfileContract.CONTENT_URI, profile.getContentValues());
        }
    }

    private Profile checkAvatarAndCover(Cursor c, Profile profile)
    {
        String avatar = c.getString(c.getColumnIndex(ProfileContract.AVATAR));
        String cover = c.getString(c.getColumnIndex(ProfileContract.COVER_IMAGE));
        if (avatar != null && avatar.contains("/" + profile.getAvatar()))
        {
            profile.setAvatar(avatar);
        }
        if (cover != null && cover.contains("/" + profile.getCover_image()))
        {
            profile.setCover_image(cover);
        }
        return profile;
    }
}