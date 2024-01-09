package com.example.utilsgather.exit;

import com.example.utilsgather.lifecycle_callback.CallbackActivity;

public class ExitHandleActivity extends CallbackActivity {
    @Override
    public void onBackPressed() {
        if (ExitUtil.handle(this, ExitUtil.Action.SYSTEM_HANDLE)) {
            return;
        }
        super.onBackPressed();
    }
}
