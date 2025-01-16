package com.example.utilsgather.function;

import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.os.VibratorManager;

public class VibrateUtil {
    public static void vibrate(long milliseconds, Context context) {
        Vibrator vib;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            VibratorManager vibratorManager = (VibratorManager) context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            vib = vibratorManager.getDefaultVibrator();
        } else {
            vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
        vib.vibrate(milliseconds);
    }
}
