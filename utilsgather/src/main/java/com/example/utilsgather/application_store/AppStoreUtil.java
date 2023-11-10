package com.example.utilsgather.application_store;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class AppStoreUtil {
    private static final String GOOGLE_PLAY = "com.android.vending";

    /**
     * 在应用商店中打开某应用
     * @param isGooglePlay 如果为false会在默认的应用商店打开，如果为true会在google play打开
     */
    public static void jumpStore(Activity activity, String pkgName, boolean isGooglePlay) {
        Uri uri = Uri.parse("market://details?id=" + pkgName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (isGooglePlay) {
            intent.setPackage(GOOGLE_PLAY);
        }
        activity.startActivity(intent);
    }
}
