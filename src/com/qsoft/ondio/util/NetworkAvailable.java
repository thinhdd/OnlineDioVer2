package com.qsoft.ondio.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author quynhlt
 */
public class NetworkAvailable
{
    private ConnectivityManager connectivityMng;
    private boolean connected = false;

    /**
     * @param context context
     */
    public NetworkAvailable(Context context)
    {
        try
        {
            connectivityMng = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityMng.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            connected = false;
        }
    }

    /**
     * @return if netword is avaiable.
     */
    public boolean isEnabled()
    {
        return connected;
    }
}
