package com.example.utilsuser.file.list.database;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilsgather.file_system.FileOperationUtil;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;
import com.example.utilsuser.file.list.database.mvp.Contract;
import com.example.utilsuser.file.list.database.mvp.MiddlePresenter;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//todo 退出这个Activity时，要关闭哪些东西 1
//todo 自行封装RxJava 1 -> 变成引入Rxjava了 1
//todo 这个Activity的线程切换是不是可以使用Handler来进行优化
//todo 优化Database，是不是可以让SQLiteDatabase对象变成单例模式
//todo Activity去notify Adapter的时候，总是要找id所在的index，有没有办法节约这个步骤呢
//todo state可以优化，不用总是新建对象 1
//todo 多线程下载
//todo 下载时控制缓存区大小，以此控制下载速度 1
//todo 将涉及到的一些工具方法，例如File文件的操作，写到UtilGather中 1
//todo 错误情况的处理，例如下载中把原文件删除（至少要能实现暂停后继续时，可以进行原文件是否存在的判断） 1 2
//todo 增加下载速度的显示

@SuppressLint("CheckResult")
public class DownloadTaskActivity extends AppCompatActivity implements Contract.View{
    private RecyclerView rv;
    public DownloaTaskAdapter downloaTaskAdapter;
    public List<DownloadTaskBean> downloadTaskBeans;

    private Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_task);
        presenter = new MiddlePresenter(this);
        loadData();
        initView();
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
        presenter.loadData();
    }

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

    public void showInfo(View view) {
        presenter.showInfo(downloadTaskBeans);
    }

    private void addTaskToStart(String url, String fileDir, String name, String showName) {
        presenter.addTask(url, fileDir, name, showName);
    }

    public void notifyAdd(DownloadTaskBean downloadTaskBean) {
        downloaTaskAdapter.notifyAdd(downloadTaskBean);
    }

    public void notifyCleared(int id) {
        downloaTaskAdapter.notifyCleared(id);
    }

    public void pauseTask(int id) {
        presenter.pauseTask(id);
    }

    public void resumeTask(DownloadTaskBean downloadTaskBean) {
        presenter.resumeTask(downloadTaskBean);
    }

    public void clearTask(DownloadTaskBean downloadTaskBean, boolean inExecutor) {
        presenter.clearTask(downloadTaskBean, inExecutor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.destroy();
        presenter = null;
    }

    @Override
    public void notifyDBTaskList(List<DownloadTaskBean> downloadTaskBeans) {
        this.downloadTaskBeans = downloadTaskBeans;
        initAdapter();
    }

    @Override
    public void notifyAddTask(DownloadTaskBean downloadTaskBean) {
        notifyAdd(downloadTaskBean);
//        downloadBinder.addTask(downloadTaskBean);
//        presenter.addTaskToExecutor_Mid(downloadTaskBean, downloadBinder);
    }

    @Override
    public void onNotifyUpdateProgress(int id, long currentLength) {
        downloaTaskAdapter.notifyUpdateProgress(id, currentLength);
    }

    @Override
    public void onNotifyPause(int id) {
        downloaTaskAdapter.notifyPause(id);
    }

    @Override
    public void onNotifyFinished(int id) {
        downloaTaskAdapter.notifyFinished(id);
    }

    @Override
    public void onNotifyError(int id, int errorCode) {
        switch (errorCode) {
            case 0:
                break;
            case 1:
                downloaTaskAdapter.notifyPause(id);
                break;
            case 2:
                downloaTaskAdapter.notifyBroken(id);
                break;
        }
    }

}