package com.example.utilsuser.file.list.database.network;

import com.example.utilsgather.format_trans.FormatTransfer;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.file.list.database.DownloadTaskBean;
import com.example.utilsuser.file.list.database.DownloadTaskDao;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateFileNetwork {
    private String downloadUrl;
    private String fileDir;
    private String fileName;
    DownloadTaskBean downloadTaskBean;

    public CreateFileNetwork(String downloadUrl, String fileDir, String fileName, DownloadTaskBean downloadTaskBean) {
        this.downloadUrl = downloadUrl;
        this.fileDir = fileDir;
        this.fileName = fileName;
        this.downloadTaskBean = downloadTaskBean;
    }

    /**
     * 获得要下载文件的大小，并在本地创建一个等大的空文件
     */
    public void start() {
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
                LogUtil.d("要下载的文件的大小： " + FormatTransfer.byteFormat(len));

                if (len > 0) {
                    File dir = new File(fileDir);
                    if (! dir.exists()) {
                        dir.mkdir();
                    }
                    File file = new File(dir, fileName);
                    //本地文件
                    /*raf = new RandomAccessFile(file, "rwd");
                    raf.setLength(len);*/

                    downloadTaskBean.setCreateTime(System.currentTimeMillis());
                    downloadTaskBean.setCurrentLength(0L);
                    downloadTaskBean.setTotalLength(len);

                    //数据库
                    long id = DownloadTaskDao.newInstance().insertTask(downloadTaskBean);
                    downloadTaskBean.setId((int) id);

                    downloadTaskBean.setUrl(downloadUrl);
                    downloadTaskBean.setPath(file.getPath());
                }
            }

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
}
