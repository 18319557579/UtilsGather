package com.example.utilsgather.lifecycle_callback;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsgather.logcat.LogUtil;

public class CallbackActivity extends AppCompatActivity {
    protected final String canonicalName = getClass().getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("Daisy", canonicalName + " 回调onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("Daisy", canonicalName + " 回调onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("Daisy", canonicalName + " 回调onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("Daisy", canonicalName + " 回调onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("Daisy", canonicalName + " 回调onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("Daisy", canonicalName + " 回调onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("Daisy", canonicalName + " 回调onRestart");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d("Daisy", canonicalName + " 回调onNewIntent");
    }
}
