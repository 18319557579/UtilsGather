package com.example.utilsuser.file.list.database;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;
import com.example.utilsuser.file.list.database.network.CreateFileNetwork;

public class DownloadTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_task);
    }

    public void downloadJiZhang(View view) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                DownloadTaskBean downloadTaskBean = new DownloadTaskBean();
                downloadTaskBean.setShowName("记账本");
                new CreateFileNetwork("https://ucdl.25pp.com/fs08/2024/01/08/11/110_54c27c8bd85e0b3f44e7d5491b5aa6a0.apk?nrd=0&fname=%E8%AE%B0%E8%B4%A6%E6%9C%AC&productid=2011&packageid=401462537&pkg=com.bookkp.accountzy&vcode=8&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=8324843&apprd=8324843&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F01%2F08%2F9%2F110_d9d998c600f8c061a079c4f73fbc1016_con.png&did=5587ef1782b1f7f31a42ced9a659c49f&md5=78631c8544971195b950d14771843514",
                        "/data/user/0/com.example.utilsuser/files/",
                        "110_54c27c8bd85e0b3f44e7d5491b5aa6a0.apk",
                        downloadTaskBean)
                        .start();

                LogUtil.d("数据库中的信息: " + downloadTaskBean);
            }
        }).start();


    }
    public void downloadDouYin(View view) {
    }
    public void downloadWeiXin(View view) {
    }
    public void downloadXiaoHongShu(View view) {
    }
    public void downloadPiPiXia(View view) {
    }
}