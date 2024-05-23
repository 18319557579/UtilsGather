package com.example.utilsuser.qihang.eight

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class PorterDuffXfermodeView : View{

    private val mWidth = 400
    private val mHeight = 400
    private val dstBmp: Bitmap
    private val srcBmp: Bitmap
    private val mPaint: Paint

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        setLayerType(LAYER_TYPE_SOFTWARE, null)

        dstBmp = makeDst(mWidth, mHeight)
        srcBmp = makeSrc(mWidth, mHeight)
        mPaint = Paint()
    }

    private fun makeDst(w: Int, h: Int) : Bitmap{
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = 0xFFFFCC44.toInt()
        c.drawOval(RectF(0f, 0f, w.toFloat(), h.toFloat()), p)
        return bm
    }

    private fun makeSrc(w: Int, h: Int) : Bitmap {
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = 0xFF66AAFF.toInt()
        c.drawRect(0f, 0f, w.toFloat(), h.toFloat(), p)
        return bm
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.GREEN)

//        val layerId = canvas.saveLayer(0f, 0f, mWidth.toFloat() * 2,
//            mHeight.toFloat() * 2, mPaint, Canvas.ALL_SAVE_FLAG)

        canvas.drawBitmap(dstBmp, 0f, 0f, mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(srcBmp, mWidth / 2f, mHeight / 2f, mPaint)
        mPaint.setXfermode(null)

//        canvas.restoreToCount(layerId)
    }
}