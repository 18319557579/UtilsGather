package com.example.utilsuser.httpurlconnect.jianshu_Baymax_zgl;

import android.os.AsyncTask;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlConnectionAsyncTask extends AsyncTask<Integer, Long, String> {
    private OnHttpProgressUtilListener onHttpProgressUtilListener;
    private String urlPath;
    private String filePath;

    public void downloadFile(String urlPath, String filePath, OnHttpProgressUtilListener onHttpProgressUtilListener) {
        this.urlPath = urlPath;
        this.filePath = filePath;
        this.onHttpProgressUtilListener = onHttpProgressUtilListener;
        execute(1);
    }

    @Override
    protected String doInBackground(Integer... integers) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String result = "";

        try {
            URL url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            inputStream = connection.getInputStream();
            //获取文件的长度
            long size = connection.getContentLength();

            outputStream = new FileOutputStream(filePath);
            int count = 0;
            long progress = 0L;
            byte[] bytes = new byte[2048];
            while ((count = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0 , count);  //写入文件中
                progress += count;
                publishProgress(progress, size);
            }

            result = "下载成功";

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null)
                connection.disconnect();
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        super.onProgressUpdate(values);
        onHttpProgressUtilListener.onProgress(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        onHttpProgressUtilListener.onSuccess(s);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        onHttpProgressUtilListener.onError(s);
    }

    public interface OnHttpProgressUtilListener {
        void onError(String e);
        void onProgress(Long... length);
        void onSuccess(String json);
    }
}
