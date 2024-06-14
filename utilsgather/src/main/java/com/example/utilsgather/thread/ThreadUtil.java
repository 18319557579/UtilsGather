package com.example.utilsgather.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    //在子线程上运行
    public static void runOnBGThread(Runnable task) {
        executorService.submit(task);
    }
    //在主线程上运行
    public static void runOnUiThread(Runnable task) {
        mainHandler.post(task);
    }

    //获得当前线程
    public static Thread getCurrentThread() {
        return Thread.currentThread();
    }


}
