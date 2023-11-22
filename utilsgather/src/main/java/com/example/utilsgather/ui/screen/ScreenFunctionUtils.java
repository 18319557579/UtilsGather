package com.example.utilsgather.ui.screen;

import android.app.Activity;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenFunctionUtils {
    /**
     * 设置屏幕常量
     */
    public static void setScreenOn(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 隐藏ActionBar
     */
    public static void hideActionBar(AppCompatActivity activity) {
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }
    }
}
