package com.example.utilsuser.application;


import androidx.annotation.Nullable;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.example.utilsuser.BuildConfig;

import com.elvishew.xlog.interceptor.BlacklistTagsFilterInterceptor;

import java.util.HashMap;
import java.util.Map;

public class XLogGlobal {
    private XLogGlobal(){}

    private static class HolderClass {
        private final static XLogGlobal instance = new XLogGlobal();
    }

    public static XLogGlobal getInstance() {
        return HolderClass.instance;
    }

    private Map<String, Logger> loggerMap = new HashMap<>();

    public static Logger logger(String xLogConstant) {
        return getInstance().loggerMap.get(xLogConstant);
    }

    public void init() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL: LogLevel.NONE)
                .tag(XLogConstant.GLOBAL_TAG)
                .build();
        Printer androidPrinter = new AndroidPrinter(true);
        XLog.init(config, androidPrinter);

        createIndependentLogger(XLogConstant.COPY_FUNCTION, builder -> builder.disableBorder()
                .disableThreadInfo()
                .enableStackTrace(1)
                .addInterceptor(new BlacklistTagsFilterInterceptor()));

        createIndependentLogger(XLogConstant.BLACK_FUNCTION);

    }

    private void createIndependentLogger(String tagName) {
        createIndependentLogger(tagName, null);
    }

    private void createIndependentLogger(String tagName, @Nullable CustomLogger customLogger) {
        Logger.Builder builder = XLog.tag(XLogConstant.GLOBAL_TAG + "-" + tagName);
        if (customLogger != null) {
            customLogger.custom(builder);
        }
        loggerMap.put(tagName, builder.build());
    }
    private interface CustomLogger {
        void custom(Logger.Builder builder);
    }

}
