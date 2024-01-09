package com.example.utilsgather.exit;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsgather.logcat.LogUtil;

public enum ExitUtil {
    INSTANCE;

    private long lastBackClickTime = 0;

    public static boolean handle(AppCompatActivity activity, Action action) {
        LogUtil.d("点击了返回键");

        if ((System.currentTimeMillis() - INSTANCE.lastBackClickTime) > 2000) {
            Toast.makeText(activity, "再按一次回到桌面", Toast.LENGTH_SHORT).show();
            INSTANCE.lastBackClickTime = System.currentTimeMillis();
            return true;
        }

        switch (action) {
            case SYSTEM_HANDLE:
                return false;

            case SIMULATE_HOME:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                activity.startActivity(intent);
                break;
            case FINISH_ACTIVITY:
                activity.finish();
                break;
        }
        return true;
    }

    public enum Action {
        SYSTEM_HANDLE,  //让系统来处理返回事件
        SIMULATE_HOME,  //模拟home键
        FINISH_ACTIVITY;  //finish掉该Activity
    }
}
