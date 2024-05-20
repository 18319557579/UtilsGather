package com.example.utilsuser.qihang.seven

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.utilsgather.logcat.LogUtil

class BillowWaveView : View {
    private var mPaint: Paint

    private var mPath: Path
    private val mItemWaveLength =  1200
    private var dx = 0


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mPath = Path()
        mPaint = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.FILL
        }

        startAnim()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPath.reset()
        val originY = 300
        val halfWaveLen = mItemWaveLength / 2
        mPath.moveTo((-mItemWaveLength + dx).toFloat(), originY.toFloat())

        LogUtil.d("打印一下宽度$width")

        for (i in -mItemWaveLength..width + mItemWaveLength step mItemWaveLength) {
            mPath.rQuadTo((halfWaveLen / 2).toFloat(), -100f, halfWaveLen.toFloat(), 0f)
            mPath.rQuadTo((halfWaveLen / 2).toFloat(), 100f, halfWaveLen.toFloat(), 0f)
        }

        mPath.lineTo(width.toFloat(), height.toFloat())
        mPath.lineTo(0f, height.toFloat())
        mPath.close()

        canvas.drawPath(mPath, mPaint)
    }

    private fun startAnim() {
        ValueAnimator.ofInt(0, mItemWaveLength).apply {
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                dx = it.getAnimatedValue() as Int
                postInvalidate()
            }
            start()
        }
    }



}