package com.example.utilsuser.sqlite;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
}