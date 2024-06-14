package com.example.utilsuser.file.list.database.mvp;

import static android.content.Context.BIND_AUTO_CREATE;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;

import com.example.utilsgather.file_system.FileOperationUtil;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.file.list.database.BackgroundDownloadService;
import com.example.utilsuser.file.list.database.ChangeReceiver;
import com.example.utilsuser.file.list.database.DownloadTaskActivity;
import com.example.utilsuser.file.list.database.DownloadTaskBean;
import com.example.utilsuser.file.list.database.DownloadTaskDao;
import com.example.utilsuser.file.list.database.network.CreateFileNetwork;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class MiddlePresenter implements Contract.Presenter{
    private final Contract.View view;

    private BackgroundDownloadService.DownloadBinder downloadBinder;
    private ChangeReceiver changeReceiver;

    public MiddlePresenter(Contract.View view) {
        this.view = view;

        DownloadTaskActivity activity = ((DownloadTaskActivity)view);
        changeReceiver = new ChangeReceiver(new Contract.ReceiverCallback() {
            @Override
            public void onNotifyUpdateProgress(int id, long currentLength) {
                view.onNotifyUpdateProgress(id, currentLength);
            }

            @Override
            public void onNotifyPause(int id) {
                view.onNotifyPause(id);
            }

            @Override
            public void onNotifyFinished(int id) {
                view.onNotifyFinished(id);
            }

            @Override
            public void onNotifyError(int id, int errorCode) {
                view.onNotifyError(id, errorCode);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.registerReceiver(changeReceiver, changeReceiver.getAllActionIntentFilter(), Context.RECEIVER_NOT_EXPORTED);
        } else {
            activity.registerReceiver(changeReceiver, changeReceiver.getAllActionIntentFilter());
        }


        Intent intent = new Intent(activity, BackgroundDownloadService.class);
        activity.bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d("MyServiceActivity中回调 onServiceConnected()");
            downloadBinder = (BackgroundDownloadService.DownloadBinder) service;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d("MyServiceActivity中回调 onServiceDisconnected() " + name);
        }
    };

    @Override
    public void addTask(String url, String fileDir, String name, String showName) {
        Observable.fromCallable(() -> {
                    DownloadTaskBean downloadTaskBean = new DownloadTaskBean();
                    downloadTaskBean.setShowName(showName);
                    new CreateFileNetwork(url,
                            fileDir,
                            name,
                            downloadTaskBean)
                            .start();
                    LogUtil.d("数据库中的信息: " + downloadTaskBean);
                    return downloadTaskBean;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(downloadTaskBean -> {
                    view.notifyAddTask(downloadTaskBean);
                    downloadBinder.addTask(downloadTaskBean);
                });
    }

    @Override
    public void loadData() {
        Observable.fromCallable(() -> {
                    List<DownloadTaskBean> downloadTaskBeans = DownloadTaskDao.newInstance().queryTaskList();
                    for (DownloadTaskBean downloadTaskBean : downloadTaskBeans) {
                        File file = new File(downloadTaskBean.getPath());
                        if (file.exists()) {
                            downloadTaskBean.setCurrentLength(file.length());
                        } else {
                            downloadTaskBean.setCurrentLength(-1);
                        }
                    }
                    return downloadTaskBeans;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(downloadTaskBeans -> {
                    view.notifyDBTaskList(downloadTaskBeans);
                });

    }

    @Override
    public void resumeTask(DownloadTaskBean downloadTaskBean) {
        //这里其实可以无需传递值，只是为了使用onNext()
        Observable.create(observableEmitter -> {
                    if (!new File(downloadTaskBean.getPath()).exists()) {
                        DownloadTaskDao.newInstance().updateTask(downloadTaskBean.getId(), -1L);
                        downloadTaskBean.setCurrentLength(-1L);
                    }
                    observableEmitter.onNext(downloadTaskBean);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    downloadBinder.resumeTask(downloadTaskBean);
                });
    }

    @Override
    public void pauseTask(int id) {
        downloadBinder.pauseTask(id);
    }

    @Override
    public void clearTask(DownloadTaskBean downloadTaskBean, boolean inExecutor) {
        //这里的第一步实际上不需要切线程，但是为了好看，还是使用了RxJava包裹
        Completable.fromAction(() -> {
                    if (inExecutor) {
                        downloadBinder.clearTask(downloadTaskBean.getId());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .doOnComplete(() -> {
                    DownloadTaskDao.newInstance().deleteTask(downloadTaskBean.getId());
                    FileOperationUtil.deleteFile(downloadTaskBean.getPath());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.notifyCleared(downloadTaskBean.getId());
                });
    }

    @Override
    public void showInfo(List<DownloadTaskBean> downloadTaskBeans) {
        StringBuilder stringBuilder = new StringBuilder();
        for (DownloadTaskBean downloadTaskBean : downloadTaskBeans) {
            stringBuilder.append(downloadTaskBean).append("\n");
        }
        LogUtil.d("内存中的Bean: \n" + stringBuilder.toString());

        Completable.fromRunnable(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        List<DownloadTaskBean> list = DownloadTaskDao.newInstance().queryTaskList();
                        for (DownloadTaskBean downloadTaskBean : list) {
                            stringBuilder2.append(downloadTaskBean).append("\n");
                        }
                        LogUtil.d("数据库中的Bean: \n" + stringBuilder2.toString());
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void destroy() {
        downloadBinder.clearAllTask();

        if (changeReceiver != null) {
            ((DownloadTaskActivity)view).unregisterReceiver(changeReceiver);
            changeReceiver = null;
        }

        ((DownloadTaskActivity)view).unbindService(connection);
        downloadBinder = null;
    }


}
