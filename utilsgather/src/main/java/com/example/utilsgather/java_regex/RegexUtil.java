package com.example.utilsgather.java_regex;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    /**
     * 全局匹配
     */
    public static void globalFind(String regex, String origin) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(origin);

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String match = origin.substring(start, end);
            System.out.println("Match: " + match);
        }
    }

    /**
     * 单一匹配
     */
    public static void singleFind(String regex, String origin) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(origin);

        if (matcher.find()) {
            System.out.println("Match: " + matcher.group());
        }

    }
}
