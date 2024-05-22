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

class LightBookView : View {
    private val mBitPaint: Paint
    private val BmpDST: Bitmap
    private val BmpSRC: Bitmap
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mBitPaint = Paint()
        BmpDST = BitmapFactory.decodeResource(resources, R.drawable.book_bg, null)
        BmpSRC = BitmapFactory.decodeResource(resources, R.drawable.book_light, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val layoutId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)

        canvas.drawBitmap(BmpDST, 0f, 0f, mBitPaint)
        mBitPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.LIGHTEN)
        canvas.drawBitmap(BmpSRC, 0f, 0f, mBitPaint)

        mBitPaint.xfermode = null
        canvas.restoreToCount(layoutId)
    }
}