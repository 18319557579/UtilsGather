package com.example.utilsgather.file_system;

import android.content.Context;

import com.example.utilsgather.logcat.LogUtil;

import java.io.File;

public class FileInfoGainUtil {
    /**
     * 获得内部存储的路径
     * /data/user/0/com.example.utilsuser/files
     */
    public static String internalStoragePath(Context context) {
        File internalFile = context.getFilesDir();
        return internalFile.getPath();
    }

    /**
     * 获得该应用的外部存储路径
     * /storage/emulated/0/Android/data/com.example.utilsuser/files
     */
    public static String externalStoragePath(Context context) {
        return externalStoragePath(context, null);
    }

    /**
     * 获得该应用的外部存储路径，如果为null就是艮目录，
     * /storage/emulated/0/Android/data/com.example.utilsuser/files
     *
     * 如果为Environment.DIRECTORY_MUSIC,就是MUSIC目录
     * /storage/emulated/0/Android/data/com.example.utilsuser/files/Music
     */
    public static String externalStoragePath(Context context, String type) {
        File externalFile = context.getExternalFilesDir(type);
        return externalFile.getPath();
    }
}
