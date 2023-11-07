package com.example.utilsgather.ui;

import android.content.Context;
import android.util.TypedValue;

public class SizeTransferUtil {
    /**
     * dp传px
     */
    public static int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
