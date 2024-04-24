package com.example.utilsuser.qihang.one.six

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.example.utilsuser.R

class DogView : View {
    private val mPaint= Paint()
    private var bitmap: Bitmap

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mPaint.isAntiAlias = true
        bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.avator)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*val colorMatrix = ColorMatrix(floatArrayOf(
            1f, 0f, 0f, 0f, 0f,
            0f,1f, 0f, 0f, 0f,
            0f, 0f, 1f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        ))*/
        /*val colorMatrix = ColorMatrix(floatArrayOf(
            0.213f, 0.715f, 0.072f, 0f, 0f,
            0.213f, 0.715f, 0.072f, 0f, 0f,
            0.213f, 0.715f, 0.072f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        ))*/
        /*val colorMatrix = ColorMatrix(floatArrayOf(
            0.333f, 0.333f, 0.333f, 0f, 0f,
            0.333f, 0.333f, 0.333f, 0f, 0f,
            0.333f, 0.333f, 0.333f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        ))*/
        /*val colorMatrix = ColorMatrix(floatArrayOf(
            0f, 1f, 0f, 0f, 0f,
            1f, 0f, 0f, 0f, 0f,
            0f, 0f, 1f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        ))*/
        /*val colorMatrix = ColorMatrix(floatArrayOf(
            1/2f, 1/2f, 1/2f, 0f, 0f,
            1/3f, 1/3f, 1/3f, 0f, 0f,
            1/4f, 1/4f, 1/4f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        ))*/

        val colorMatrix = ColorMatrix().apply {
//            setSaturation(20f)
            setScale(1f, 1.3f, 1f, 1f)
        }
        mPaint.setColorFilter(ColorMatrixColorFilter(colorMatrix))

        canvas.drawBitmap(bitmap, null,
            Rect(0, 0, 500, 500 * bitmap.height / bitmap.width), mPaint)


    }

}