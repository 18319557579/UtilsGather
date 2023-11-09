package com.example.utilsgather.version;

import android.os.Build;

public class VersionCodeUtil {
    /**
     * 判断当前设备的系统是否达到了（大于或等于）getVersion
     */
    public static boolean versionReached(int targetVersion) {
        return Build.VERSION.SDK_INT >= targetVersion;
    }
}
