package com.example.utilsuser.qihang.seven

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.example.utilsuser.R

class ShadowView : View {
    private val mPaint= Paint()
    private val mDogBmp: Bitmap

    var mSetShow = true

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mPaint.apply {
            color = Color.BLACK
            textSize = 25f
//            setShadowLayer(1f, 10f, 10f, Color.GRAY)
            mDogBmp = BitmapFactory.decodeResource(resources, R.drawable.avator)
        }
    }

    fun setShadow(showShadow: Boolean) {
        mSetShow = showShadow
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mSetShow) {
            mPaint.setShadowLayer(1f, 10f, 10f, Color.GRAY)
        } else {
            mPaint.clearShadowLayer()
        }

        canvas.apply {
            drawColor(Color.WHITE)
            drawText("旗舰", 100f, 100f, mPaint)
            drawCircle(300f, 100f, 50f, mPaint)
            drawBitmap(mDogBmp, null, Rect(500, 50, 500 + mDogBmp.width, 50 + mDogBmp.height), mPaint)
        }
    }
}