package com.example.utilsuser.xlog;


import androidx.annotation.Nullable;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.ClassicFlattener;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy2;
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
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

    private final Map<String, Logger> loggerMap = new HashMap<>();

    public static Logger logger(String xLogConstant) {
        return getInstance().loggerMap.get(xLogConstant);
    }

    // 在Application中调用，以进行初始化
    public static void init() {
        getInstance().innerInit();
    }

    private void innerInit() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL: LogLevel.NONE)
                .tag(XLogConstant.GLOBAL_TAG)
                .build();
        Printer androidPrinter = new AndroidPrinter(true);
        Printer filePrinter = new FilePrinter
                // 日志文件的存储路径。这里设置的是内部存储路径
                .Builder(XLogConstant.getFolderPath())
                // 设置文件名生成规则。这里设置每天有不同的文件名，用当天的日期进行作为文件名
                .fileNameGenerator(new DateFileNameGenerator())
                // 自定义每条数据的格式。这里使用格式化后的日期名称
                .flattener(new ClassicFlattener())
                // 其实给了这些功能的设置开放：1：备份的条件 2：备份的文件名
                // 不过，作者封装了很多类用于方便使用，导致会误以为开放了最大index这样的接口，其实也只是备份的文件名的一部分罢了
                .backupStrategy(new FileSizeBackupStrategy2(
                        XLogConstant.DEFAULT_LOG_FILE_MAX_SIZE,
                        FileSizeBackupStrategy2.NO_LIMIT))
                .cleanStrategy(new FileLastModifiedCleanStrategy(XLogConstant.MILL_SECONDS_IN_A_WEEK))
                .build();

        XLog.init(config,
                androidPrinter,
                filePrinter);

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
