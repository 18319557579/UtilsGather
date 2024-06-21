package com.example.utilsuser.delegate

import kotlin.reflect.KProperty

class Delegate {
    var proValue: Any? = null

    operator fun getValue(myClass: MyClass, prop: KProperty<*>) : Any? {
        return proValue
    }

    operator fun setValue(myClass: MyClass, prop: KProperty<*>, value: Any?) {
        proValue = value
    }
}