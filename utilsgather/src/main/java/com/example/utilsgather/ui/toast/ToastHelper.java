package com.example.utilsgather.ui.toast;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
    private static Toast toast;

    // 简简单单地取消前一个
    public static void showToast(Context context, String text, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
