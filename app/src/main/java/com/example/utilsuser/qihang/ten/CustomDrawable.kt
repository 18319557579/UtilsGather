package com.example.utilsuser.qihang.ten

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import com.example.utilsgather.logcat.LogUtil

class CustomDrawable(val mBitmap: Bitmap) : Drawable() {
    private val mPaint = Paint().apply {
        isAntiAlias = true
    }
    private lateinit var bitmapShader: BitmapShader
    private lateinit var mBound: RectF  //代表显示的区域

    override fun draw(canvas: Canvas) {
        //将bitmap用着色器展示出来
        canvas.drawRoundRect(mBound, 20f, 20f, mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
        LogUtil.d("回调setAlpha $alpha")
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
        LogUtil.d("回调setColorFilter $colorFilter")
    }

    override fun getOpacity(): Int {
        LogUtil.d("回调getOpacity")
        return PixelFormat.TRANSLUCENT
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        LogUtil.d("回调setBounds left:$left, top:$top, right:$right, bottom:$bottom")

        bitmapShader = BitmapShader(Bitmap.createScaledBitmap(mBitmap, right - left, bottom - top, true),
            Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mPaint.shader = bitmapShader

        mBound = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }

    override fun getIntrinsicWidth(): Int {
        LogUtil.d("回调getIntrinsicWidth：${mBitmap.width}")
        return super.getIntrinsicWidth()
    }

    override fun getIntrinsicHeight(): Int {
        LogUtil.d("回调getIntrinsicHeight：${mBitmap.height}")
//        return mBitmap.height
        return super.getIntrinsicHeight()
    }
}