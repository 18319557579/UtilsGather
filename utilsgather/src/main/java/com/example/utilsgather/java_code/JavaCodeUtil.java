package com.example.utilsgather.java_code;

public class JavaCodeUtil {
    /**
     * 字符串逆序输出
     */
    public static String reverseString(String str) {
        StringBuilder buffer = new StringBuilder(str);
        return buffer.reverse().toString();
    }
}
