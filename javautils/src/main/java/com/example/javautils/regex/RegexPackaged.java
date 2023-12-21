package com.example.javautils.regex;

import java.util.regex.Pattern;

public class RegexPackaged {
    /**
     * 判断是否为windows的路径
     */
    public static boolean isWindowsDir(String origin) {
        //即，^[a-zA-Z]:\\([^\:*<>|"?\r\n/]+\\)*([^\:*<>|"?\r\n/]+)?$
        String regex = "^[a-zA-Z]:\\\\([^\\:*<>|\"?\\r\\n/]+\\\\)*([^\\:*<>|\"?\\r\\n/]+)?$";
        return Pattern.matches(regex, origin);
    }

    /**
     * 去掉文件名的后缀，获得文件名
     */
    public static String getFileName(String wholeFileName) {
        return Pattern.compile("[.][^.]*$").matcher(wholeFileName).replaceFirst("");
    }
}
