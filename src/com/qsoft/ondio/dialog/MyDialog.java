package com.qsoft.ondio.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import com.qsoft.ondio.R;

import java.util.Calendar;

/**
 * User: anhnt
 * Date: 10/14/13
 * Time: 2:55 PM
 */
public class MyDialog extends DialogFragment
{
    private static final int REQUEST_CODE_CAMERA_TAKE_PICTURE = 999;
    private static final int REQUEST_CODE_RESULT_LOAD_IMAGE = 888;

    private static Activity activity;
    private static Dialog dialog;

    private static Calendar c = Calendar.getInstance();
    private static int mYear = c.get(Calendar.YEAR);
    private static int mMonth = c.get(Calendar.MONTH);
    private static int mDay = c.get(Calendar.DAY_OF_MONTH);
    private static EditText etDateToShow;

    public static void showMessageDialog(Activity act, String tittle, String message)
    {
        activity = act;
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(activity);
        alertDialog2.setTitle(tittle);
        alertDialog2.setMessage(message);
        alertDialog2.setPositiveButton("OK", onClickListener);
        alertDialog2.show();
    }

    private static final DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialogInterface, int i)
        {
            // do some thing.
        }
    };

    public static void showSetImageDialog(Activity act, String tittle)
    {
        activity = act;
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_setimage);
        TextView tvTitle = (TextView) dialog.findViewById(android.R.id.title);
        tvTitle.setSingleLine(false);
        tvTitle.setText(tittle);

        Button btTakePicture = (Button) dialog.findViewById(R.id.dialog_btTakePicture);
        Button btChoosePicture = (Button) dialog.findViewById(R.id.dialog_btChoosePicture);
        Button btCancel = (Button) dialog.findViewById(R.id.dialog_btCancel);

        btTakePicture.setOnClickListener(onViewClickListener);
        btChoosePicture.setOnClickListener(onViewClickListener);
        btCancel.setOnClickListener(onViewClickListener);

        dialog.show();
    }

    private final static View.OnClickListener onViewClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.dialog_btTakePicture:
                    takePicture(activity);
                    dialog.dismiss();
                    break;
                case R.id.dialog_btChoosePicture:
                    loadPicture(activity);
                    dialog.dismiss();
                    break;
                case R.id.dialog_btCancel:
                    dialog.dismiss();
                    break;
            }
        }
    };

    private static void loadPicture(Activity activity)
    {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, REQUEST_CODE_RESULT_LOAD_IMAGE);
    }

    private static void takePicture(Activity activity)
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA_TAKE_PICTURE);
    }

    public static void showDatePickerDialog(Activity activity, EditText etDate)
    {
        etDateToShow = etDate;
        dialog = new DatePickerDialog(activity, mDateSetListener, mYear, mMonth, mDay);
        dialog.show();
    }

    private static DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            showDate(etDateToShow, year, monthOfYear, dayOfMonth);
        }
    };

    private static void showDate(EditText etDateToShow, int year, int monthOfYear, int dayOfMonth)
    {
        etDateToShow.setText(dayOfMonth + "/" + ++monthOfYear + "/" + year);
    }
}
