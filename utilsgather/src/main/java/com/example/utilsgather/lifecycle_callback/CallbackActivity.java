package com.example.utilsgather.lifecycle_callback;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CallbackActivity extends AppCompatActivity {
    private final String canonicalName = getClass().getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Daisy", canonicalName + " 回调onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Daisy", canonicalName + " 回调onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Daisy", canonicalName + " 回调onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Daisy", canonicalName + " 回调onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Daisy", canonicalName + " 回调onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Daisy", canonicalName + " 回调onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Daisy", canonicalName + " 回调onRestart");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Daisy", canonicalName + " 回调onNewIntent");
    }
}
