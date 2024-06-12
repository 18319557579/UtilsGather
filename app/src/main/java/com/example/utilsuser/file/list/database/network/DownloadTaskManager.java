package com.example.utilsuser.file.list.database.network;

import com.example.utilsgather.file_system.FileOperationUtil;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.file.list.database.DownloadTaskBean;
import com.example.utilsuser.file.list.database.DownloadTaskDao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTaskManager implements Runnable {

    private final DownloadTaskManager.DownloadListener downloadListener;

    private volatile boolean paused;

    private final DownloadTaskBean downloadTaskBean;


    public DownloadTaskManager(DownloadTaskBean downloadTaskBean, DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
        this.downloadTaskBean = downloadTaskBean;
    }

    public void pause() {
        paused = true;
    }
    public void resume() {
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public void run() {
        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        InputStream is = null;
        try {
            URL url = new URL(downloadTaskBean.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Range", "bytes=" + downloadTaskBean.getCurrentLength() + "-");

            //是从开头标记的位置算的文件长度，所以可能是文件的一部分
            long originFileLength = conn.getContentLength();
            LogUtil.d("打印本次要下载源文件长度: " + originFileLength);
            long wholeLength = originFileLength + downloadTaskBean.getCurrentLength();
            LogUtil.d("看看对不对得上：bean的总长度：" + downloadTaskBean.getTotalLength()
                            + ", 加出来的： " + wholeLength);

            File pathFile = new File(downloadTaskBean.getPath());
            raf = new RandomAccessFile(pathFile, "rwd");
            raf.seek(downloadTaskBean.getCurrentLength());

            long downloadedLocation = downloadTaskBean.getCurrentLength();
            if (conn.getResponseCode() != 206) {
                downloadListener.onFail("响应码错误: " + conn.getResponseCode(), 0);
                return;
            }

            is = conn.getInputStream();
            byte[] buf = new byte[1024 * 8];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                if (paused) {
                    downloadListener.onPause();
                    return;
                }

                //更新本地文件
                raf.write(buf, 0, len);
                downloadedLocation += len;

                //更新数据库
                DownloadTaskDao.newInstance().updateTask(downloadTaskBean.getId(), downloadedLocation);

                //更新UI
                downloadListener.downloading(downloadedLocation);
            }


            if (pathFile.exists()) {
                downloadListener.onSuccess(1);
            } else {
                DownloadTaskDao.newInstance().updateTask(downloadTaskBean.getId(), -1L);
                downloadListener.onFail("文件不存在", 2);
            }


        } catch (Exception e) {
            downloadListener.onFail(e.toString(), 1);

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

    public interface DownloadListener {
        void downloading(long now);
        void onSuccess(int whatCase);
        void onFail(String failDesc, int whatCase);
        void onPause();
    }
}