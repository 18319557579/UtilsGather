package com.example.utilsuser.file.list.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.utilsgather.context.ApplicationGlobal;
import com.example.utilsgather.logcat.LogUtil;

import java.util.ArrayList;
import java.util.List;

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
        long insertResult = db.insert(DownloadTaskDBHelper.TABLE_TASK, null, contentValues);
        LogUtil.d("插入的结果：" + insertResult);

        db.close();

        return insertResult;
    }
    public synchronized void updateTask(int id, long downloadedLocation) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DownloadTaskDBHelper.FIELD_CURRENT_LENGTH, downloadedLocation);
        //返回收影响的行数
        int updateResult = db.update(DownloadTaskDBHelper.TABLE_TASK, contentValues,
                "id = ?", new String[]{String.valueOf(id)});

        db.close();
    }

    public List<DownloadTaskBean> queryTaskList() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        List<DownloadTaskBean> downloadTaskBeanList = new ArrayList<>();

        Cursor cursor = db.query(DownloadTaskDBHelper.TABLE_TASK, null, null,
                null, null, null, DownloadTaskDBHelper.FIELD_CREATE_TIME);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DownloadTaskDBHelper.FIELD_ID));
                @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex(DownloadTaskDBHelper.FIELD_URL));
                @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(DownloadTaskDBHelper.FIELD_PATH));
                @SuppressLint("Range") long currentLength = cursor.getLong(cursor.getColumnIndex(DownloadTaskDBHelper.FIELD_CURRENT_LENGTH));
                @SuppressLint("Range") long totalLength = cursor.getLong(cursor.getColumnIndex(DownloadTaskDBHelper.FIELD_TOTAL_LENGTH));
                @SuppressLint("Range") long createTime = cursor.getLong(cursor.getColumnIndex(DownloadTaskDBHelper.FIELD_CREATE_TIME));
                @SuppressLint("Range") String showName = cursor.getString(cursor.getColumnIndex(DownloadTaskDBHelper.FIELD_SHOW_NAME));

                DownloadTaskBean downloadTaskBean = new DownloadTaskBean();
                downloadTaskBean.setId(id);
                downloadTaskBean.setUrl(url);
                downloadTaskBean.setPath(path);
                downloadTaskBean.setCurrentLength(currentLength);
                downloadTaskBean.setCreateTime(createTime);
                downloadTaskBean.setShowName(showName);
                downloadTaskBean.setTotalLength(totalLength);

                LogUtil.d("打印数据中读出的Bean:" + downloadTaskBean);
                downloadTaskBeanList.add(downloadTaskBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return downloadTaskBeanList;
    }

}
