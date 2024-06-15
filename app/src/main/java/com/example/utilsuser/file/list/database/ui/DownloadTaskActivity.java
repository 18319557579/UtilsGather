package com.example.utilsuser.file.list.database.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;
import com.example.utilsuser.file.list.database.bean.BeanPackaged;
import com.example.utilsuser.file.list.database.bean.DownloadTaskBean;
import com.example.utilsuser.file.list.database.bean.state.BaseState;
import com.example.utilsuser.file.list.database.mvp.Contract;
import com.example.utilsuser.file.list.database.mvp.MiddlePresenter;

import java.util.List;

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
//todo 注意clear的情况，因为clear的时候会先进行暂停，这会进行暂停的回调，会更新list状态；而clear本身也会清楚list中的该元素；注意这里会不会发生冲突

@SuppressLint("CheckResult")
public class DownloadTaskActivity extends AppCompatActivity implements Contract.View{
    private RecyclerView rv;
    public DownloaTaskAdapter downloaTaskAdapter;
//    public List<BeanPackaged> downloadTaskBeans;

    private Contract.Presenter presenter;
    private DownloadTaskViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_task);
        viewHolder = new ViewModelProvider(this).get(DownloadTaskViewHolder.class);
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
        downloaTaskAdapter = new DownloaTaskAdapter(viewHolder.getDownloadTaskBeans());
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

    public void downloadFanQie(View view) {
        addTaskToStart("https://ucdl.25pp.com/fs08/2024/06/14/10/120_e8c52a47e6c480830e0147811dab51f7.apk?nrd=0&fname=%E7%95%AA%E8%8C%84%E5%85%8D%E8%B4%B9%E5%B0%8F%E8%AF%B4&productid=2011&packageid=701056031&pkg=com.dragon.read&vcode=62532&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=8048758&apprd=8048758&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F04%2F28%2F7%2F125_ed8a38599daedc9b23ce74370416268e_con.png&did=aa28cee315a41efc7ff43319a4b494ca&md5=2dee3e15d6ce270f55aaa0cdb248d197",
                "/data/user/0/com.example.utilsuser/files/",
                "120_e8c52a47e6c480830e0147811dab51f7",
                "番茄免费小说");
    }

    public void downloadXiaoHongShu(View view) {
    }

    public void downloadPiPiXia(View view) {

    }

    public void showInfo(View view) {
        presenter.showInfo(viewHolder.getDownloadTaskBeans());
    }

    private void addTaskToStart(String url, String fileDir, String name, String showName) {
        presenter.addTask(url, fileDir, name, showName);
    }


    public void notifyCleared(int id) {
        for (int i = 0; i < this.viewHolder.getDownloadTaskBeans().size(); i++) {
            BeanPackaged beanPackaged = this.viewHolder.getDownloadTaskBeans().get(i);
            if (beanPackaged.downloadTaskBean.getId() == id) {
                viewHolder.getDownloadTaskBeans().remove(i);
                downloaTaskAdapter.notifyItemRemoved(i);
                LogUtil.d("删除什么位置的item: " + i);
                break;
            }
        }
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
    public void notifyDBTaskList(List<BeanPackaged> downloadTaskBeans) {
        viewHolder.setDownloadTaskBeans(downloadTaskBeans);
        initAdapter();
    }

    @Override
    public void notifyAddTask(BeanPackaged beanPackaged) {
        viewHolder.addTask(beanPackaged);
        downloaTaskAdapter.notifyItemInserted(0);
    }

    @Override
    public void onNotifyUpdateProgress(int id, long currentLength) {
        downloaTaskAdapter.notifyUpdateProgress(id, currentLength);
    }

    @Override
    public void onNotifyPause(int id) {
        for (int i = 0; i < viewHolder.getDownloadTaskBeans().size(); i++) {
            BeanPackaged beanPackaged = viewHolder.getDownloadTaskBeans().get(i);
            if (beanPackaged.downloadTaskBean.getId() == id) {
                beanPackaged.baseState = BaseState.PAUSED_STATE();
                downloaTaskAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onNotifyFinished(int id) {
        for (int i = 0; i < viewHolder.getDownloadTaskBeans().size(); i++) {
            BeanPackaged beanPackaged = viewHolder.getDownloadTaskBeans().get(i);
            if (beanPackaged.downloadTaskBean.getId() == id) {
                beanPackaged.baseState = BaseState.FINISHED_STATE();
                downloaTaskAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onNotifyError(int id, int errorCode) {
        switch (errorCode) {
            case 0:
                break;
            case 1:
                onNotifyPause(id);
                break;
            case 2:
                for (int i = 0; i < viewHolder.getDownloadTaskBeans().size(); i++) {
                    BeanPackaged beanPackaged = viewHolder.getDownloadTaskBeans().get(i);
                    if (beanPackaged.downloadTaskBean.getId() == id) {
                        beanPackaged.downloadTaskBean.setCurrentLength(-1L);
                        beanPackaged.baseState = BaseState.BROKEN_STATE();
                        downloaTaskAdapter.notifyItemChanged(i);
                        LogUtil.d("notifyBroken什么位置的item: " + i);
                        break;
                    }
                }
                break;
        }
    }

}