package com.example.utilsuser.file.list.database.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Scheduler {
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void runOnBGThread(Runnable task) {
        executorService.submit(task);
    }

    public static void runOnUiThread(Runnable task) {
        mainHandler.post(task);
    }

}

