package com.example.utilsgather.manifest;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ManifestUtil {
    public static String getString(Context context, String key) {
        try {
            Bundle metaData = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
            String value = metaData.getString(key);
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getBoolean(Context context, String key) {
        try {
            Bundle metaData = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
            boolean value = metaData.getBoolean(key);
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得 AMf.xml 中<application>中<meta-data>的数据
     * @param context
     * @param key 对应 android:name="key" 中的 key
     * @param type 数据的类型，例如 String.class
     * @return 对应 android:value="value" 中的 value
     */
    public static <T> T getMetaDataValue(Context context, String key, Class<T> type) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = ai.metaData;
            if (metaData == null) {
                Log.w("ConfigManager", "metaData is null. Did you forget to set meta-data in your AndroidManifest.xml?");
                return null;
            }

            // 如果这里还有其他类型要获取的话，请继续手动添加情况
            if (type == String.class) {
                return type.cast(metaData.getString(key));
            } else if (type == Boolean.class) {
                return type.cast(metaData.getBoolean(key));
            } else if (type == Integer.class) {
                return type.cast(metaData.getInt(key));
            } else {
                throw new IllegalArgumentException("Unsupported type " + type.toString());
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("ConfigManager", "Failed to load meta-data, NameNotFound: " + e.getMessage());
        }
        return null;
    }
}
