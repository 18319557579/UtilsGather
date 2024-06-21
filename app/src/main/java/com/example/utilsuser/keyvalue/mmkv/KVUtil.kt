package com.example.utilsuser.keyvalue.mmkv

import com.example.utilsgather.logcat.LogUtil
import com.tencent.mmkv.MMKV

object KVUtil {
    enum class Model(val str: String) {
        USER("user"),
        SETTINGS("settings")
    }

     fun <T> encode(model: Model, key: String, value: T) {
        val mmkv = MMKV.mmkvWithID(model.str)

        when (value) {
            is Int -> mmkv.encode(key, value)
            is Long -> mmkv.encode(key, value)
            is Float -> mmkv.encode(key, value)
            is Double -> mmkv.encode(key, value)
            is Boolean -> mmkv.encode(key, value)
            is String -> mmkv.encode(key, value)
        }
    }

    //由于直接使用defValue来判断类型，因此不用泛型实化，但造成的后果就是defValue是必传的了
    fun <T> decode(model: Model, key: String, defValue: T?) : T {
        val mmkv = MMKV.mmkvWithID(model.str)

        LogUtil.d("是否为空：${defValue == null}")

        return when(defValue) {
            /*is Int -> {
                if (defValue == null) {
                    mmkv.decodeInt(key) as T
                } else {
                    mmkv.decodeInt(key, defValue) as T
                }
            }*/

            is Int -> mmkv.decodeInt(key, defValue) as T
            is Long -> mmkv.decodeLong(key, defValue) as T
            is Float -> mmkv.decodeFloat(key, defValue) as T
            is Double -> mmkv.decodeDouble(key, defValue) as T
            is Boolean -> mmkv.decodeBool(key, defValue) as T
            is String -> mmkv.decodeString(key, defValue) as T
            else -> defValue as T
        }
    }
}
