package com.example.utilsgather.browser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class BrowserUtil {
    /**
     * 这个貌似只能跳默认的手机浏览器
     */
    public static void jumpBrowser(String jumpUrl, Activity activity) {
        Uri uri = Uri.parse(jumpUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    /**
     * 这个目前跳的是Chrome浏览器
     * todo 后续改造成让用户选择跳哪个浏览器
     */
    public static void jumpOthers(Activity activity, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setPackage("com.android.chrome");
        activity.startActivity(intent);
    }

}
