package com.example.utilsgather.info;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;
import java.util.Locale;

public class DeviceInfoUtil {

    /**
     * 获得AndroidId
     */
    public static String getAndroidId(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 手机系统品牌。例如OPPO，VIVO
     */
    public static String getOsType() {
        return Build.BRAND;
    }

    /**
     * 获得Android系统版本
     */
    public static String getAndroidOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获得鸿蒙系统版本
     */
    public static String getHarmonyOsVersion() {
        if (! isHarmonyOs()) {
            return "Is not Harmony os!";
        }

        try {
            Class systemPropertiesClazz = Class.forName("android.os.SystemProperties");
            Method getMethod = systemPropertiesClazz.getDeclaredMethod("get", String.class);
            String version = (String) getMethod.invoke(systemPropertiesClazz, "hw_sc.build.platform.version");
            return version;
        } catch (Exception e) {
           e.printStackTrace();
           return null;
        }
    }

    /**
     * 判断是否为鸿蒙系统
     */
    public static boolean isHarmonyOs() {
        try {
            Class buildExClazz = Class.forName("com.huawei.system.BuildEx");
            Object osBrand = buildExClazz.getMethod("getOsBrand").invoke(buildExClazz);
            return "Harmony".equalsIgnoreCase(osBrand.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取当前手机系统语言
     */
    public static String getDeviceLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获得国家代码
     */
    public static String getCountryCode() {
        return Locale.getDefault().getCountry();
    }

    /**
     * 获得sim卡所在国家
     */
    public static String getSimCountry(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimCountryIso();
    }


}
