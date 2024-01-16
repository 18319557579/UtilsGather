package com.example.utilsgather.version;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public class VersionCodeUtil {
    /**
     * 判断当前设备的系统是否达到了（大于或等于）getVersion
     */
    public static boolean versionReached(int targetVersion) {
        return Build.VERSION.SDK_INT >= targetVersion;
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
}
