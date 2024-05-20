package com.example.utilsuser.qihang.seven

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PathIterator
import android.graphics.Rect
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.utilsuser.R

class TelescopeView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val mPaint: Paint
    private val mBitmap: Bitmap
    private lateinit var mBitmapBG: Bitmap

    private var mDx = -1f
    private var mDy = -1f

    init {
        mPaint = Paint()
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.scenery)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mDx = event.x
                mDy = event.y
                postInvalidate()
                return true
            }
            MotionEvent.ACTION_MOVE ->{
                mDx = event.x
                mDy = event.y
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mDx = -1f
                mDy = -1f
            }
        }
        postInvalidate()
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (::mBitmapBG.isLateinit) {
            mBitmapBG = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvasbg = Canvas(mBitmapBG)
            canvasbg.drawBitmap(mBitmap, null, Rect(0, 0, width, height), mPaint)
        }
        if (mDx != -1f && mDy != -1f) {
            mPaint.setShader(BitmapShader(mBitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT))
            canvas.drawCircle(mDx, mDy, 150f, mPaint)
        }
    }
}