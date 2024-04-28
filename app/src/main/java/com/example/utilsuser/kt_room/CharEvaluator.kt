package com.example.utilsuser.kt_room

import android.animation.TypeEvaluator

class CharEvaluator : TypeEvaluator<Char> {
    override fun evaluate(fraction: Float, startValue: Char, endValue: Char): Char {
        val startint = startValue.code
        val endint = endValue.code
        val curint = (startint + fraction * (endint - startint)).toInt()
        val result = curint.toChar()
        return result
    }
}
