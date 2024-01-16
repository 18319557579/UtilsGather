package com.example.utilsgather.encoding;


import android.util.Base64;

import java.nio.charset.Charset;

public class Base64Util {
    /**
     * 将String进行Base64编码
     */
    public static String base64Encode(String content) {
        byte[] contentByte = content.getBytes(Charset.forName("UTF-8"));
        return Base64.encodeToString(contentByte, Base64.DEFAULT);
    }

    /**
     * 将Base64的数据进行解码后返回
     */
    public static String base64Decode(String originStr) {
        if (originStr == null) return null;

        byte[] b = Base64.decode(originStr, Base64.DEFAULT);
        return new String(b, Charset.forName("UTF-8"));
    }

}
