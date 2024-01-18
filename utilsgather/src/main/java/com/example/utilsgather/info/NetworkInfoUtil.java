package com.example.utilsgather.info;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkInfoUtil {
    /**
     * 获得当前网络类型
     */
    public static String getNetType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return "unconnected";
        }
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return "wifi";
        } else {
            return "mobile network";
        }
    }
}
