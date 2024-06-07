package com.example.utilsuser.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;

public class MyServiceActivity extends AppCompatActivity {
    private MyService.DownloadBinder downloadBinder;
    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d("MyServiceActivity中回调 onServiceConnected()");
            downloadBinder = (MyService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d("MyServiceActivity中回调 onServiceDisconnected() " + name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);
    }

    public void invokeStartService(View view) {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    public void invokeStopService(View view) {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }


    public void invokeBindService(View view) {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public void invokeUnbindService(View view) {
        unbindService(connection);
        downloadBinder = null;
    }

    public void invokeMyServiceFunction(View view) {
        downloadBinder.getProgress();
    }
    public void invokeMyServiceFunctionThread(View view) {
        downloadBinder.startDownload();
    }
}