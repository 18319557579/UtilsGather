package com.example.utilsuser.file.list.database.mvp;

import com.example.utilsuser.file.list.database.BackgroundDownloadService;
import com.example.utilsuser.file.list.database.DownloadTaskBean;

public interface Contract {
    interface Model {
        DownloadTaskBean addTask(String url, String fileDir, String name, DownloadTaskBean downloadTaskBean);
        void addTaskToExecutor(DownloadTaskBean downloadTaskBean, BackgroundDownloadService.DownloadBinder downloadBinder);
    }

    interface View {
        void notifyAddTask(DownloadTaskBean downloadTaskBean);
    }

    interface Presenter {
        void addTask_Mid(String url, String fileDir, String name, String showName);
        void addTaskToExecutor_Mid(DownloadTaskBean downloadTaskBean, BackgroundDownloadService.DownloadBinder downloadBinder);
    }
}
