package com.example.utilsuser.file.list.database.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.MessageFormat;

public class DownloadTaskDBHelper extends SQLiteOpenHelper {
    private static volatile DownloadTaskDBHelper mDownloadTaskDBHelper;
    public static DownloadTaskDBHelper newInstance(Context context) {
        if (mDownloadTaskDBHelper == null) {
            synchronized (DownloadTaskDBHelper.class) {
                if (mDownloadTaskDBHelper == null) {
                    mDownloadTaskDBHelper = new DownloadTaskDBHelper(context, "download_task.db", 1);
                }
            }
        }
        return mDownloadTaskDBHelper;
    }

    //Field
    public static final String FIELD_ID = "id";
    public static final String FIELD_URL = "url";
    public static final String FIELD_PATH = "path";
    public static final String FIELD_CURRENT_LENGTH = "current_length";
    public static final String FIELD_TOTAL_LENGTH = "total_length";
    public static final String FIELD_CREATE_TIME = "create_time";
    public static final String FIELD_SHOW_NAME = "show_name";

    //Table
    public static final String TABLE_TASK = "Task";

    private static final String createTaskTable = "create table {0} (" +
            "{1} integer primary key autoincrement," +
            "{2} text," +
            "{3} text," +
            "{4} long," +
            "{5} long," +
            "{6} long," +
            "{7} text)";

    public DownloadTaskDBHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MessageFormat.format(createTaskTable, TABLE_TASK,
                FIELD_ID, FIELD_URL, FIELD_PATH, FIELD_CURRENT_LENGTH, FIELD_TOTAL_LENGTH, FIELD_CREATE_TIME, FIELD_SHOW_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
