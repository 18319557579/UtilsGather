package com.example.utilsgather.application_device_info;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

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

    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkOK(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断网络是否可用
     * 从 Android 10（API 级别 29）开始，getActiveNetworkInfo() 方法已被弃用，推荐使用 ConnectivityManager.NetworkCallback 来监听网络变化。
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }
        return false;
    }

    /**
     * 判断网络是否可用
     * 因为 getActiveNetwork() 和 getNetworkCapabilities() 是从 API 级别 21（Android 5.0）引入的，而 NetworkCallback 是从 API 级别 23（Android 6.0）引入的。
     */
    public static boolean isNetworkConnectedCompat(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Android 6.0 (API 23) 及以上使用 NetworkCallback
                Network network = connectivityManager.getActiveNetwork();
                if (network != null) {
                    NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                    return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
                }
            } else {
                // Android 6.0 以下使用旧的方式
                android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
        }
        return false;
    }

}
