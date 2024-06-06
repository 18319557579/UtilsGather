package com.example.utilsuser.file.list.database.network;

import com.example.utilsgather.format_trans.FormatTransfer;
import com.example.utilsgather.logcat.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkCreateFile implements Runnable{
    private String downloadUrl;
    private String fileDir;
    private String fileName;
    private InfoCallback infoCallback;

    private String filePath;
    private long totalLength;



    public NetworkCreateFile(String downloadUrl, String fileDir, String fileName, InfoCallback infoCallback) {
        this.downloadUrl = downloadUrl;
        this.fileDir = fileDir;
        this.fileName = fileName;

        this.infoCallback = infoCallback;
    }

    /**
     * 获得要下载文件的大小，并在本地创建一个等大的空文件
     */
    @Override
    public void run() {
        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        try {
            URL url = new URL(downloadUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                long len = conn.getContentLength();
                totalLength = len;
                LogUtil.d("要下载的文件的大小： " + FormatTransfer.byteFormat(len));

                if (len > 0) {
                    File dir = new File(fileDir);
                    if (! dir.exists()) {
                        dir.mkdir();
                    }
                    File file = new File(dir, fileName);
                    filePath = file.getPath();
                    raf = new RandomAccessFile(file, "rwd");
                    raf.setLength(len);
                }
            }

            infoCallback.getTotalLength(filePath, totalLength);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public interface InfoCallback {
        void getTotalLength(String filePath, long totalLength);
    }
}
