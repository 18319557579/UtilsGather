package com.example.utilsuser.qihang.seven

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

class LinearGradientView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet){
    private val mPaint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val colors = intArrayOf(0xffff0000.toInt(),
            0xff00ff00.toInt(), 0xff0000ff.toInt(), 0xffffff00.toInt(), 0xff00ffff.toInt()
        )
        val pos = floatArrayOf(0f, 0.2f, 0.4f, 0.6f, 1.0f)
        val multiGradient = LinearGradient(0f, 0f, width / 2f, height / 2f,
            colors, pos, Shader.TileMode.MIRROR)
        mPaint.setShader(multiGradient)
        mPaint.textSize = 50f
        canvas.drawText("欢迎关注旗舰的blog", 0f, 200f, mPaint)

    }
}