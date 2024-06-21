package com.example.utilsuser.delegate

import kotlin.reflect.KProperty

class Delegate2 {
    var proValue: Int = 0

    operator fun getValue(myClass: MyClass, prop: KProperty<*>) : String {
        return proValue.toString()
    }

    operator fun setValue(myClass: MyClass, prop: KProperty<*>, value: String) {
        proValue = value.toInt()
    }
}