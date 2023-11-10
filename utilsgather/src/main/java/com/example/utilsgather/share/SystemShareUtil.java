package com.example.utilsgather.share;

import android.app.Activity;
import android.content.Intent;

public class SystemShareUtil {
    /**
     * 系统文本分享
     */
    public static void textShare(Activity activity, String shareContent) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        intent.setType("text/plain");
        activity.startActivity(intent);
    }
}
