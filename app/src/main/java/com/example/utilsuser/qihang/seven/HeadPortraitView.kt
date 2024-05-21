package com.example.utilsuser.qihang.seven

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import com.example.utilsuser.R

class HeadPortraitView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val mPaint : Paint
    private val mBitmap: Bitmap
    private val mBitmapShader: BitmapShader

    init {
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.avator)
        mPaint = Paint()
        mBitmapShader = BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val matrix = Matrix()
        val scale = width.toFloat() / mBitmap.width
        matrix.setScale(scale, scale)
        mBitmapShader.setLocalMatrix(matrix)
        mPaint.setShader(mBitmapShader)

        val half = width / 2f
        canvas.drawCircle(half, half, width / 2f, mPaint)
    }
}