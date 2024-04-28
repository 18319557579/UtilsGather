package com.example.utilsuser.qihang.two

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.widget.ImageView

class FallingBallImageView : androidx.appcompat.widget.AppCompatImageView {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    public fun setFallingPos(pos: Point) {
        layout(pos.x, pos.y, pos.x + width, pos.y + height)
    }

}