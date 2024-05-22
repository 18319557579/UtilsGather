package com.example.utilsuser.qihang.eight

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import com.example.utilsuser.R

class TwitterView : View {

    private val mBitPaint: Paint
    private val bmpDST: Bitmap
    private val bmpSRC: Bitmap
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        mBitPaint = Paint()
        bmpDST = BitmapFactory.decodeResource(resources, R.drawable.twiter_bg, null)
        bmpSRC = BitmapFactory.decodeResource(resources, R.drawable.twiter_light, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        canvas.drawBitmap(bmpDST, 0f, 0f, mBitPaint)
        mBitPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
        canvas.drawBitmap(bmpSRC, 0f, 0f, mBitPaint)
        mBitPaint.xfermode = null
        canvas.restoreToCount(layerId)
    }
}