package com.example.utilsuser.qihang.eight

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.utilsuser.R
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue.Value

class TextWave_DSTIN : View {
    private val mPaint: Paint
    private val mPath: Path
    private val mItemWaveLength = 1000
    private var dx = 0

    private val bmpSRC: Bitmap
    private val bmpDST: Bitmap

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mPath = Path()

        mPaint = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.FILL_AND_STROKE
        }

        bmpSRC = BitmapFactory.decodeResource(resources, R.drawable.text_shade, null)
        bmpDST = Bitmap.createBitmap(bmpSRC.width, bmpSRC.height, Bitmap.Config.ARGB_8888)

        startAnim()
    }

    private fun startAnim() {
        ValueAnimator.ofInt(0, mItemWaveLength).apply {
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                dx = it.animatedValue as Int
                postInvalidate()
            }
            start()
        }
    }

    private fun generateWavePath() {
        mPath.reset()
        val originY = bmpSRC.height / 2f
        val halfWaveLen = mItemWaveLength / 2f
        mPath.moveTo((-mItemWaveLength + dx).toFloat(), originY)
        for (i in -mItemWaveLength .. width + mItemWaveLength step mItemWaveLength) {
            mPath.rQuadTo(halfWaveLen / 2, -50f, halfWaveLen, 0f)
            mPath.rQuadTo(halfWaveLen / 2, 50f,  halfWaveLen, 0f)
        }
        mPath.lineTo(bmpSRC.width.toFloat(), bmpSRC.height.toFloat())
        mPath.lineTo(0f, bmpSRC.height.toFloat())
        mPath.close()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        generateWavePath()

        val c = Canvas(bmpDST)
        c.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR)
        c.drawPath(mPath, mPaint)

        canvas.drawBitmap(bmpSRC, 0f, 0f, mPaint)

        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        canvas.drawBitmap(bmpDST, 0f, 0f, mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawBitmap(bmpSRC, 0f, 0f, mPaint)

        mPaint.xfermode = null
        canvas.restoreToCount(layerId)
    }
}