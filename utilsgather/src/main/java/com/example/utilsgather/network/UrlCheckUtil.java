package com.example.utilsgather.network;

import java.net.URL;

public class UrlCheckUtil {
    /**
     * 检查是否为有效的URL 
     */
    public static boolean isValidURL(String urlString) {
        try {
            new URL(urlString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
