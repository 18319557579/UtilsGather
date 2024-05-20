package com.example.utilsuser.qihang.seven

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class WaveCurve : View {
    val mPath = Path()
    var mPaint: Paint
    var mPreX = 0f
    var mPreY = 0f

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mPaint = Paint().apply {
            color = Color.RED
            strokeWidth = 5f
            style = Paint.Style.STROKE
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath.moveTo(event.x, event.y)
                mPreX = event.x
                mPreY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = (mPreX + event.x) / 2
                val endY = (mPreY + event.y) / 2
                mPath.quadTo(mPreX, mPreY, endX, endY)

                mPreX = event.x
                mPreY = event.y

//                mPath.lineTo(event.x, event.y)
                invalidate()
            }
        }

        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*val path = Path().apply {
            moveTo(100f, 300f)
            quadTo(200f, 200f, 300f, 300f)
            quadTo(400f, 400f, 500f, 300f)
        }*/

        canvas.drawColor(Color.WHITE)
        canvas.drawPath(mPath, mPaint)
    }
}