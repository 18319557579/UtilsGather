package com.example.utilsuser.file.list.database;

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

    public static final String FIELD_URL = "url";
    public static final String FIELD_PATH = "path";
    public static final String FIELD_CURRENT_LENGTH = "current_length";
    public static final String FIELD_TOTAL_LENGTH = "total_length";
    public static final String FIELD_CREATE_TIME = "create_time";
    public static final String FIELD_SHOW_NAME = "show_name";

    private static final String createTaskTable = "create table Task (" +
            "id integer primary key autoincrement," +
            "{0} text," +
            "{1} text," +
            "{2} long," +
            "{3} long," +
            "{4} long," +
            "{5} text)";

    public DownloadTaskDBHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MessageFormat.format(createTaskTable,
                FIELD_URL, FIELD_PATH, FIELD_CURRENT_LENGTH, FIELD_TOTAL_LENGTH, FIELD_CREATE_TIME, FIELD_SHOW_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
