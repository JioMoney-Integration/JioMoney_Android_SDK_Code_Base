package com.ril.jm_sdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by RIL
 * Check connection
 */
public class ConnectionUtility {

    private static final String TAG = ConnectionUtility.class.getSimpleName();


    /**
     * To check the connection is available
     * @param context android context
     * @return returns true when connection is available
     */
    public static Boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(null != info && info.isConnected()){
//            Toast.makeText(context, "Please check your network connection !!!", Toast.LENGTH_SHORT).show();
            return true;
        }
        Log.e(TAG, "Please check you internet connetion !!!");
        return false;
    }




}
