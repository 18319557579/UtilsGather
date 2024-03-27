package com.example.utilsgather.cutomerview.one_seven;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCustomView extends View {
    // 构造方法
    public MyCustomView(Context context) {
        super(context);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBackgroundColorRGB(int[] rgbValues) {
        // 确保数组长度为3
        if (rgbValues == null || rgbValues.length != 3) {
            throw new IllegalArgumentException("RGB array must contain exactly 3 values.");
        }
        // 使用RGB值设置背景颜色
        setBackgroundColor(Color.rgb(rgbValues[0], rgbValues[1], rgbValues[2]));
    }
}
