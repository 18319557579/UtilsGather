package com.example.utilsuser.keyvalue.sp

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SPDelegate<T> (
    private val context: Context,
    private val key: String,
    private val defaultValue: T? = null,  // 该默认值只有在读取的时候才会使用
    private val spFile: SPUtil.Model = SPUtil.Model.COMMON

) : ReadWriteProperty<Any?, T>{

    private val sp: SharedPreferences by lazy {
        context.getSharedPreferences(spFile.str, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return SPUtil.get(context, spFile, key, defaultValue)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        SPUtil.put(context, spFile, key, value)
    }
}