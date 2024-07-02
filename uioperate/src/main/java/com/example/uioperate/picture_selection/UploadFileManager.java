package com.example.uioperate.picture_selection;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class UploadFileManager {
    private static final String nextLine = "\r\n";
    private static final String twoHyphens = "--";//连接符
    //分割线  随便写一个
    private static final String boundary = "android";

    private void upLoadFile(final String url, final String filePath) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                File file = new File(filePath);
                HttpURLConnection connection = null;
                try {
                    URL url1 = new URL(url);
                    connection = (HttpURLConnection) url1.openConnection();
                    connection.setDoOutput(true);//允许输出流
                    //设置请求参数格式以及boundary分割线
                    connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    //传递头参
                    connection.setRequestProperty("userId", "1892");

                    connection.setRequestProperty("sessionId","15856368900771892");

                    OutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    //分隔符头部
                    String header = twoHyphens + boundary + nextLine;
                    //分隔符参数设置
                    header += "Content-Disposition: form-data;name=\"image\";" + "filename=\"" + file.getName() + "\"" + nextLine + nextLine;
                    //写入输出流
                    outputStream.write(header.getBytes());

                    //读取文件并写入
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, length);
                    }
                    //文件写入完成后加回车
                    outputStream.write(nextLine.getBytes());

                    //写入结束分隔符
                    String footer = nextLine + twoHyphens + boundary + twoHyphens + nextLine;
                    outputStream.write(footer.getBytes());
                    outputStream.flush();

                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream1 = connection.getInputStream();
                        String json = new String(convertStreamToString(inputStream1));
                        Log.i("====", json);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
