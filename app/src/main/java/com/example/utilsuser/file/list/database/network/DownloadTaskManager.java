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

    private boolean paused;

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

            raf = new RandomAccessFile(downloadTaskBean.getPath(), "rwd");
            raf.seek(downloadTaskBean.getCurrentLength());

            long downloadedLocation = downloadTaskBean.getCurrentLength();
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
                    downloadedLocation += len;

                    DownloadTaskDao.newInstance().updateTask(downloadTaskBean.getId(), downloadedLocation);

                    downloadListener.downloading(downloadedLocation);
                }
            }

            /*if (! filePath.renameTo(finishedFile)) {
                downloadListener.onFail("下载完成后改名失败");
                return;
            }*/

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


    /*private long localFileCheck2() {
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
    }*/


    public interface DownloadListener {
        void downloading(long now);
        void onSuccess(int whatCase);
        void onFail(String failDesc);
        void onPause();
    }
}