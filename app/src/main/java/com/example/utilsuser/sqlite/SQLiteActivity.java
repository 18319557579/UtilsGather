package com.example.utilsuser.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;

public class SQLiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
    }

    public void createDB(View view) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", 1);
        dbHelper.getWritableDatabase();
    }

    public void createDB2(View view) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", 2);
        dbHelper.getWritableDatabase();
    }

    public void insertData(View view) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "The Da Vinci Code");
        contentValues.put("author", "Dan Brown");
        contentValues.put("pages", 454);
        contentValues.put("price", 16.96);
        //新插入行的行ID，如果发生错误，则为-1
        long insertResult = db.insert("Book", null, contentValues);
        LogUtil.d("插入的结果1：" + insertResult);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("name", "The Last Symbol");
        contentValues2.put("author", "Dan Brown");
        contentValues2.put("pages", 510);
        contentValues2.put("price", 19.95);
        long insertResult2 = db.insert("Book", null, contentValues2);
        LogUtil.d("插入的结果2：" + insertResult2);

    }

    public void updateData(View view) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("price", 10.99);
        //返回收影响的行数
        int updateResult = db.update("Book", contentValues, "name = ?", new String[]{"The Da Vinci Code"});
        LogUtil.d("更新的结果：" + updateResult);
    }

    public void deleteData(View view) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int deleteResult = db.delete("Book", "pages > ?", new String[]{"500"});
        LogUtil.d("删除的结果：" + deleteResult);
    }

    public void queryData(View view) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", 2);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex("author"));
                @SuppressLint("Range") int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price"));

                LogUtil.d("book name is " + name);
                LogUtil.d("book author is " + author);
                LogUtil.d("book pages is " + pages);
                LogUtil.d("book price is " + price);
                LogUtil.d("----------------------------------------------------------------------------");

            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void insertDataExecSQL(View view) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
                new String[]{"被讨厌的勇气", "岸见一郎", "550", "78.00"});
        db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
                new String[]{"自卑与超越", "阿德勒", "800", "29.10"});

        dbHelper.close();
    }

    public void updateDataExecSQL(View view) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("update Book set price = ? where name = ?",
                new String[]{"100", "自卑与超越"});

        dbHelper.close();
    }
    public void deleteDataExecSQL(View view) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("delete from Book where pages > ?",
                new String[]{"700"});

        dbHelper.close();
    }
    public void queryDataExecSQL(View view) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from Book", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex("author"));
                @SuppressLint("Range") int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price"));

                LogUtil.d("book name is " + name);
                LogUtil.d("book author is " + author);
                LogUtil.d("book pages is " + pages);
                LogUtil.d("book price is " + price);
                LogUtil.d("----------------------------------------------------------------------------");

            } while (cursor.moveToNext());
        }
        cursor.close();

        dbHelper.close();
    }
}