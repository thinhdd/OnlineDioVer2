package com.qsoft.ondio.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.qsoft.ondio.R;

public class MaskImage extends ImageView
{
    int mImageSource = 0;
    int mMaskSource = 0;
    int mBackgroundSource = 0;
    RuntimeException mException;

    public MaskImage(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MaskImage, 0, 0);
        mImageSource = a.getResourceId(R.styleable.MaskImage_image, 0);
        mMaskSource = a.getResourceId(R.styleable.MaskImage_mask, 0);
        mBackgroundSource = a.getResourceId(R.styleable.MaskImage_frame, 0);

        if (mImageSource == 0 || mMaskSource == 0 || mBackgroundSource == 0)
        {
            mException = new IllegalArgumentException(a.getPositionDescription() +
                    ": The content attribute is required and must refer to a valid image.");
        }

        if (mException != null)
        {
            throw mException;
        }


        Bitmap mask = BitmapFactory.decodeResource(getResources(), mMaskSource);
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);

        Bitmap original_before = BitmapFactory.decodeResource(getResources(), mImageSource);
        Bitmap original = Bitmap.createScaledBitmap(original_before, mask.getWidth(), mask.getHeight(), false);

        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        setImageBitmap(result);
        setScaleType(ScaleType.FIT_CENTER);
        setBackgroundResource(mBackgroundSource);

        a.recycle();
    }
}
