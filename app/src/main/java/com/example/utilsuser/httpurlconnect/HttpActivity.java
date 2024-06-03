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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
        new HttpUrlConnectionAsyncTask().downloadFile("https://ucdl.25pp.com/fs08/2024/01/08/11/110_54c27c8bd85e0b3f44e7d5491b5aa6a0.apk?nrd=0&fname=%E8%AE%B0%E8%B4%A6%E6%9C%AC&productid=2011&packageid=401462537&pkg=com.bookkp.accountzy&vcode=8&yingid=wdj_web&pos=wdj_web%2Fdetail_normal_dl%2F0&appid=8324843&apprd=8324843&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2024%2F01%2F08%2F9%2F110_d9d998c600f8c061a079c4f73fbc1016_con.png&did=5587ef1782b1f7f31a42ced9a659c49f&md5=78631c8544971195b950d14771843514",
                "/data/user/0/com.example.utilsuser/files/jizhang.apk", new HttpUrlConnectionAsyncTask.OnHttpProgressUtilListener() {
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
                        LogUtil.d("进度： " + json);

                    }
                });
    }
}