package com.example.utilsuser.file.list.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.utilsgather.context.ApplicationGlobal;
import com.example.utilsgather.logcat.LogUtil;

public class DownloadTaskDao {
    private final DownloadTaskDBHelper mDBHelper;

    public DownloadTaskDao(Context context) {
        this.mDBHelper = DownloadTaskDBHelper.newInstance(context);
    }

    private static volatile DownloadTaskDao downloadTaskDao;
    public static DownloadTaskDao newInstance() {
        if (downloadTaskDao == null) {
            synchronized (DownloadTaskDao.class) {
                if (downloadTaskDao == null) {
                    downloadTaskDao = new DownloadTaskDao(ApplicationGlobal.getInstance());
                }
            }
        }
        return downloadTaskDao;
    }

    public synchronized long insertTask(DownloadTaskBean downloadTaskBean) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        /*db.execSQL("insert into Task (url, path, current_length, total_length, create_time, show_name) values(?, ?, ?, ?, ?, ?)",
                new Object[]{downloadTaskBean.getUrl(), downloadTaskBean.getPath(), downloadTaskBean.getCurrentLength(), downloadTaskBean.getTotalLength(), downloadTaskBean.getCreateTime(), downloadTaskBean.getShowName()});*/

        ContentValues contentValues = new ContentValues();
        contentValues.put(DownloadTaskDBHelper.FIELD_URL, downloadTaskBean.getUrl());
        contentValues.put(DownloadTaskDBHelper.FIELD_PATH, downloadTaskBean.getPath());
        contentValues.put(DownloadTaskDBHelper.FIELD_CURRENT_LENGTH, downloadTaskBean.getCurrentLength());
        contentValues.put(DownloadTaskDBHelper.FIELD_TOTAL_LENGTH, downloadTaskBean.getTotalLength());
        contentValues.put(DownloadTaskDBHelper.FIELD_CREATE_TIME, downloadTaskBean.getCreateTime());
        contentValues.put(DownloadTaskDBHelper.FIELD_SHOW_NAME, downloadTaskBean.getShowName());
        //新插入行的行ID，如果发生错误，则为-1
        long insertResult = db.insert("Task", null, contentValues);
        LogUtil.d("插入的结果：" + insertResult);

        db.close();

        return insertResult;
    }
    void deleteTask(String id) {

    }

}
