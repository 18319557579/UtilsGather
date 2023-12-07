package com.example.utilsgather.random;

import java.util.Random;

public class RandomUtil {
    //获取[min, max] 的随机数
    public static int getRandomInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
