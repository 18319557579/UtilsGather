package com.example.utilsuser.file.list.database.bean.state;

import com.example.utilsuser.R;

public abstract class BaseState {
    protected final int resumeResId = R.drawable.download_resume;
    protected final int pauseResId = R.drawable.download_pause;

    public String text;
    public int resId;

    private static BaseState PAUSED_STATE, DOWNLOADING_STATE, FINISHED_STATE, BROKEN_STATE;

    //避免了基类的静态初始化器中直接创建子类实例
    public static BaseState PAUSED_STATE() {
        if (PAUSED_STATE == null) {
            PAUSED_STATE = new PausedState();
        }
        return PAUSED_STATE;
    }

    public static BaseState DOWNLOADING_STATE() {
        if (DOWNLOADING_STATE == null) {
            DOWNLOADING_STATE = new DownloadingState();
        }
        return DOWNLOADING_STATE;
    }

    public static BaseState FINISHED_STATE() {
        if (FINISHED_STATE == null) {
            FINISHED_STATE = new FinishedState();
        }
        return FINISHED_STATE;
    }

    public static BaseState BROKEN_STATE() {
        if (BROKEN_STATE == null) {
            BROKEN_STATE = new BrokenState();
        }
        return BROKEN_STATE;
    }
}
