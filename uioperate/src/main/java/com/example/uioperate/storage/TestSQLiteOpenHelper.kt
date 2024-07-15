package com.example.uioperate.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TestSQLiteOpenHelper(context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version){

    companion object {
        val DATABASE_NAME = "TestDatabase"
    }

    private val TABLE_NAME = "TestTable"
    private val CREATE_TABLE = "create table %s (id integer primary key autoincrement, name text)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(String.format(CREATE_TABLE, TABLE_NAME))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}