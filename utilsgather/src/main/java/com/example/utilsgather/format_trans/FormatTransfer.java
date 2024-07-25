package com.example.utilsgather.format_trans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatTransfer {
    /**
     * 文件大小格式化
     * @param size long类型数据
     * @return B, KB, MB, GB, TB 结尾的字符串
     */
    public static String byteFormat(long size) {
        BigDecimal bytes = new BigDecimal(size);
        BigDecimal kilobytes = new BigDecimal(1024);
        if (bytes.compareTo(kilobytes) < 0) {
            return bytes + " B";  //Bytes
        }

        BigDecimal megabytes = kilobytes.multiply(kilobytes);
        if (bytes.compareTo(megabytes) < 0) {
            //除以KB，保留2位小数，小数四舍五入
            return bytes.divide(kilobytes, 2, RoundingMode.HALF_UP) + " KB";  //Kilobytes
        }

        BigDecimal gigabytes = kilobytes.multiply(megabytes);
        if (bytes.compareTo(gigabytes) < 0) {
            return bytes.divide(megabytes, 2, RoundingMode.HALF_UP) + " MB";  //Megabytes
        }

        BigDecimal teraByte = kilobytes.multiply(gigabytes);
        if (bytes.compareTo(teraByte) < 0) {
            return bytes.divide(gigabytes, 2, RoundingMode.HALF_UP) + " GB";  //Gigabytes
        }

        return bytes.divide(teraByte, 2, RoundingMode.HALF_UP) + " TB";  //TeraByte
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
