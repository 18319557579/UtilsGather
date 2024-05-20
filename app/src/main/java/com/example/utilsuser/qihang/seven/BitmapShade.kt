package com.example.utilsuser.qihang.seven

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.example.utilsuser.R

class BitmapShade(context: Context, attributeSet: AttributeSet)
    : View(context, attributeSet) {

        private val mPaint: Paint
        private val mBitmap: Bitmap
        private val mAlphaBmp: Bitmap

        init {
            setLayerType(LAYER_TYPE_SOFTWARE, null)

            mPaint = Paint()
            mBitmap = BitmapFactory.decodeResource(resources, R.drawable.cat_dog)
            mAlphaBmp = mBitmap.extractAlpha()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = 200
        val height = width * mAlphaBmp.width / mAlphaBmp.height

        mPaint.color = Color.GRAY
        mPaint.setMaskFilter(BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL))
        canvas.drawBitmap(mAlphaBmp, null, Rect(10, 10, width, height), mPaint)

        /*canvas.translate(width.toFloat(), 0f)
        mPaint.color = Color.BLACK
        canvas.drawBitmap(mAlphaBmp, null, Rect(10, 10, width, height), mPaint)
*/

        canvas.translate(-5f, -5f)
        mPaint.setMaskFilter(null)
        canvas.drawBitmap(mBitmap, null, Rect(0, 0, width, height), mPaint)
    }
}