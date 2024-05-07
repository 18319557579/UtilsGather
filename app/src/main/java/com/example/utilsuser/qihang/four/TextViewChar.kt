package com.example.utilsuser.qihang.four

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class TextViewChar : androidx.appcompat.widget.AppCompatTextView {


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setCharText(character: Char) {
        text = character.toString()
    }

    fun CharText(character: Char) {
        text = character.toString()
    }
}