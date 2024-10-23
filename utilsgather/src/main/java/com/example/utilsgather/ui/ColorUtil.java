package com.example.utilsgather.ui;

import android.graphics.Color;

import java.util.Random;

public class ColorUtil {
    /**
     * 获得随机颜色
     */
    public static int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.rgb(r,g,b);
    }
}
