package com.qsoft.ondio.cache;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import com.qsoft.ondio.R;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Image
{
    CacheManager cacheManager = new CacheManager();
    FileCache fileCache;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executor;
    final int stub_id = R.drawable.ic_launcher;

    public Image(Context context)
    {
        this.fileCache = new FileCache(context, "imageList");
        executor = Executors.newFixedThreadPool(5);//create an executor restservice with maximum thread is 5
    }

    public Bitmap DisplayImage(String url, ImageView view)
    {
        imageViews.put(view, url);//put image view with url to the map imageViews

        Bitmap bitmap = cacheManager.get(url);//get bitmap has id is url from cache
        if (bitmap == null)
        {
            QueuePhotos(url, view);
            view.setImageResource(stub_id);
        }
        else
        {
            view.setImageBitmap(bitmap);
        }
        return cacheManager.get(url);
    }

    public void QueuePhotos(String url, ImageView view)
    {
        photos queue = new photos(url, view);
        executor.submit(new photoLoader(queue));
    }

    private class photos
    {
        public String url;
        public ImageView view;

        public photos(String URL, ImageView imgaeview)
        {
            this.url = URL;
            this.view = imgaeview;
        }
    }

    class photoLoader implements Runnable
    {
        photos photo;

        public photoLoader(photos photo)
        {
            this.photo = photo;
        }

        public void run()
        {
            // TODO Auto-generated method stub
            if (imageViewReused(photo))
            {
                return;
            }

            Bitmap bmp = getBitmap(photo.url);
            cacheManager.put(photo.url, bmp);
            if (imageViewReused(photo))
            {
                return;
            }

            BitmapDisplay bd = new BitmapDisplay(bmp, photo);
            Activity activity = (Activity) photo.view.getContext();
            activity.runOnUiThread(bd);
        }
    }

    class BitmapDisplay implements Runnable
    {
        Bitmap bitmap;
        photos photo;

        public BitmapDisplay(Bitmap bmp, photos pho)
        {
            this.bitmap = bmp;
            this.photo = pho;
        }

        public void run()
        {
            // TODO Auto-generated method stub
            if (imageViewReused(photo))
            {
                return;
            }
            if (bitmap != null)
            {
                photo.view.setImageBitmap(bitmap);
            }
            else
            {
                photo.view.setImageResource(stub_id);
            }
        }
    }

    boolean imageViewReused(photos photo)
    {
        String tag = imageViews.get(photo.view);
        if (tag == null || !tag.equals(photo.url))
        {
            return true;
        }
        return false;
    }

    private Bitmap getBitmap(String url)
    {
        File f = fileCache.getImageToCache(url);
        Bitmap bmp = decodeFileToImage(f);
        if (bmp != null)
        {
            return bmp;
        }

        Bitmap bmpUrl = decodeFromWebToBitmap(url, f);
        return bmpUrl;
    }

    private Bitmap decodeFileToImage(File file)
    {
        try
        {
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, op);

            final int REQUIRED_SIZE = 70;
            int width_tmp = op.outWidth;
            int height_tmp = op.outHeight;
            int scale = 1;
            //define scale
            while (true)
            {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                {
                    break;
                }

                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            BitmapFactory.Options op2 = new BitmapFactory.Options();
            op2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, op2);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private Bitmap decodeFromWebToBitmap(String url, File file)
    {
        try
        {
            URL imageUrl = new URL(url);
            Log.i("abc", imageUrl.toString());
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            connection.setInstanceFollowRedirects(true);

            InputStream inStream = connection.getInputStream();
            OutputStream outStream = new FileOutputStream(file);
            Utils.copyStream(inStream, outStream);
            outStream.close();
            Bitmap bmp = decodeFileToImage(file);
            return bmp;
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void clearCache()
    {
        cacheManager.clearCache();
        fileCache.clearCache();
    }
}
