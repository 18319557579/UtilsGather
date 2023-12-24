package com.example.javautils.regex;

import com.example.javautils.LogUtil;

import java.util.regex.Matcher;
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

    /**
     * 判断inputStr是否以prefix中的某个开头
     * 例，(^http://)|(^https://)
     * 转义之类的东西没有考虑
     */
    public static boolean startOf(String inputStr, String... prefix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String pre: prefix) {
            if (!stringBuilder.toString().isEmpty()) {
                stringBuilder.append("|");
            }
            String subschema = String.format("(^%s)", pre);
            stringBuilder.append(subschema);
        }
        LogUtil.println("得到的regex:" + stringBuilder.toString());

        Pattern pattern = Pattern.compile(stringBuilder.toString());
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.find();
    }

    /**
     * 将一个输入串中的单词的首字母都大写
     *
     * 例，dgHDf name is epeli sf_33f 87SDF_df _SDF423
     */
    public static String capitalization(String inputStr) {
        Pattern pattern = Pattern.compile("\\b[a-z]");
        Matcher matcher = pattern.matcher(inputStr);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group().toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 将一个输出串整体驼峰化
     *
     * 例，"-moz-transform  "
     */
    public static String humping(String inputStr) {
        Pattern pattern = Pattern.compile("[-_\\s]+(.)?");
        Matcher matcher = pattern.matcher(inputStr);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String initialLetter = matcher.group(1);
            if (initialLetter == null) {
                initialLetter = "";
            } else {
                initialLetter = initialLetter.toUpperCase();
            }
            matcher.appendReplacement(sb, initialLetter);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 中划线化。
     *
     * 例，"_M_oz  Trans-form-FDSa - "
     */
    public static String toStrike(String inputStr) {
        Pattern pattern = Pattern.compile("([A-Z])");
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.replaceAll("-$1")  //在大写字母之前都加上中划线
                .replaceAll("[-_\\s]+$", "")  //将末尾的指定字符干掉
                .replaceAll("[-_\\s]+", "-")  //将重复的指定字符替换为中划线
                .toLowerCase();  //中划线化都是小写字母
    }
}
