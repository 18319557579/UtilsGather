package com.example.utilsuser.qihang.seven

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.Xfermode
import android.util.AttributeSet
import android.widget.TextView

class ShimmerTextView(context: Context, attributeSet: AttributeSet) : androidx.appcompat.widget.AppCompatTextView(context, attributeSet) {
    private var mDx = 0
    private lateinit var mLinearGradient: LinearGradient
    private val mPaint: Paint

    init {
        mPaint = paint
        val length = mPaint.measureText(text.toString())
        createAnim(length.toInt())
        createLinearGradient(length.toInt())
    }

    private fun createAnim(length: Int) {
        ValueAnimator.ofInt(0, 2 * length).apply {
            addUpdateListener {
                mDx = it.getAnimatedValue() as Int
                postInvalidate()
            }
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            duration = 2000
            start()
        }
    }

    private fun createLinearGradient(length: Int) {
        mLinearGradient = LinearGradient(-length.toFloat(), 0f, 0f, 0f,
            intArrayOf(currentTextColor, 0xff00ff00.toInt(), currentTextColor),
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        val matrix = Matrix().apply {
            setTranslate(mDx.toFloat(), 0f)
        }
        mLinearGradient.setLocalMatrix(matrix)
        mPaint.shader = mLinearGradient

        super.onDraw(canvas)
    }
}