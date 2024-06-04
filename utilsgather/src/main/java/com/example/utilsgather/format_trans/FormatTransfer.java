package com.example.utilsgather.format_trans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatTransfer {
    public static String byteFormat(long bytes) {
        BigDecimal fileSize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = fileSize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
        if (returnValue > 1) {
            return returnValue + "MB";
        }
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = fileSize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue();
        return returnValue + "KB";
    }

    /**
     * 格式化时间戳
     */
    public static String timeStampFormat(long timeStamp) {
        return timeFormat("yyyy-MM-dd HH:mm:ss", timeStamp);
    }

    /**
     * 自定义格式化，然后格式化时间戳
     */
    public static String timeFormat(String pattern, long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = new Date(timeStamp);
        return formatter.format(date);
    }
}
