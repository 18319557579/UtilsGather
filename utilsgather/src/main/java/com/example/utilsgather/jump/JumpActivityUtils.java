package com.example.utilsgather.jump;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class JumpActivityUtils {

    /**
     * 跳转其它APP
     * @param context
     * @param packageName 跳转APP包名
     * @param activityName 跳转APP的Activity名
     */
    public static void startApp(Context context, String packageName, String activityName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setComponent(new ComponentName(packageName, activityName));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.d("Daisy", "Activity不存在：" + e);
        } catch (Exception e) {
            Log.d("Daisy", "其他问题：" + e);
        }
    }

    /**
     * 用URL的方式跳转APP
     * @param context
     * @param urlScheme url
     */
    public static void startApp(Context context, String urlScheme) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlScheme));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.d("Daisy", "Activity不存在：" + e);
        } catch (Exception e) {
            Log.d("Daisy", "其他问题：" + e);
        }
    }

}
