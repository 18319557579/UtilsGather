package com.example.utilsgather.logcat;

import android.util.Log;


public class LogUtil {
    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final String TAG = "Daisy";  //默认的tag

    //有5种过滤级别。当level设置为VERBOSE，那么所有的日志都可以打印出来；当level设置为WARN，那么只有WARN,ERROR可以被打印出来；
    //当设置为NONE，那么所有的日志都打印不出来了
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NONE = 6;

    private static int level = VERBOSE;

    /**
     * 所有日志都可以打印
     */
    public static void setAllLog() {
        level = VERBOSE;
    }

    /**
     * 所有的日志都无法打印
     */
    public static void setNoneLog() {
        level = NONE;
    }

    //下面的这些传入自定义TAG进行打印
    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg == null ? "null-object" : msg);
        }
    }
    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg == null ? "null-object" : msg);
        }
    }
    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Log.i(tag, msg == null ? "null-object" : msg);
        }
    }
    public static void w(String tag, String msg) {
        if (level <= WARN) {
            Log.w(tag, msg == null ? "null-object" : msg);
        }
    }
    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg == null ? "null-object" : msg);
        }
    }

    //下面这些使用预设的TAG进行打印
    public static void v(String msg) {
        LogUtil.v(TAG, msg);
    }
    public static void d(String msg) {
        LogUtil.d(TAG, msg);
    }
    public static void i(String msg) {
        LogUtil.i(TAG, msg);
    }
    public static void w(String msg) {
        LogUtil.w(TAG, msg);
    }
    public static void e(String msg) {
        LogUtil.e(TAG, msg);
    }
}
