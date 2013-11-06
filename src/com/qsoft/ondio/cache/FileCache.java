package com.qsoft.ondio.cache;

import android.content.Context;

import java.io.File;

public class FileCache
{
    private File fileCacheDir;

    //create directory store cache image from server
    public FileCache(Context context)
    {
        if (android.os.Environment.getExternalStorageDirectory().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            fileCacheDir = new File(android.os.Environment.getDownloadCacheDirectory(), "imageList");
        }
        else
        {
            fileCacheDir = context.getCacheDir();
        }
        if (!fileCacheDir.exists())
        {
            fileCacheDir.mkdirs();
        }
    }

    //get image from url to cache
    public File getImageToCache(String url)
    {
        //Identify image by hash code
        String filename = String.valueOf(url.hashCode());
        //create file has name filename on the cache
        File file = new File(fileCacheDir, filename);
        return file;
    }

    public void clearCache()
    {
        File[] files = fileCacheDir.listFiles();
        if (files == null)
        {
            return;
        }
        for (File f : files)
        {
            f.delete();
        }
    }
}
