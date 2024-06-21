package com.example.utilsuser.keyvalue.mmkv

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class KVDelegate<T>(
    private val key: String,
    private val defValue: T? = null,  //defValue只有get的时候会用到
    private val model: KVUtil.Model = KVUtil.Model.USER  //默认为USER，用户可以手动传不同的Model
) : ReadWriteProperty<Any?, T>{
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return KVUtil.decode(model, key, defValue)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        KVUtil.encode(model, key, value)
    }
}