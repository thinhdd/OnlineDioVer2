package com.qsoft.ondio.fragment;

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
import com.qsoft.ondio.R;
import com.qsoft.ondio.activity.SlidebarActivity;
import com.qsoft.ondio.data.ParseComServerAccessor;
import com.qsoft.ondio.data.dao.ProfileContract;
import com.qsoft.ondio.dialog.MyDialog;
import com.qsoft.ondio.model.Profile;

/**
 * User: anhnt
 * Date: 10/16/13
 * Time: 8:56 AM
 */
public class ProfileFragment extends Fragment
{
    private static final String TAG = "ProfileFragment";
    private EditText etProfileName;
    private EditText etFullName;
    private EditText etPhoneNo;
    private EditText etBirthday;
    private Button btMale;
    private Button btFemale;
    private EditText etCountry;
    private Spinner spCountry;
    private EditText etDescription;
    private ImageView ivAvatar;
    private RelativeLayout rlCoverImage;
    private ScrollView scroll;
    private Button btSave;
    private Button btMenu;

    private static final int MALE = 0;
    private static final int FEMALE = 1;
    private static int gender;
    private static final int REQUEST_CODE_CAMERA_TAKE_PICTURE = 999;
    private static final int REQUEST_CODE_RESULT_LOAD_IMAGE = 888;
    private static final int AVATAR_CODE = 0;
    private static final int COVER_IMAGE_CODE = 1;
    private static int code;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.profile, null);
        getDataToLocalDB();
        setUpUI(view);
        setUpListenerController();
        doSetupDataToView();
        return view;
    }

    private void doSetupDataToView()
    {
        Cursor cursor = getActivity().getContentResolver().query(ProfileContract.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst())
        {
            int gender = cursor.getInt(cursor.getColumnIndex(ProfileContract.GENDER));
            if (gender == 0)
            {
                setGender(0);
            }
            else
            {
                setGender(1);
            }
            etProfileName.setText(cursor.getString(cursor.getColumnIndex(ProfileContract.DISPLAY_NAME)));
            etFullName.setText(cursor.getString(cursor.getColumnIndex(ProfileContract.FULL_NAME)));
            etPhoneNo.setText(cursor.getString(cursor.getColumnIndex(ProfileContract.PHONE)));
            etBirthday.setText(cursor.getString(cursor.getColumnIndex(ProfileContract.BIRTHDAY)));
            etCountry.setText(cursor.getString(cursor.getColumnIndex(ProfileContract.COUNTRY_ID)));
            etDescription.setText(cursor.getString(cursor.getColumnIndex(ProfileContract.DESCRIPTION)));
        }
    }

    private void setUpListenerController()
    {
        etBirthday.setOnClickListener(onClickListener);
        etCountry.setOnClickListener(onClickListener);
        btMale.setOnClickListener(onClickListener);
        btFemale.setOnClickListener(onClickListener);
        ivAvatar.setOnClickListener(onClickListener);
        rlCoverImage.setOnClickListener(onClickListener);
        etDescription.setOnClickListener(onClickListener);
        btSave.setOnClickListener(onClickListener);
        btMenu.setOnClickListener(onClickListener);
    }

    private void setUpUI(View view)
    {
        etProfileName = (EditText) view.findViewById(R.id.profile_etProfileName);
        etFullName = (EditText) view.findViewById(R.id.profile_etFullName);
        etPhoneNo = (EditText) view.findViewById(R.id.profile_etPhoneNo);
        etBirthday = (EditText) view.findViewById(R.id.profile_etBirthday);
        btMale = (Button) view.findViewById(R.id.profile_btMale);
        btFemale = (Button) view.findViewById(R.id.profile_btFemale);
        etCountry = (EditText) view.findViewById(R.id.profile_etCountry);
        spCountry = (Spinner) view.findViewById(R.id.profile_spCountry);
        etDescription = (EditText) view.findViewById(R.id.profile_etDescription);
        ivAvatar = (ImageView) view.findViewById(R.id.profile_ivAvatar);
        rlCoverImage = (RelativeLayout) view.findViewById(R.id.profile_rlCoverImage);
        scroll = (ScrollView) view.findViewById(R.id.profile_svScroll);
        btSave = (Button) view.findViewById(R.id.profile_btSave);
        btMenu = (Button) view.findViewById(R.id.profile_btMenu);
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
                    setGender(MALE);
                    break;
                case R.id.profile_btFemale:
                    setGender(FEMALE);
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
        profile.setCountry_id(Integer.parseInt(etCountry.getText().toString()));
        profile.setDescription(etDescription.getText().toString());
    }

    private Boolean checkFieldEmpty()
    {
        View[] viewList = {etProfileName, etFullName, etPhoneNo, etBirthday, etCountry, etDescription};
        for (View view : viewList)
        {
            EditText edit = (EditText) view;
            if (edit.getText().toString() == null)
            {
                Toast.makeText(getActivity(), edit.get) 000
                return false;
            }
        }
        return true;
    }

    private void setCoverImage()
    {
        code = COVER_IMAGE_CODE;
        MyDialog.showSetImageDialog(getActivity(), getString(R.string.dialog_tittle_coverimage));
    }

    private void setAvatar()
    {
        code = AVATAR_CODE;
        MyDialog.showSetImageDialog(getActivity(), getString(R.string.dialog_tittle_avatar));
    }

    private void setGender(int gender)
    {
        switch (gender)
        {
            case MALE:
                ProfileFragment.gender = 1;
                btMale.setBackgroundResource(R.drawable.profile_male);
                btFemale.setBackgroundResource(R.drawable.profile_female_visible);
                break;
            case FEMALE:
                ProfileFragment.gender = 0;
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
                showCountry(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        });
    }

    private void showCountry(String address)
    {
        etCountry.setText(address);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i(TAG, "onActivityResult");
        if (resultCode == getActivity().RESULT_OK && null != data)
        {
            if (requestCode == REQUEST_CODE_CAMERA_TAKE_PICTURE)
            {
                setImageFromCamera(data);
            }
            if (requestCode == REQUEST_CODE_RESULT_LOAD_IMAGE)
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
            case AVATAR_CODE:
                makeMaskImage(ivAvatar, photo);
                break;
            case COVER_IMAGE_CODE:
                Drawable cover = new BitmapDrawable(photo);
                rlCoverImage.setBackgroundDrawable(cover);
                break;
        }
    }

    private void setImageFromCamera(Intent data)
    {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        switch (code)
        {
            case AVATAR_CODE:
                makeMaskImage(ivAvatar, photo);
                break;
            case COVER_IMAGE_CODE:
                Drawable cover = new BitmapDrawable(photo);
                rlCoverImage.setBackgroundDrawable(cover);
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
        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String token = setting.getString("authToken", "n/a");
        String user_id = setting.getString("user_id", "n/a");
        ParseComServerAccessor parseComServerAccessor = new ParseComServerAccessor();
        Profile profile = parseComServerAccessor.getShowsProfile(token, user_id);
        Cursor c = getActivity().getContentResolver().query(ProfileContract.CONTENT_URI, null
                , null, null, null);
        if (c.moveToFirst())
        {
            Uri updateUri = ProfileContract.CONTENT_URI.buildUpon()
                    .appendPath("1").build();
            getActivity().getContentResolver().update(updateUri, profile.getContentValues(), null, null);
        }
        else
        {
            getActivity().getContentResolver().insert(ProfileContract.CONTENT_URI, profile.getContentValues());
        }
    }
}