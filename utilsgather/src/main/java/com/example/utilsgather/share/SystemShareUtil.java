package com.example.utilsgather.share;

import android.app.Activity;
import android.content.Intent;

public class SystemShareUtil {
    /**
     * 系统文本分享. 可以带标题
     */
    public static void textShare(Activity activity, String shareContent, String title) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");  //效果未知
        if (title != null) {
            intent.putExtra(Intent.EXTRA_TITLE, title);  //可以看到标题
        }
        intent.setType("text/plain");
        activity.startActivity(Intent.createChooser(intent, "分享"));  //效果未知, "分享"这两个字在findx5pro上也出不来
    }

    /**
     * 系统文本分享,不带标题
     */
    public static void textShare(Activity activity, String shareContent) {
        textShare(activity, shareContent, null);
    }
}
