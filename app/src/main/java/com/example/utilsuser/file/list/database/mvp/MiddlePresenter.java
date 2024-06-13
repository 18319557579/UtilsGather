package com.example.utilsuser.file.list.database.mvp;

import android.annotation.SuppressLint;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.file.list.database.BackgroundDownloadService;
import com.example.utilsuser.file.list.database.DownloadTaskBean;
import com.example.utilsuser.file.list.database.network.CreateFileNetwork;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class MiddlePresenter implements Contract.Presenter{
    private final Contract.View view;
    private final Contract.Model model;

    public MiddlePresenter(Contract.View view) {
        this.view = view;
        model = new ModelImpl();
    }

    @Override
    public void addTask_Mid(String url, String fileDir, String name, String showName) {
        Observable.fromCallable(() -> {
                    DownloadTaskBean downloadTaskBean = new DownloadTaskBean();
                    downloadTaskBean.setShowName(showName);
                    downloadTaskBean = model.addTask(url, fileDir, name, downloadTaskBean);
                    LogUtil.d("数据库中的信息: " + downloadTaskBean);
                    return downloadTaskBean;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(downloadTaskBean -> {
                    view.notifyAddTask(downloadTaskBean);
                });
    }

    @Override
    public void addTaskToExecutor_Mid(DownloadTaskBean downloadTaskBean, BackgroundDownloadService.DownloadBinder downloadBinder) {
        model.addTaskToExecutor(downloadTaskBean, downloadBinder);
    }


}
