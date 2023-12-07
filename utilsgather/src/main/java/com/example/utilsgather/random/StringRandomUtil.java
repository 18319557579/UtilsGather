package com.example.utilsgather.random;

import static com.example.utilsgather.random.RandomUtil.getRandomInt;

import java.util.Random;

public class StringRandomUtil {

    private static String getSpecifiedDict(int label) {
        String dictionary = "";
        switch (label) {
            case 0:
                dictionary = "abcdefghijklmnopqrstuvwxyz";
                break;
            case 1:
                dictionary = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            case 2:
                dictionary = "abcdefghijklmnopqrstuvwxyz0123456789";
                break;
            case 3:
                dictionary = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                break;
        }
        return dictionary;
    }




    //获得指定区间[min, max]位数的字符串
    public static String getRandomStringWithSection(int minDigit, int maxDigit, int option) {
        char[] dictionaries = getSpecifiedDict(option).toCharArray();
        StringBuilder sb = new StringBuilder();
        int digits = RandomUtil.getRandomInt(minDigit, maxDigit);
        for (int i = 0; i < digits; i++) {
            sb.append(dictionaries[new Random().nextInt(dictionaries.length)]);
        }
        return sb.toString();
    }

    /**
     * 简易的获得随机字符串，6位
     */
    public static String getRandomString() {
        return getRandomStringWithSection(6, 6, 3);
    }
}
