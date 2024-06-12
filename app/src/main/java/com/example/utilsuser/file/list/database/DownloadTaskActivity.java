package com.example.utilsuser.file.list.database;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;
import com.example.utilsuser.file.list.database.network.CreateFileNetwork;

import java.io.File;
import java.util.List;

//todo 自行封装RxJava
//todo 这个Activity的线程切换是不是可以使用Handler来进行优化
//todo 优化Database，是不是可以让SQLiteDatabase对象变成单例模式
//todo Activity去notify Adapter的时候，总是要找id所在的index，有没有办法节约这个步骤呢
//todo state可以优化，不用总是新建对象
//todo 多线程下载
//todo 下载时控制缓存区大小，以此控制下载速度
public class DownloadTaskActivity extends AppCompatActivity {
    RecyclerView rv;
    public DownloaTaskAdapter downloaTaskAdapter;
    public List<DownloadTaskBean> downloadTaskBeans;
    public ChangeReceiver changeReceiver;
    private BackgroundDownloadService.DownloadBinder downloadBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_task);
        loadData();
        initView();
        registerBroadCast();
        bindService();

    }

    private void initView() {
        rv = findViewById(R.id.rv_download_task_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initAdapter() {
        downloaTaskAdapter = new DownloaTaskAdapter(downloadTaskBeans);
        downloaTaskAdapter.setRecyclerItemClickListener(new DownloaTaskAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position) {

            }

            @Override
            public void onTaskToPause(int id) {
                pauseTask(id);
            }

            @Override
            public void onTaskToResume(DownloadTaskBean downloadTaskBean) {
                resumeTask(downloadTaskBean);
            }

            @Override
            public void onTaskToClear(DownloadTaskBean downloadTaskBean, boolean inExecutor) {
                clearTask(downloadTaskBean, inExecutor);
            }
        });

        rv.setAdapter(downloaTaskAdapter);

    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadTaskBeans = DownloadTaskDao.newInstance().queryTaskList();
                for (DownloadTaskBean downloadTaskBean : downloadTaskBeans) {
                    File file = new File(downloadTaskBean.getPath());
                    if (file.exists()) {
                        downloadTaskBean.setCurrentLength(file.length());
                    } else {
                        downloadTaskBean.setCurrentLength(-1);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initAdapter();
                    }
                });
            }
        }).start();
    }

    private void registerBroadCast(){
        changeReceiver = new ChangeReceiver(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(changeReceiver, ChangeReceiver.getAllActionIntentFilter(), Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(changeReceiver, ChangeReceiver.getAllActionIntentFilter());
        }
    }

    private void bindService() {
        Intent intent = new Intent(this, BackgroundDownloadService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
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

    public void downloadJiZhang(View view) {
        addTaskToStart("https://ucdl.25pp.com/fs08/2024/01/08/11/110_54c27c8bd85e0b3f44e7d5491b5aa6a0.apk?nrd=0&fname=%E8%AE%B0%E8%B4%A6%E6%9C%AC&productid=2011&packageid=401462537&pkg=com.bookkp.accountzy&vcode=8&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=8324843&apprd=8324843&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F01%2F08%2F9%2F110_d9d998c600f8c061a079c4f73fbc1016_con.png&did=5587ef1782b1f7f31a42ced9a659c49f&md5=78631c8544971195b950d14771843514",
                "/data/user/0/com.example.utilsuser/files/",
                "110_54c27c8bd85e0b3f44e7d5491b5aa6a0.apk",
                "记账本");
    }
    public void downloadDouYin(View view) {
        addTaskToStart("https://ucdl.25pp.com/fs08/2024/05/28/8/120_1520704cba97e878685c716c5dd89961.apk?nrd=0&fname=%E6%8A%96%E9%9F%B3&productid=2011&packageid=500960169&pkg=com.ss.android.ugc.aweme&vcode=300101&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=7461948&apprd=7461948&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F05%2F29%2F0%2F110_948e6c5440768f9a40ddaf6036045056_con.png&did=6499240c97db2fab01ff289f059dbb92&md5=17dded2bed52cb677eb31ab8f3df33b6",
                "/data/user/0/com.example.utilsuser/files/",
                "120_1520704cba97e878685c716c5dd89961.apk",
                "抖音");
    }
    public void downloadWeiXin(View view) {
    }
    public void downloadXiaoHongShu(View view) {
    }
    public void downloadPiPiXia(View view) {

    }

    private void addTaskToStart(String url, String fileDir, String name, String showName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DownloadTaskBean downloadTaskBean = new DownloadTaskBean();
                downloadTaskBean.setShowName(showName);
                new CreateFileNetwork(url,
                        fileDir,
                        name,
                        downloadTaskBean)
                        .start();

                LogUtil.d("数据库中的信息: " + downloadTaskBean);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyAdd(downloadTaskBean);

                        downloadBinder.addTask(downloadTaskBean);
                    }
                });

            }
        }).start();
    }

    public void notifyUpdateProgress(int id, long currentLength) {
        downloaTaskAdapter.notifyUpdateProgress(id, currentLength);
    }

    public void notifyAdd(DownloadTaskBean downloadTaskBean) {
        downloaTaskAdapter.notifyAdd(downloadTaskBean);
    }
    public void notifyPause(int id) {
        downloaTaskAdapter.notifyPause(id);
    }
    public void notifyFinished(int id) {
        downloaTaskAdapter.notifyFinished(id);
    }
    public void notifyCleared(int id) {
        downloaTaskAdapter.notifyCleared(id);
    }

    public void pauseTask(int id) {
        downloadBinder.pauseTask(id);
    }
    public void resumeTask(DownloadTaskBean downloadTaskBean) {
        downloadBinder.resumeTask(downloadTaskBean);
    }
    public void clearTask(DownloadTaskBean downloadTaskBean, boolean inExecutor) {
        if (inExecutor) {
            downloadBinder.clearTask(downloadTaskBean.getId());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                DownloadTaskDao.newInstance().deleteTask(downloadTaskBean.getId());
                FileOperation.INSTANCE.deleteFile(downloadTaskBean.getPath());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyCleared(downloadTaskBean.getId());
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (changeReceiver != null)
            unregisterReceiver(changeReceiver);

        unbindService(connection);
        downloadBinder = null;
    }
}