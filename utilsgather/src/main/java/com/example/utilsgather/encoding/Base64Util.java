package com.example.utilsgather.encoding;


import android.util.Base64;

public class Base64Util {
    /**
     * 将Base64的数据进行解码后返回
     */
    public static String decodeBase64(String originStr) {
        if (originStr == null) return null;
        byte[] b = Base64.decode(originStr, Base64.DEFAULT);
        return new String(b);
    }
}
