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
import kotlin.math.abs

class PathMeasureView : View {
    var mCurAnimValue = 0f
    var mPaint: Paint
    var mDstPath: Path
    var mPathMeasure: PathMeasure

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        mPaint = Paint().apply {
            color = Color.BLACK
            strokeWidth = 4f
            style = Paint.Style.STROKE
        }

        mDstPath = Path()
        val mCirclePath = Path().apply {
            addCircle(100f, 100f, 50f, Path.Direction.CW)
        }

        mPathMeasure = PathMeasure(mCirclePath, false)

        ValueAnimator.ofFloat(0f, 1f).apply {
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                mCurAnimValue = it.animatedValue as Float
                invalidate()
            }
            duration = 2000
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)

        val length = mPathMeasure.length
        val stop = length * mCurAnimValue
//        val start = (stop - ((0.5 - abs(mCurAnimValue - 0.5)) * length)).toFloat()

        var start = 0f;
        if (mCurAnimValue >= 0.5f) {
            start = 2 * mCurAnimValue - 1;
        }
        start *= length;

        mDstPath.reset()
        mPathMeasure.getSegment(start, stop, mDstPath, true)
        canvas.drawPath(mDstPath, mPaint)
    }
}