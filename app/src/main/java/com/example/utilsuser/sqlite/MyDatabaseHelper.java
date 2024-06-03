package com.example.utilsuser.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.utilsgather.logcat.LogUtil;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String createBook = "create table Book (" +
            " id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text)";


    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(createBook);  //只调用了构造函数，但是没有执行这句话的话，是不会创建数据库的
        LogUtil.d("MyDatabaseHelper回调onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
