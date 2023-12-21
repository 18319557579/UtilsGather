package com.example.javautils.regex;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static boolean matches(String regex, String inputStr) {
        return Pattern.compile(regex).matcher(inputStr).matches();
    }

    public static boolean find(String regex, String inputStr) {
        return Pattern.compile(regex).matcher(inputStr).find();
    }

    public static String findSubstringFirst(String regex, String inputStr) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
    public static List<String> findSubstringAll(String regex, String inputStr) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputStr);
        List<String> substringList = new ArrayList<>();
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String match = inputStr.substring(start, end);
            substringList.add(match);
        }
        return substringList;
    }

    public static List<Integer> findAllLocation(String regex, String inputStr) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputStr);
        List<Integer> matchPosition = new ArrayList<>();
        while (matcher.find()) {
            int start = matcher.start();
            matchPosition.add(start);
        }
        return matchPosition;
    }

    public static String replaceAll(String regex, String inputStr, String replacement) {
        return Pattern.compile(regex).matcher(inputStr).replaceAll(replacement);
    }

    public static String replaceFirst(String regex, String inputStr, String replacement) {
        return Pattern.compile(regex).matcher(inputStr).replaceFirst(replacement);
    }
}
