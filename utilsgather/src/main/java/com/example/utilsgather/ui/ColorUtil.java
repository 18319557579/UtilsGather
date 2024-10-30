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

    public static int getRandomColorARGB() {
        Random random = new Random();
        int a = random.nextInt(256);
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.argb(a, r,g,b);
    }
}
