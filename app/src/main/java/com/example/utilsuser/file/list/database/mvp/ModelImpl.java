package com.example.utilsuser.file.list.database.mvp;

import com.example.utilsuser.file.list.database.BackgroundDownloadService;
import com.example.utilsuser.file.list.database.DownloadTaskBean;
import com.example.utilsuser.file.list.database.network.CreateFileNetwork;

public class ModelImpl implements Contract.Model {

    @Override
    public DownloadTaskBean addTask(String url, String fileDir, String name, DownloadTaskBean downloadTaskBean) {
        new CreateFileNetwork(url,
                fileDir,
                name,
                downloadTaskBean)
                .start();
        return downloadTaskBean;
    }

    @Override
    public void addTaskToExecutor(DownloadTaskBean downloadTaskBean, BackgroundDownloadService.DownloadBinder downloadBinder) {
        downloadBinder.addTask(downloadTaskBean);
    }
}
