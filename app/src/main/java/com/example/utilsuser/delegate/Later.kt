package com.example.utilsuser.delegate

import java.util.LinkedList
import kotlin.reflect.KProperty

//这个顶层函数的功能，就是将创建对象的过程放到了函数体中，这样用户使用的时候直接用later函数就可以了
fun <T> later(block: () -> T) : Later<T>{
    return Later(block)
}

class Later<T>(val block: () -> T) {
    var value: Any? = null

    operator fun getValue(any: Any?, prop: KProperty<*>): T {
        if (value == null) {
            value = block()
        }
        return value as T
    }
}

