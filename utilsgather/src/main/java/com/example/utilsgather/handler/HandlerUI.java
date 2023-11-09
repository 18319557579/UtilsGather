package com.example.utilsgather.handler;

import android.os.Handler;
import android.os.Looper;

public class HandlerUI {
    /**
     * 将runnable运行到UI线程上
     */
    public static void runOnUI(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
