package com.example.utilsgather.info;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class PackageInfoUtil {
    /**
     * 获得包名
     */
    public static String getPackageName(Context context) {
        PackageManager manager = context.getPackageManager();

        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得版本号
     */
    public static int getPackageVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获得版本名
     */
    public static String getPackageVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0); //PackageManager.GET_CONFIGURATIONS
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
