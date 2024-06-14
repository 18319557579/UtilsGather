package com.example.utilsuser.file.list.database.four_components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.file.list.database.mvp.Contract;

public class ChangeReceiver extends BroadcastReceiver {

    //action
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_PAUSED = "ACTION_PAUSED";
    public static final String ACTION_FINISHED = "ACTION_FINISHED";
    public static final String ACTION_ERROR = "ACTION_ERROR";

    //extra
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_CURRENT_LENGTH = "EXTRA_CURRENT_LENGTH";
    public static final String EXTRA_ERROR_CODE = "EXTRA_ERROR_CODE";

    private Contract.ReceiverCallback receiverCallback;

    public ChangeReceiver(Contract.ReceiverCallback receiverCallback) {
        this.receiverCallback = receiverCallback;
    }

    public IntentFilter getAllActionIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ChangeReceiver.ACTION_UPDATE);
        filter.addAction(ChangeReceiver.ACTION_PAUSED);
        filter.addAction(ChangeReceiver.ACTION_FINISHED);
        filter.addAction(ChangeReceiver.ACTION_ERROR);
        return filter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtil.d("downloading", "广播接收到了 " + action);
        if (ACTION_UPDATE.equals(action)) {
            int id = intent.getIntExtra(EXTRA_ID, -1);
            long currentLength = intent.getLongExtra(EXTRA_CURRENT_LENGTH, -1);
            receiverCallback.onNotifyUpdateProgress(id, currentLength);

        } else if (ACTION_PAUSED.equals(action)) {
            int id = intent.getIntExtra(EXTRA_ID, -1);
            receiverCallback.onNotifyPause(id);

        } else if (ACTION_FINISHED.equals(action)) {
            int id = intent.getIntExtra(EXTRA_ID, -1);
            receiverCallback.onNotifyFinished(id);

        } else if (ACTION_ERROR.equals(action)) {
            int id = intent.getIntExtra(EXTRA_ID, -1);
            int errorCode = intent.getIntExtra(EXTRA_ERROR_CODE, -1);
            receiverCallback.onNotifyError(id, errorCode);
        }
    }
}