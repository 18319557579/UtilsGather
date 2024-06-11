package com.example.utilsuser.file.list.database;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ChangeReceiver extends BroadcastReceiver {

    //action
    public static final String ACTION_UPDATE = "ACTION_UPDATE";

    //extra
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_CURRENT_LENGTH = "EXTRA_CURRENT_LENGTH";

    public DownloadTaskActivity downloadTaskActivity;

    public ChangeReceiver(DownloadTaskActivity downloadTaskActivity) {
        this.downloadTaskActivity = downloadTaskActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (EXTRA_ID.equals(action)) {
            int id = intent.getIntExtra(EXTRA_ID, -1);
            long currentLength = intent.getLongExtra(EXTRA_CURRENT_LENGTH, -1);
            downloadTaskActivity.updateProgress(id, currentLength);
        }
    }
}