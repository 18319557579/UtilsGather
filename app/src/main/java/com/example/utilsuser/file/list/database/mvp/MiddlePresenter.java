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
import com.example.utilsuser.file.list.database.bean.BeanPackaged;
import com.example.utilsuser.file.list.database.bean.state.BaseState;
import com.example.utilsuser.file.list.database.four_components.BackgroundDownloadService;
import com.example.utilsuser.file.list.database.four_components.ChangeReceiver;
import com.example.utilsuser.file.list.database.ui.DownloadTaskActivity;
import com.example.utilsuser.file.list.database.bean.DownloadTaskBean;
import com.example.utilsuser.file.list.database.db.DownloadTaskDao;
import com.example.utilsuser.file.list.database.network.CreateFileNetwork;

import java.io.File;
import java.util.ArrayList;
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
            LogUtil.d("打印这个Binder: " + downloadBinder);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d("MyServiceActivity中回调 onServiceDisconnected() " + name);
        }
    };

    @Override
    public void addTask(String url, String fileDir, String name, String showName) {
        Observable.fromCallable(() -> {
                    BeanPackaged beanPackaged = new BeanPackaged();
                    beanPackaged.baseState = BaseState.PAUSED_STATE();

                    DownloadTaskBean downloadTaskBean = new DownloadTaskBean();
                    downloadTaskBean.setShowName(showName);
                    new CreateFileNetwork(url,
                            fileDir,
                            name,
                            downloadTaskBean)
                            .start();
                    LogUtil.d("数据库中的信息: " + downloadTaskBean);
                    beanPackaged.downloadTaskBean = downloadTaskBean;

                    return beanPackaged;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beanPackaged -> {
                    view.notifyAddTask(beanPackaged);
                    downloadBinder.addTask(beanPackaged.downloadTaskBean);
                });
    }

    @Override
    public void loadData() {
        Observable.fromCallable(() -> {
                    List<DownloadTaskBean> downloadTaskBeans = DownloadTaskDao.newInstance().queryTaskList();

                    List<BeanPackaged> beanPackagedList = new ArrayList<>();
                    for (DownloadTaskBean downloadTaskBean : downloadTaskBeans) {
                        BeanPackaged beanPackaged = new BeanPackaged();
                        beanPackaged.downloadTaskBean = downloadTaskBean;

                        File file = new File(downloadTaskBean.getPath());
                        if (! file.exists()) {
                            downloadTaskBean.setCurrentLength(-1);
                            beanPackaged.baseState = BaseState.BROKEN_STATE();

                        } else {
                            downloadTaskBean.setCurrentLength(file.length());  //这里进行了一个长度校正
                            beanPackaged.baseState = downloadTaskBean.getCurrentLength() == downloadTaskBean.getTotalLength() ?
                                    BaseState.FINISHED_STATE() : BaseState.PAUSED_STATE();
                        }
                        beanPackagedList.add(beanPackaged);
                    }
                    return beanPackagedList;
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
    public void showInfo(List<BeanPackaged> downloadTaskBeans) {
        StringBuilder stringBuilder = new StringBuilder();
        for (BeanPackaged beanPackaged : downloadTaskBeans) {
            stringBuilder.append(beanPackaged.toString()).append("\n");
        }
        LogUtil.d("内存中的Bean: \n" + stringBuilder.toString());

        Completable.fromRunnable(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        List<DownloadTaskBean> list = DownloadTaskDao.newInstance().queryTaskList();
                        for (DownloadTaskBean downloadTaskBean : list) {
                            stringBuilder2.append(downloadTaskBean.toString()).append("\n");
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
        //补充增加一个stopService()，可能这样可以更好的关闭Service
        ((DownloadTaskActivity)view).stopService(new Intent(((DownloadTaskActivity)view), BackgroundDownloadService.class));
        downloadBinder = null;
    }


}
