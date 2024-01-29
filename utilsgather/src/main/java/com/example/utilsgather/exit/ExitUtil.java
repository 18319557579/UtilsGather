package com.example.utilsgather.exit;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsgather.logcat.LogUtil;

public enum ExitUtil {
    INSTANCE;

    private long lastBackClickTime = 0;

    /**
     * 连续两次返回才退出应用
     * @param activity 主Activity，即退出应用前的最后一个Activity
     * @param action 连续两次返回时的处理行为
     * @param isToast 是否展示弹窗
     *
     * @return 是否进行了处理：
     * 1.点击距离上次的时间>2000ms，那么进行处理，吞噬掉这次点击
     * 2.点击距离上次的时间<2000ms，判断行为，如果为SYSTEM_HANDLE，那么不进行处理，交由系统处理；其他的情况，根据用户的选择进行处理
     */
    public static boolean handle(AppCompatActivity activity, Action action, boolean isToast) {
        LogUtil.d("点击了返回键");

        if ((System.currentTimeMillis() - INSTANCE.lastBackClickTime) > 2000) {
            if (isToast) {
                Toast.makeText(activity, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            }
            LogUtil.d("再按一次回到桌面");
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
