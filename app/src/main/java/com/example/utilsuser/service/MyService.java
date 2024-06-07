package com.example.utilsuser.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.utilsgather.logcat.LogUtil;

public class MyService extends Service {
    private DownloadBinder downloadBinder = new DownloadBinder();

    public MyService() {
        LogUtil.d("MyService构造函数");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("MyService回调 onBind()");
        return downloadBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("MyService回调 onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("MyService回调 onStartCommand()");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.d("MyService回调 onDestroy()");

        super.onDestroy();
    }

    public class DownloadBinder extends Binder {
        void startDownload() {
            LogUtil.d("开始进行下载 startDownload()");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.d("MyService 子线程运行");
                    try {
                        Thread.sleep(2000);
                        LogUtil.d("MyService 子线程结束，干掉自己");
                        stopSelf();

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
        int getProgress() {
            LogUtil.d("getProgress()被调用");
            return 0;
        }
    }
}