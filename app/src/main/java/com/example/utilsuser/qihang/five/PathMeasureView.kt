package com.example.utilsuser.qihang.five

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import kotlin.math.abs

class PathMeasureView : View {
    var mCurAnimValue = 0f
    var mPaint: Paint
    var mDstPath: Path
    var mPathMeasure: PathMeasure

    var mArrawBmp: Bitmap

    val pos = FloatArray(2)
    val tan = FloatArray(2)

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

        mArrawBmp = BitmapFactory.decodeResource(resources, R.drawable.arraw)

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

        mPathMeasure.getPosTan(stop, pos, tan)
        LogUtil.d("打印一下pos: ${pos[0]}, ${pos[1]}")
        LogUtil.d("打印一下tan: ${tan[0]}, ${tan[1]}")
        val degress = Math.atan2(tan[1].toDouble(), tan[0].toDouble()) * 180.0f / Math.PI

        /*val matrix = Matrix().apply {
            postRotate(degress.toFloat(), mArrawBmp.width / 2f, mArrawBmp.height / 2f)
            postTranslate(pos[0] - mArrawBmp.width, pos[1] - mArrawBmp.height / 2)
        }
        canvas.drawBitmap(mArrawBmp, matrix, mPaint)*/
        val matrix = Matrix();
        mPathMeasure.getMatrix(stop, matrix, PathMeasure.POSITION_MATRIX_FLAG or PathMeasure.TANGENT_MATRIX_FLAG)
        matrix.preTranslate(-mArrawBmp.width / 2f, -mArrawBmp.height / 2f)

        canvas.drawBitmap(mArrawBmp, matrix, mPaint)
    }
}