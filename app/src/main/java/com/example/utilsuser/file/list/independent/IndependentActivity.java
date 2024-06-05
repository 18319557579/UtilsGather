package com.example.utilsuser.file.list.independent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utilsgather.file_system.FileOperationUtil;
import com.example.utilsgather.format_trans.FormatTransfer;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;
import com.example.utilsuser.httpurlconnect.juejin_zhangfeng.DownloadManager;

import java.io.File;

public class IndependentActivity extends AppCompatActivity {
    IndependentBean independentBean;

    private TextView tv_inde_file_name;
    private TextView tv_inde_file_path;
    private TextView tv_inde_file_length;
    private TextView tv_inde_file_last_modified;
    private Button btn_inde_pause;
    private Button btn_inde_clear;
    private ProgressBar pb_downloading;

    private int status = 0;  //0代表默认，1代表下载中

    DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_independent);

        tv_inde_file_name = findViewById(R.id.tv_inde_file_name);
        tv_inde_file_path = findViewById(R.id.tv_inde_file_path);
        tv_inde_file_length = findViewById(R.id.tv_inde_file_length);
        tv_inde_file_last_modified = findViewById(R.id.tv_inde_file_last_modified);
        btn_inde_pause = findViewById(R.id.btn_inde_pause);
        btn_inde_clear = findViewById(R.id.btn_inde_clear);
        pb_downloading = findViewById(R.id.pb_downloading);


        independentBean = new IndependentBean();
        independentBean.setUrl("https://ucdl.25pp.com/fs08/2024/05/31/4/1_b5ad5326678f8ae0fc218febcd525bc4.apk?nrd=0&fname=%E5%B0%8F%E7%BA%A2%E4%B9%A6&productid=2011&packageid=401600585&pkg=com.xingin.xhs&vcode=8381512&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=6233739&apprd=6233739&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F06%2F03%2F11%2F110_0d9fd027b032ec1f9912aefdb52c3e15_con.png&did=1f9702bdc0908ef80ca5d58376f1a98c&md5=ac107f06d6f063a91755f1c3b6ff6f44");
        independentBean.setPath("/data/user/0/com.example.utilsuser/files/dl/xiaohongshu.apk");

        File xiaohongshuFile = new File(independentBean.getPath());
        File finishedFile = FileOperationUtil.getFileWithPrefix(xiaohongshuFile, "finished-");
        if (finishedFile.exists()) {
            independentBean.setName(finishedFile.getName());
            independentBean.setLength(finishedFile.length());
            independentBean.setLastModified(finishedFile.lastModified());
            btn_inde_pause.setEnabled(false);

        } else if (xiaohongshuFile.exists()) {
            independentBean.setName(xiaohongshuFile.getName());
            independentBean.setLength(xiaohongshuFile.length());
            independentBean.setLastModified(xiaohongshuFile.lastModified());


        }

        tv_inde_file_name.setText(independentBean.getName());
        tv_inde_file_path.setText(independentBean.getPath());

        if (independentBean.getLength() != 0L)
            tv_inde_file_length.setText(FormatTransfer.byteFormat(independentBean.getLength()));

        if (independentBean.getLastModified() != 0L)
            tv_inde_file_last_modified.setText(FormatTransfer.timeStampFormat(independentBean.getLastModified()));


        btn_inde_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == 0) {
                    startDownload();

                    status = 1;
                    btn_inde_pause.setText("暂停");

                } else if (status == 1) {
                    downloadManager.pause();
                }
            }
        });
    }

    private void startDownload() {
        downloadManager = new DownloadManager();
        downloadManager.setOriginUrl(independentBean.getUrl());
        downloadManager.setFilePath(new File(independentBean.getPath()));
        downloadManager.setDownloadListener(new DownloadManager.DownloadListener() {
            @Override
            public void downloading(long now, long total) {
                LogUtil.d("下载中: now: " + now + ", total:" + total);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        independentBean.setLength(now);
                        independentBean.setTotalLength(total);
                        tv_inde_file_length.setText(FormatTransfer.byteFormat(independentBean.getLength()) +
                                " / " +
                                FormatTransfer.byteFormat(independentBean.getTotalLength())
                        );

                        int progress = (int) (now * 100 / total);
                        pb_downloading.setProgress(progress);
                    }
                });
            }

            @Override
            public void onSuccess(int whatCase) {
                LogUtil.d("下载成功: " + whatCase);
                status = 0;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_inde_pause.setEnabled(false);
                    }
                });
            }

            @Override
            public void onFail(String failDesc) {
                LogUtil.d("下载失败: " + failDesc);
            }

            @Override
            public void onPause() {
                LogUtil.d("暂停了" );
                status = 0;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_inde_pause.setText("继续");
                    }
                });
            }
        });
        new Thread(downloadManager).start();
    }
}