package com.example.utilsuser.application;

import com.example.utilsgather.context.ApplicationGlobal;
import com.example.utilsgather.file_system.FilePathUtil;

public class XLogConstant {
    public static final String GLOBAL_TAG = "GlobalTAG";

    public static final String COPY_FUNCTION = "Copy";
    public static final String BLACK_FUNCTION = "Black";

    /**
     * 获得日志文件的文件夹路径
     */
    public static String getFolderPath() {
        return FilePathUtil.internalStoragePath(ApplicationGlobal.getInstance()) + "/daily_log";
    }

    /**
     * 获得日志压缩文件的路径
     */
    public static String getZipPath() {
        return FilePathUtil.internalStoragePath(ApplicationGlobal.getInstance()) + "/daily_zip/compressed_log.zip";
    }

    // 1M bytes
    public static final long DEFAULT_LOG_FILE_MAX_SIZE = 1024 * 1024;
//    public static final long DEFAULT_LOG_FILE_MAX_SIZE = 1024;

    // 一周的毫秒数： 7天 * 24小时 * 60分钟 * 60秒 * 1000毫秒
    public static final long MILL_SECONDS_IN_A_WEEK = 7L * 24 * 60 * 60 * 1000;
//    public static final long MILL_SECONDS_IN_A_WEEK = 1 * 60 * 1000;

}
