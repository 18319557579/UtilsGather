package com.example.uioperate.shortcus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.utilsgather.logcat.LogUtil;

/**
 * 添加成功后，或者已经有了的情况下，都会回调到这里
 */
public class ShortcutReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.d("接收到广播了");
    }
}
