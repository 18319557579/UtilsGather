package com.example.utilsuser.keyvalue.sp

import android.content.Context

object SPUtil {
    enum class Model(val str: String) {
        COMMON("common"),
        PERSONAL("personal")
    }

    fun <T> put(context: Context, model: Model, key: String, value: T) {
        val sp = context.getSharedPreferences(model.str, Context.MODE_PRIVATE)
        with(sp.edit()) {
            when (value) {
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Set<*> -> putStringSet(key, value.map { it.toString() }.toHashSet())
                else -> throw IllegalStateException("Unsupported type")
            }
            apply()
        }
    }

    fun <T> get(context: Context, model: Model, key: String, defaultValue: T?) : T{
        if (defaultValue == null) {
            throw IllegalArgumentException("获取值时必须要传默认值")
        }

        val sp = context.getSharedPreferences(model.str, Context.MODE_PRIVATE)
        val getValue =  when (defaultValue) {
            is Int -> sp.getInt(key, defaultValue)
            is Long -> sp.getLong(key, defaultValue)
            is Float -> sp.getFloat(key, defaultValue)
            is Boolean -> sp.getBoolean(key, defaultValue)
            is String -> sp.getString(key, defaultValue)
            is Set<*> -> sp.getStringSet(key, defaultValue as? Set<String>)
            else -> throw IllegalStateException("Unsupported type")
        }
        return getValue as T
    }
}