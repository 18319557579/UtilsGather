package com.example.utilsuser.file.list.database.mvp;

import com.example.utilsuser.file.list.database.BackgroundDownloadService;
import com.example.utilsuser.file.list.database.DownloadTaskBean;

import java.util.List;

public interface Contract {
    interface View {
        void notifyDBTaskList(List<DownloadTaskBean> downloadTaskBeans);
        void notifyAddTask(DownloadTaskBean downloadTaskBean);
        void notifyCleared(int id);

        //BroadcastReceiver回调
        void onNotifyUpdateProgress(int id, long currentLength);
        void onNotifyPause(int id);
        void onNotifyFinished(int id);
        void onNotifyError(int id, int errorCode);
    }

    interface Presenter {
        void addTask(String url, String fileDir, String name, String showName);
        void loadData();
        void resumeTask(DownloadTaskBean downloadTaskBean);
        void pauseTask(int id);
        void clearTask(DownloadTaskBean downloadTaskBean, boolean inExecutor);

        void showInfo(List<DownloadTaskBean> downloadTaskBeans);

        void destroy();
    }

    interface ReceiverCallback {
        void onNotifyUpdateProgress(int id, long currentLength);
        void onNotifyPause(int id);
        void onNotifyFinished(int id);
        void onNotifyError(int id, int errorCode);
    }
}
