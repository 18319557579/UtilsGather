package com.example.utilsuser.qihang.nine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.utilsuser.R

class SaveLayerUseExample : View {
    private val mPaint: Paint
    private val mBitmap: Bitmap

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        mPaint = Paint().apply {
//            color = Color.GRAY
        }
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.dog)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /*canvas.drawBitmap(mBitmap, 0f, 0f, mPaint)

        val layerID = canvas.saveLayerAlpha(0f, 0f, 200f, 200f, 255, Canvas.ALL_SAVE_FLAG)
        canvas.drawColor(Color.WHITE)

        canvas.restoreToCount(layerID)*/

        /*canvas.save()
        canvas.rotate(40f)
        canvas.drawRect(100f, 0f, 200f, 100f, mPaint)
        canvas.restore()

        mPaint.color = Color.BLACK
        canvas.drawRect(100f, 0f, 200f, 100f, mPaint)*/

        canvas.drawColor(Color.GRAY)

        canvas.saveLayer(0f, 0f, 300f, 300f, mPaint, Canvas.ALL_SAVE_FLAG)
        mPaint.color = Color.BLACK

        canvas.drawRect(100f, 100f, 200f, 200f, mPaint)

        canvas.restore()

    }
}