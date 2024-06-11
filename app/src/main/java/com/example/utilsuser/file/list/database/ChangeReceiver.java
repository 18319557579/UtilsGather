package com.example.utilsuser.file.list.database;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class ChangeReceiver extends BroadcastReceiver {

    //action
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_PAUSED = "ACTION_PAUSED";
    public static final String ACTION_FINISHED = "ACTION_FINISHED";

    //extra
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_CURRENT_LENGTH = "EXTRA_CURRENT_LENGTH";



    public DownloadTaskActivity downloadTaskActivity;

    public ChangeReceiver(DownloadTaskActivity downloadTaskActivity) {
        this.downloadTaskActivity = downloadTaskActivity;
    }

    public static IntentFilter getAllActionIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ChangeReceiver.ACTION_UPDATE);
        filter.addAction(ChangeReceiver.ACTION_PAUSED);
        filter.addAction(ChangeReceiver.ACTION_FINISHED);
        return filter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_UPDATE.equals(action)) {
            int id = intent.getIntExtra(EXTRA_ID, -1);
            long currentLength = intent.getLongExtra(EXTRA_CURRENT_LENGTH, -1);
            downloadTaskActivity.notifyUpdateProgress(id, currentLength);

        } else if (ACTION_PAUSED.equals(action)) {
            int id = intent.getIntExtra(EXTRA_ID, -1);
            downloadTaskActivity.notifyPause(id);

        } else if (ACTION_FINISHED.equals(action)) {
            int id = intent.getIntExtra(EXTRA_ID, -1);
            downloadTaskActivity.notifyFinished(id);
        }
    }
}