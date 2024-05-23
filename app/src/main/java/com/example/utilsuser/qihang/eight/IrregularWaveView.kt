package com.example.utilsuser.qihang.eight

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.utilsuser.R

class IrregularWaveView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val mPaint: Paint
    private var mItemWaveLength = 0
    private var dx = 0
    private val bmpSRC: Bitmap
    private val bmpDST: Bitmap

    init {
        mPaint = Paint().apply {
            isAntiAlias = true
        }

        bmpDST = BitmapFactory.decodeResource(resources, R.drawable.wave_bg, null)
        bmpSRC = BitmapFactory.decodeResource(resources, R.drawable.circle_shape, null)
        mItemWaveLength = bmpDST.width

        startAnim()
    }

    private fun startAnim() {
        ValueAnimator.ofInt(0, mItemWaveLength - bmpSRC.width).apply {
            duration = 4000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                dx = it.getAnimatedValue() as Int
                postInvalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bmpSRC, 0f, 0f, mPaint)

        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)

        canvas.drawBitmap(bmpDST, Rect(dx, 0, dx + bmpSRC.width, bmpSRC.height),
            Rect(0, 0, bmpSRC.width, bmpSRC.height), mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawBitmap(bmpSRC, 0f, 0f, mPaint)

        mPaint.xfermode = null
        canvas.restoreToCount(layerId)

    }
}