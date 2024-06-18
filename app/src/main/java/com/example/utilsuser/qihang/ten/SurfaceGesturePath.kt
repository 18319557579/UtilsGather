package com.example.utilsuser.qihang.ten

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import com.example.utilsgather.logcat.LogUtil
import kotlin.concurrent.thread

class SurfaceGesturePath : SurfaceView {
    private lateinit var mPaint: Paint
    private lateinit var mPath: Path

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setWillNotDraw(false)

        mPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 5f
            color = Color.RED
        }

        mPath = Path()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        if (event.action == MotionEvent.ACTION_DOWN) {
            mPath.moveTo(x, y)
            LogUtil.d("ACTION_DOWN")
            return true
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            mPath.lineTo(x, y)
        }
        drawCanvas()
        LogUtil.d("invalidate")

        return super.onTouchEvent(event)
    }


    private fun drawCanvas() {
        thread {
            val surfaceHolder = holder
            val canvas = surfaceHolder.lockCanvas()

            canvas.drawPath(mPath, mPaint)

            surfaceHolder.unlockCanvasAndPost(canvas)
        }


    }
}