package com.example.utilsgather.exit;

import com.example.utilsgather.lifecycle_callback.LifecycleLogActivity;

public class ExitHandleActivity extends LifecycleLogActivity {
    @Override
    public void onBackPressed() {
        if (ExitUtil.handle(this, ExitUtil.Action.SYSTEM_HANDLE, true)) {
            return;
        }
        super.onBackPressed();
    }
}
