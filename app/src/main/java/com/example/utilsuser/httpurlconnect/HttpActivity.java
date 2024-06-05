package com.example.utilsuser.httpurlconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utilsgather.format_trans.FormatTransfer;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;
import com.example.utilsuser.httpurlconnect.jianshu_Baymax_zgl.HttpUrlConnectionAsyncTask;
import com.example.utilsuser.httpurlconnect.juejin_zhangfeng.JueJInZhangFengActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        findViewById(R.id.btn_start_base).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        BufferedReader reader = null;
                        try {
                            URL url = new URL("http://www.baidu.com");
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
                            InputStream in = connection.getInputStream();
                            reader = new BufferedReader(new InputStreamReader(in));

                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            LogUtil.d("最终相应的内容: " + response.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (connection != null) {
                                connection.disconnect();
                            }
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.btn_start_hongyang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        BufferedReader reader = null;
                        try {
                            URL url = new URL("https://wanandroid.com/article/list/0/json?author=鸿洋");
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
                            InputStream in = connection.getInputStream();
                            reader = new BufferedReader(new InputStreamReader(in));

                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            LogUtil.d("最终相应的内容: " + response.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (connection != null) {
                                connection.disconnect();
                            }
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.btn_start_hongyang_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        BufferedReader reader = null;
                        try {
                            URL url = new URL("https://www.wanandroid.com/user/register");
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
                            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                            out.writeBytes("username=ALittleDaisy2&password=987654321&repassword=987654321");

                            InputStream in = connection.getInputStream();
                            reader = new BufferedReader(new InputStreamReader(in));

                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            //最终相应的内容: {"data":{"admin":false,"chapterTops":[],"coinCount":0,"collectIds":[],"email":"","icon":"","id":161266,"nickname":"ALittleDaisy2","password":"","publicName":"ALittleDaisy2","token":"","type":0,"username":"ALittleDaisy2"},"errorCode":0,"errorMsg":""}
                            LogUtil.d("最终相应的内容: " + response.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (connection != null) {
                                connection.disconnect();
                            }
                        }
                    }
                }).start();
            }
        });
    }

    public void jumpNetworkActivity(View view) {
        Intent myIntent = new Intent(this, NetworkActivity.class);

        String tag = (String) view.getTag();
        switch (tag) {
            case "get":
                myIntent.putExtra("action", NetworkActivity.NetworkAsyncTask.NETWORK_GET);
                break;
            case "get_parse_json":
                myIntent.putExtra("action", NetworkActivity.NetworkAsyncTask.NETWORK_GET_PARSE_JSON);
                break;
            case "post":
                myIntent.putExtra("action", NetworkActivity.NetworkAsyncTask.NETWORK_POST_KEY_VALUE);
                break;
        }

        startActivity(myIntent);
    }

    public void simpleDownload(View view) {
        String[] urlPathArray = new String[] {
                "https://ucdl.25pp.com/fs08/2024/01/08/11/110_54c27c8bd85e0b3f44e7d5491b5aa6a0.apk?nrd=0&fname=%E8%AE%B0%E8%B4%A6%E6%9C%AC&productid=2011&packageid=401462537&pkg=com.bookkp.accountzy&vcode=8&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=8324843&apprd=8324843&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F01%2F08%2F9%2F110_d9d998c600f8c061a079c4f73fbc1016_con.png&did=5587ef1782b1f7f31a42ced9a659c49f&md5=78631c8544971195b950d14771843514",
                "https://ucdl.25pp.com/fs08/2024/05/28/8/120_1520704cba97e878685c716c5dd89961.apk?nrd=0&fname=%E6%8A%96%E9%9F%B3&productid=2011&packageid=500960169&pkg=com.ss.android.ugc.aweme&vcode=300101&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=7461948&apprd=7461948&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F05%2F29%2F0%2F110_948e6c5440768f9a40ddaf6036045056_con.png&did=6499240c97db2fab01ff289f059dbb92&md5=17dded2bed52cb677eb31ab8f3df33b6",
                "https://ucdl.25pp.com/fs08/2024/04/25/5/106_62d3b4571e2ad4fcd022dfd3eee40665.apk?nrd=0&fname=%E5%BE%AE%E4%BF%A1&productid=2011&packageid=602121932&pkg=com.tencent.mm&vcode=2600&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=596157&apprd=596157&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F04%2F25%2F1%2F106_94cb4d71918aeae9075f93d0b472d8d2_con.png&did=aa2ae20f8db904a42460b022d683219e&md5=6c8c3c8ccb60bd085d41159877ccee33",
                "https://ucdl.25pp.com/fs08/2024/05/31/4/1_b5ad5326678f8ae0fc218febcd525bc4.apk?nrd=0&fname=%E5%B0%8F%E7%BA%A2%E4%B9%A6&productid=2011&packageid=401600585&pkg=com.xingin.xhs&vcode=8381512&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=6233739&apprd=6233739&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F06%2F03%2F11%2F110_0d9fd027b032ec1f9912aefdb52c3e15_con.png&did=1f9702bdc0908ef80ca5d58376f1a98c&md5=ac107f06d6f063a91755f1c3b6ff6f44"
        };
        String[] filePath = new String[] {
                "/data/user/0/com.example.utilsuser/files/dl/jizhang.apk",
                "/data/user/0/com.example.utilsuser/files/dl/douyin.apk",
                "/data/user/0/com.example.utilsuser/files/dl/weixin.apk",
                "/data/user/0/com.example.utilsuser/files/dl/xiaohongshu.apk"
        };

        int length = urlPathArray.length;

        for (int count = 0; count < length; count++) {
            startSimpleDownload(
                    urlPathArray[count],
                    filePath[count],
                    new FinishListener() {
                        @Override
                        public void onFinish() {

                        }
                    });
        }
    }

    interface FinishListener {
        void onFinish();
    }
    private void startSimpleDownload(String urlPath, String filePath, FinishListener finishListener) {
        new HttpUrlConnectionAsyncTask().downloadFile(urlPath,
                filePath, new HttpUrlConnectionAsyncTask.OnHttpProgressUtilListener() {
                    @Override
                    public void onError(String e) {
                        LogUtil.d("错误: " + 3);
                    }

                    @Override
                    public void onProgress(Long... length) {
                        LogUtil.d("已下载: " + FormatTransfer.byteFormat(length[0]));
                        LogUtil.d("总大小: " + FormatTransfer.byteFormat(length[1]));

                        int progress = (int) (length[0] * 100 / length[1]);
                        LogUtil.d("进度: " + progress);
                    }

                    @Override
                    public void onSuccess(String json) {
                        LogUtil.d("结束： " + json);
                        finishListener.onFinish();
                    }
                });
    }

    public void jumpJueJInZhangFengActivity(View view) {
        Intent myIntent = new Intent(this, JueJInZhangFengActivity.class);
        startActivity(myIntent);
    }
}