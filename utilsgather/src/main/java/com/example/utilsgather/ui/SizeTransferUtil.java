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

    /**
     * dip传px（不知道和上面那个区别在哪里）
     */
    public static int dip2px(float dpValue, Context context) {
        float scaleDensity = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scaleDensity + 0.5f);
    }
}
