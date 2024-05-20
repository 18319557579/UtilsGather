package com.example.utilsuser.qihang.seven

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BlurView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val mPaint: Paint

    init {
        mPaint = Paint().apply {
            color = Color.BLACK
            setMaskFilter(BlurMaskFilter(50f, BlurMaskFilter.Blur.OUTER))
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(200f, 200f, 100f, mPaint)
    }
}