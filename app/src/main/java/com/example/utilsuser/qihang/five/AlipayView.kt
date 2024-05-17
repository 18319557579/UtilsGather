package com.example.utilsuser.qihang.five

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.example.utilsgather.logcat.LogUtil

class AlipayView : View {
    var mCurAnimValue = 0f
    var mPaint: Paint
    var mDstPath: Path
    var mCirclePath: Path
    var mPathMeasure: PathMeasure

    val mCentX= 200f
    val mCentY = 200f
    val mRadius = 100f

    var hasChange = false

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mPaint = Paint().apply {
            color = Color.BLACK
            strokeWidth = 4f
            style = Paint.Style.STROKE
        }

        mDstPath = Path()
        mCirclePath = Path().apply {
            addCircle(mCentX, mCentY, mRadius, Path.Direction.CW)
            moveTo(mCentX - mRadius / 2, mCentY)
            lineTo(mCentX, mCentY + mRadius / 2)
            lineTo(mCentX + mRadius / 2, mCentY - mRadius / 3)
        }

        mPathMeasure = PathMeasure(mCirclePath, false)

        ValueAnimator.ofFloat(0f, 2f).apply {
            addUpdateListener {
                mCurAnimValue = it.getAnimatedValue() as Float
                invalidate()
            }
            duration = 4000
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)

        if (mCurAnimValue < 1) {
            val stop = mPathMeasure.length * mCurAnimValue
            mPathMeasure.getSegment(0f, stop, mDstPath, true)
            LogUtil.d("当前的mCurAnimValue: $mCurAnimValue, stop: $stop")

        } else if(hasChange == false){

            mPathMeasure.getSegment(0f, mPathMeasure.length, mDstPath, true)
            LogUtil.d("超过1了，开始转换。当前的mCurAnimValue: $mCurAnimValue, stop: ${mPathMeasure.length}")
            mPathMeasure.nextContour()

            hasChange = true
        } else {
            val stop = mPathMeasure.length * (mCurAnimValue - 1)
            mPathMeasure.getSegment(0f, stop, mDstPath, true)
            LogUtil.d("当前的mCurAnimValue: $mCurAnimValue, stop: $stop")
        }
        canvas.drawPath(mDstPath, mPaint)

//        canvas.drawPath(mCirclePath, mPaint)
    }
}