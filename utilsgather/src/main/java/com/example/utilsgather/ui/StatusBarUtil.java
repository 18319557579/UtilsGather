package com.example.utilsgather.ui;

import android.content.Context;
import android.content.res.Resources;

public class StatusBarUtil {
    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
