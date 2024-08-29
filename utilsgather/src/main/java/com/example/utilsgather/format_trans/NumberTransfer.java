package com.example.utilsgather.format_trans;

import java.text.DecimalFormat;

public class NumberTransfer {
    /**
     * 将double格式化为指定小数位的String，不足小数位用0补全
     * @param v     需要格式化的数字
     * @param scale 小数点后保留几位
     */
    public static String roundByScale(double v, int scale) {
        if (scale < 0)
            throw new IllegalArgumentException("参数错误，必须设置大于0的数字");
        if (scale == 0) {
            return new DecimalFormat("0").format(v);
        }

        StringBuilder formatStr = new StringBuilder("0.");
        for (int i = 0; i < scale; i++) {
            formatStr.append("0");
        }
        return new DecimalFormat(formatStr.toString()).format(v);
    }
}
