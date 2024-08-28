package com.example.uioperate.custom_juejin_newki

import android.icu.text.DecimalFormat

object CircleUtil {
    /**
     * 将double格式化为指定小数位的String，不足小数位用0补全
     * @param v     需要格式化的数字
     * @param scale 小数点后保留几位
     */
    fun roundByScale(v: Double, scale: Int): String {
        if (scale < 0) {
            throw IllegalArgumentException("参数错误，必须设置大于0的数字")
        }
        if (scale == 0) {
            return DecimalFormat("0").format(v)
        }

        var formatStr = "0."
        for (i in 0 until scale) {
            formatStr += "0"
        }
        return DecimalFormat(formatStr).format(v)
    }
}