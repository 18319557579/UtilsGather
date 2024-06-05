package com.example.utilsuser.file.list;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilsgather.file_system.FileInfoGainUtil;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;
import com.example.utilsuser.httpurlconnect.juejin_zhangfeng.DownloadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView rv;
    List<FileInfoBean> fileInfoBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView();
        gainFileInfo();
    }

    private void initView() {
        rv = findViewById(R.id.rv_file_info_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FileInfoAdapter fileInfoAdapter = new FileInfoAdapter(fileInfoBeans);
        rv.setAdapter(fileInfoAdapter);
    }

    private void gainFileInfo() {
        File internalFile = getFilesDir();
        File downloadFile = new File(internalFile, "dl");
        LogUtil.d("打印下载路径: " + downloadFile);

        File[] listFiles = downloadFile.listFiles();
        for (File file: listFiles) {
            FileInfoBean bean = new FileInfoBean();
            bean.setName(file.getName());
            bean.setPath(file.getPath());
            bean.setLength(file.length());
            bean.setLastModified(file.lastModified());

            fileInfoBeans.add(bean);
        }
    }

    public void downloadJiZhang(View view) {
        DownloadManager downloadManager = new DownloadManager();
        downloadManager.setOriginUrl("https://ucdl.25pp.com/fs08/2024/01/08/11/110_54c27c8bd85e0b3f44e7d5491b5aa6a0.apk?nrd=0&fname=%E8%AE%B0%E8%B4%A6%E6%9C%AC&productid=2011&packageid=401462537&pkg=com.bookkp.accountzy&vcode=8&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=8324843&apprd=8324843&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F01%2F08%2F9%2F110_d9d998c600f8c061a079c4f73fbc1016_con.png&did=5587ef1782b1f7f31a42ced9a659c49f&md5=78631c8544971195b950d14771843514");
        downloadManager.setFilePath(new File("/data/user/0/com.example.utilsuser/files/dl/jizhang.apk"));
        downloadManager.setDownloadListener(new DownloadManager.DownloadListener() {
            @Override
            public void downloading(long now, long total) {
                LogUtil.d("下载中: now: " + now + ", total:" + total);
            }

            @Override
            public void onSuccess(int whatCase) {
                LogUtil.d("下载成功: " + whatCase);
            }

            @Override
            public void onFail(String failDesc) {
                LogUtil.d("下载失败: " + failDesc);
            }

            @Override
            public void onPause() {

            }
        });
        new Thread(downloadManager).start();
    }
    public void downloadDouYin(View view) {
        DownloadManager downloadManager = new DownloadManager();
        downloadManager.setOriginUrl("https://ucdl.25pp.com/fs08/2024/05/28/8/120_1520704cba97e878685c716c5dd89961.apk?nrd=0&fname=%E6%8A%96%E9%9F%B3&productid=2011&packageid=500960169&pkg=com.ss.android.ugc.aweme&vcode=300101&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=7461948&apprd=7461948&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F05%2F29%2F0%2F110_948e6c5440768f9a40ddaf6036045056_con.png&did=6499240c97db2fab01ff289f059dbb92&md5=17dded2bed52cb677eb31ab8f3df33b6");
        downloadManager.setFilePath(new File("/data/user/0/com.example.utilsuser/files/dl/douyin.apk"));
        downloadManager.setDownloadListener(new DownloadManager.DownloadListener() {
            @Override
            public void downloading(long now, long total) {
                LogUtil.d("下载中: now: " + now + ", total:" + total);
            }

            @Override
            public void onSuccess(int whatCase) {
                LogUtil.d("下载成功: " + whatCase);
            }

            @Override
            public void onFail(String failDesc) {
                LogUtil.d("下载失败: " + failDesc);
            }

            @Override
            public void onPause() {

            }
        });
        new Thread(downloadManager).start();
    }
    public void downloadWeiXin(View view) {
    }
    public void downloadXiaoHongShu(View view) {
    }
    public void downloadPiPiXia(View view) {
    }

}