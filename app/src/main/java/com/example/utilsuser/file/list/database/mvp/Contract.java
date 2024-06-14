package com.example.utilsuser.file.list.database.mvp;

import com.example.utilsuser.file.list.database.bean.BeanPackaged;
import com.example.utilsuser.file.list.database.bean.DownloadTaskBean;

import java.util.List;

public interface Contract {
    interface View {
        void notifyDBTaskList(List<BeanPackaged> downloadTaskBeans);
        void notifyAddTask(BeanPackaged beanPackaged);
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

        void showInfo(List<BeanPackaged> downloadTaskBeans);

        void destroy();
    }

    interface ReceiverCallback {
        void onNotifyUpdateProgress(int id, long currentLength);
        void onNotifyPause(int id);
        void onNotifyFinished(int id);
        void onNotifyError(int id, int errorCode);
    }
}