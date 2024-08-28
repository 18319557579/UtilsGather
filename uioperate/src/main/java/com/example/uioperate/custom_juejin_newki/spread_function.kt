package com.example.uioperate.custom_juejin_newki

import android.content.res.TypedArray
import androidx.annotation.StyleableRes

/**
 * 获取String的时候，如果没有定义，那么使用默认值
 */
fun TypedArray.getStringWithDefaultValue(@StyleableRes index: Int, defaultValue: String)
    = this.getString(index) ?: defaultValue