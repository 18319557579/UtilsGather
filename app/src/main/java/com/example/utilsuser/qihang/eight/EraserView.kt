package com.example.utilsuser.qihang.eight

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.utilsuser.R

class EraserView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val mBitPaint: Paint
    private val bmpDST: Bitmap
    private val bmpSRC: Bitmap
    private val bmpText: Bitmap
    private val mPath: Path

    private var mPreX = 0f
    private var mPreY = 0f

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        mBitPaint = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 35f
            strokeCap = Paint.Cap.ROUND
        }

        val options = BitmapFactory.Options().apply {
            inSampleSize = 2
        }

        bmpSRC = BitmapFactory.decodeResource(resources, R.drawable.dog, options)
        bmpDST = Bitmap.createBitmap(bmpSRC.width, bmpSRC.height, Bitmap.Config.ARGB_8888)
        bmpText = BitmapFactory.decodeResource(resources, R.drawable.guaguaka_text, null)

        mPath = Path()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
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
            }
        }
        postInvalidate()
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bmpText, null, RectF(0f, 0f, bmpDST.width.toFloat(),
            bmpDST.height.toFloat()
        ), mBitPaint)

        val layerId = canvas.saveLayer(0f, 0f,
            width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)

        val c = Canvas(bmpDST)
        c.drawPath(mPath, mBitPaint)

        canvas.drawBitmap(bmpDST, 0f, 0f, mBitPaint)

        mBitPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        canvas.drawBitmap(bmpSRC, 0f, 0f, mBitPaint)

        mBitPaint.xfermode = null
        canvas.restoreToCount(layerId)

    }
}