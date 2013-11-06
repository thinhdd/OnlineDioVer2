package com.qsoft.ondio.cache;


import android.graphics.Bitmap;
import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CacheManager
{
    public CacheManager()
    {
        //constructor for heap size
        setLimit(Runtime.getRuntime().maxMemory() / 4);
    }

    public void setLimit(long l)
    {
        this.limit = l;
    }

    //get image from cache has id = id
    public Bitmap get(String id)
    {
        if (!cache.containsKey(id))
        {
            Log.i("CacheManager", "null");
            return null;
        }
        Log.i("CacheManager", "not null");
        return cache.get(id);
    }

    //put image to cache has id=id and value = bitmap
    public void put(String id, Bitmap bitmap)
    {
        try
        {
            //if image has is=is exist, we will subtract byte of this image
            if (cache.containsKey(id))
            {
                size -= getByteofBitmap(cache.get(id));
            }
            //put image to cache
            cache.put(id, bitmap);
            //add byte of this image to size
            size += getByteofBitmap(bitmap);
            //check size of cache
            checkSize();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    //check size of cache, if size>limit we will remove least recently accessed item
    public void checkSize()
    {
        if (size > limit)
        {
            Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator();

            while (iter.hasNext())
            {
                Entry<String, Bitmap> entry = iter.next();
                size -= getByteofBitmap(entry.getValue());
                iter.remove();

                if (size <= limit)
                {
                    break;
                }
            }
        }

    }

    //get byte of bitmap
    public long getByteofBitmap(Bitmap bitmap)
    {
        if (bitmap == null)
        {
            return 0;
        }
        else
        {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }

    //clear cache
    public void clearCache()
    {
        cache.clear();
    }

    private long size = 0;
    private long limit = 1000000;
    private Map<String, Bitmap> cache = Collections.synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));
}
