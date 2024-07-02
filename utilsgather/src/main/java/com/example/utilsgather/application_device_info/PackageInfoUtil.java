package com.example.utilsgather.application_device_info;

import android.content.Context;
import android.content.pm.ApplicationInfo;
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
     * 获得当前应用的包名，简单的方式
     */
    public static String getPackageNameSimple(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return context.getPackageName(); // 获取当前应用的包名
    }

    /**
     * 获取应用程序名称。
     */
    public static String getAppName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName(); // 获取当前应用的包名
        String appName = "";
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            appName = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }

    /**
     * 获得版本号
     */
    public static long getPackageVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            long versionCode;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                versionCode = info.getLongVersionCode();  // 使用新API
            } else {
                versionCode = info.versionCode;  // 使用旧API，并转换为Long
            }
            return versionCode;
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
