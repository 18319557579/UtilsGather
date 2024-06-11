package com.example.utilsuser.file.list.database;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.SparseArray;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.file.list.database.network.DownloadTaskManager;
import com.example.utilsuser.service.MyService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundDownloadService extends Service {
    public static ExecutorService executor = Executors.newCachedThreadPool();
    private DownloadBinder downloadBinder = new DownloadBinder();
    private SparseArray<DownloadTaskManager> taskManagerArray = new SparseArray<>();

    public BackgroundDownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("BackgroundDownloadService回调 onBind()");
        return downloadBinder;
    }

    class DownloadBinder extends Binder {
        public void addTask(DownloadTaskBean downloadTaskBean) {
            DownloadTaskManager.DownloadListener downloadListener = new DownloadTaskManager.DownloadListener() {
                @Override
                public void downloading(long now) {
                    Intent intent = new Intent(ChangeReceiver.ACTION_UPDATE);
                    intent.putExtra(ChangeReceiver.EXTRA_ID, downloadTaskBean.getId());
                    intent.putExtra(ChangeReceiver.EXTRA_CURRENT_LENGTH, now);
                    sendBroadcast(intent);
                }

                @Override
                public void onSuccess(int whatCase) {
                    LogUtil.d("下载完成了：" + whatCase);
                }

                @Override
                public void onFail(String failDesc) {

                }

                @Override
                public void onPause() {
                    LogUtil.d(downloadTaskBean.getShowName() + "已暂停");

                    Intent intent = new Intent(ChangeReceiver.ACTION_PAUSED);
                    intent.putExtra(ChangeReceiver.EXTRA_ID, downloadTaskBean.getId());
                    sendBroadcast(intent);
                }
            };

            DownloadTaskManager downloadTaskManager = new DownloadTaskManager(downloadTaskBean, downloadListener);
            taskManagerArray.put(downloadTaskBean.getId(), downloadTaskManager);

            executor.execute(downloadTaskManager);
        }

        public void pauseTask(int id) {
            DownloadTaskManager downloadTaskManager = taskManagerArray.get(id);
            downloadTaskManager.pause();
        }

        public void resumeTask(DownloadTaskBean downloadTaskBean) {
            DownloadTaskManager downloadTaskManager = taskManagerArray.get(downloadTaskBean.getId());

            if (downloadTaskManager == null) {
                addTask(downloadTaskBean);
                LogUtil.d("taskManagerArray中不存在，新增");
            } else {
                downloadTaskManager.resume();
                executor.execute(downloadTaskManager);
                LogUtil.d("taskManagerArray中存在，复用");
            }
        }
    }
}