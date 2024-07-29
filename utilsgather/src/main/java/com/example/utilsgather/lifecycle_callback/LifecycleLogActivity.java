package com.example.utilsgather.lifecycle_callback;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsgather.logcat.LogUtil;

public class LifecycleLogActivity extends AppCompatActivity {
    protected final String canonicalName = getClass().getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.d("Daisy", canonicalName + " 回调onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        LogUtil.d("Daisy", canonicalName + " 回调onResume");
        super.onResume();

    }

    @Override
    protected void onStart() {
        LogUtil.d("Daisy", canonicalName + " 回调onStart");
        super.onStart();

    }

    @Override
    protected void onPause() {
        LogUtil.d("Daisy", canonicalName + " 回调onPause");
        super.onPause();

    }

    @Override
    protected void onStop() {
        LogUtil.d("Daisy", canonicalName + " 回调onStop");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        LogUtil.d("Daisy", canonicalName + " 回调onDestroy");
        super.onDestroy();

    }

    @Override
    protected void onRestart() {
        LogUtil.d("Daisy", canonicalName + " 回调onRestart");
        super.onRestart();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogUtil.d("Daisy", canonicalName + " 回调onNewIntent");
        super.onNewIntent(intent);

    }
}
