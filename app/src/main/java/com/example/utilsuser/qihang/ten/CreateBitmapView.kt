package com.example.utilsuser.qihang.ten

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import com.example.utilsgather.logcat.LogUtil

class CreateBitmapView : View{
    private val mDestBmp: Bitmap
    private val mPaint: Paint

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mPaint = Paint()

        val width = 500
        val height = 300
        mDestBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(mDestBmp)

        val linearGradient = LinearGradient(width / 2f, 0f, width / 2f, height.toFloat(),
            0xffffffff.toInt(), 0x00ffffff.toInt(), Shader.TileMode.CLAMP)
        val paint = Paint().apply {
            shader = linearGradient

        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 55f
        }
        canvas.drawBitmap(mDestBmp, 0f, 0f, mPaint)

        LogUtil.d("重新编过")
    }
}