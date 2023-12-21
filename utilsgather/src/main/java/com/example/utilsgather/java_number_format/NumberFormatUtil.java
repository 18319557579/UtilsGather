package com.example.utilsgather.java_number_format;

import java.text.DecimalFormat;

public class NumberFormatUtil {
    /**
     * 将一个数字保留两位小数后，返回字符串。如果小数不足两位，用0来补齐
     */
    private static String keepTwoDecimals(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String result = decimalFormat.format(number);
        return result;
    }
}
