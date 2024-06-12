package com.example.utilsuser.file.list.database;

import com.example.utilsgather.logcat.LogUtil;

import java.io.File;

public enum FileOperation {
    INSTANCE;

    public boolean deleteFile(String path) {
        File file = new File(path);
        if (! file.exists()) {
            LogUtil.d("源文件不存在");
            return false;
        }
        return file.delete();
    }

}
