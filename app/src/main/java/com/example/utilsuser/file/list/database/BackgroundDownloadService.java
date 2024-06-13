package com.example.utilsuser.file.list.database;

import android.app.Service;
import android.content.ComponentName;
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
    public ExecutorService executor = Executors.newCachedThreadPool();
    private DownloadBinder downloadBinder = new DownloadBinder();
    private SparseArray<DownloadTaskManager> taskManagerArray = new SparseArray<>();

    public BackgroundDownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("BackgroundDownloadService回调 onBind()");
        return downloadBinder;
    }

    public class DownloadBinder extends Binder {
        //添加任务
        public void addTask(DownloadTaskBean downloadTaskBean) {
            DownloadTaskManager.DownloadListener downloadListener = new DownloadTaskManager.DownloadListener() {
                @Override
                public void downloading(long now) {
                    LogUtil.d("downloading", "下载中：" + now);
                    Intent intent = new Intent(ChangeReceiver.ACTION_UPDATE);
                    intent.setPackage(getPackageName());
                    intent.putExtra(ChangeReceiver.EXTRA_ID, downloadTaskBean.getId());
                    intent.putExtra(ChangeReceiver.EXTRA_CURRENT_LENGTH, now);
                    sendBroadcast(intent);
                }

                @Override
                public void onSuccess(int whatCase) {
                    LogUtil.d("下载完成了：" + whatCase);
                    Intent intent = new Intent(ChangeReceiver.ACTION_FINISHED);
                    intent.setPackage(getPackageName());
                    intent.putExtra(ChangeReceiver.EXTRA_ID, downloadTaskBean.getId());
                    sendBroadcast(intent);
                }

                @Override
                public void onFail(String failDesc, int whatCase) {
                    LogUtil.d(downloadTaskBean.getShowName() + " 已失败, " + failDesc + " : " + whatCase);
                    Intent intent = new Intent(ChangeReceiver.ACTION_ERROR);
                    intent.setPackage(getPackageName());
                    intent.putExtra(ChangeReceiver.EXTRA_ID, downloadTaskBean.getId());
                    intent.putExtra(ChangeReceiver.EXTRA_ERROR_CODE, whatCase);
                    sendBroadcast(intent);
                }

                @Override
                public void onPause() {
                    LogUtil.d(downloadTaskBean.getShowName() + "已暂停");
                    Intent intent = new Intent(ChangeReceiver.ACTION_PAUSED);
                    intent.setPackage(getPackageName());
                    intent.putExtra(ChangeReceiver.EXTRA_ID, downloadTaskBean.getId());
                    sendBroadcast(intent);
                }
            };

            DownloadTaskManager downloadTaskManager = new DownloadTaskManager(downloadTaskBean, downloadListener);
            taskManagerArray.put(downloadTaskBean.getId(), downloadTaskManager);

            executor.execute(downloadTaskManager);
        }

        //暂停任务
        public void pauseTask(int id) {
            DownloadTaskManager downloadTaskManager = taskManagerArray.get(id);
            downloadTaskManager.pause();
        }

        //继续任务（如果任务列表中没有，则新增）
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

        public void clearTask(int id) {
            DownloadTaskManager downloadTaskManager = taskManagerArray.get(id);
            if (downloadTaskManager == null) {  //因为有可能paused状态下，还没有启动任务（刚进该Activity的时候）
                return;
            }
            if (! downloadTaskManager.isPaused()) {
                downloadTaskManager.pause();
            }
            taskManagerArray.remove(id);
        }

        public void clearAllTask() {
            for (int i = 0; i < taskManagerArray.size(); i++) {
                DownloadTaskManager downloadTaskManager = taskManagerArray.valueAt(i);
                if (! downloadTaskManager.isPaused()) {
                    downloadTaskManager.pause();
                }
            }
            taskManagerArray.clear();
            executor.shutdown();
        }
    }
}