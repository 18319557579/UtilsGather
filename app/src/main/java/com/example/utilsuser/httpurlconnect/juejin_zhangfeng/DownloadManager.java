package com.example.utilsuser.httpurlconnect.juejin_zhangfeng;

import com.example.utilsgather.file_system.FileOperationUtil;
import com.example.utilsgather.logcat.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadManager implements Runnable {
    private String originUrl;
    private File filePath;
    private DownloadListener downloadListener;

    //成功的文件名
    private File finishedFile;

    private boolean paused;

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public void pause() {
        paused = true;
    }

    @Override
    public void run() {
        long startLocation = localFileCheck2();
        if (startLocation == -1) {
            downloadListener.onSuccess(0);
            return;
        }

        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        InputStream is = null;
        try {
            URL url = new URL(originUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Range", "bytes=" + startLocation + "-");

            //是从开头标记的位置算的文件长度，所以可能是文件的一部分
            long originFileLength = conn.getContentLength();
            LogUtil.d("打印源文件长度: " + originFileLength);
            long wholeLength = originFileLength + startLocation;

            raf = new RandomAccessFile(filePath, "rwd");
            raf.seek(startLocation);

            if (conn.getResponseCode() == 206) {
                is = conn.getInputStream();
                byte[] buf = new byte[1024 * 4];
                int len = 0;
                while ((len = is.read(buf)) != -1) {
                    if (paused) {
                        downloadListener.onPause();
                        return;
                    }

                    raf.write(buf, 0, len);
                    startLocation += len;

                    downloadListener.downloading(startLocation, wholeLength);
                }
            }

            if (! filePath.renameTo(finishedFile)) {
                downloadListener.onFail("下载完成后改名失败");
                return;
            }

            downloadListener.onSuccess(1);

        } catch (Exception e) {
            downloadListener.onFail(e.toString());

        } finally
        {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                if (raf != null) {
                    raf.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private long localFileCheck(long originFileLength) {
        if (! filePath.exists()) {
            LogUtil.d("文件无");
            return 0;
        }

        long localFileLength = filePath.length();
        if (localFileLength == originFileLength) {
            LogUtil.d("文件已存在，已下载完成");
            return -1;
        }

        LogUtil.d("文件已存在，断点");
        return localFileLength;
    }

    private long localFileCheck2() {
        finishedFile = FileOperationUtil.getFileWithPrefix(filePath, "finished-");
        if (finishedFile.exists()) {
            LogUtil.d("文件早已完成");
            return -1;
        }

        if (! filePath.exists()) {
            LogUtil.d("文件无");
            return 0;
        }

        LogUtil.d("文件已存在，断点:" + filePath.length());
        return filePath.length();
    }


    public interface DownloadListener {
        void downloading(long now, long total);
        void onSuccess(int whatCase);
        void onFail(String failDesc);
        void onPause();
    }
}
