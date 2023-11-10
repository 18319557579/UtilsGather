package com.example.utilsgather.ui;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

public class ScreenParamsUtils {
    /**
     * 设置屏幕常量
     */
    public static void setScreenOn(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
