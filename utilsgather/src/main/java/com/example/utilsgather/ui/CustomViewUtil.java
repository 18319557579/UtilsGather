package com.example.utilsgather.ui;

import android.view.View;

public class CustomViewUtil {
    /**
     * 打印宽高信息
     */
    public static String getWidthHeightInfo(int widthMeasureSpec, int heightMeasureSpec) {
        String widthInfo = "width " + View.MeasureSpec.toString(widthMeasureSpec);
        String heightInfo = "height " + View.MeasureSpec.toString(heightMeasureSpec);
        return "父View传来的宽高值:\n" + widthInfo + "\n" + heightInfo;
    }
}
