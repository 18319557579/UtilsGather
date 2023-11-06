package com.example.utilsgather;

import android.os.Bundle;

import com.example.utilsgather.lifecycle_callback.CallbackActivity;

public class MainActivity extends CallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}