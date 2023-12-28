package com.example.utilsgather.manifest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

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
}
