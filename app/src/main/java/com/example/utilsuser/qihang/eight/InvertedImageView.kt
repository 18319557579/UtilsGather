package com.example.utilsuser.qihang.eight

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.utilsuser.R

class InvertedImageView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val mBitPaint: Paint
    private val bmpDST: Bitmap
    private val bmpSRC: Bitmap
    private val bmpRevert : Bitmap
    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        mBitPaint = Paint()
        bmpDST = BitmapFactory.decodeResource(resources, R.drawable.dog_invert_shade, null)
        bmpSRC = BitmapFactory.decodeResource(resources, R.drawable.dog, null)

        val matrix = Matrix().apply {
            setScale(1f, -1f)  //翻转
        }
        bmpRevert = Bitmap.createBitmap(bmpSRC, 0, 0, bmpSRC.width, bmpSRC.height, matrix, true)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val theWidth = width / 2f
        val theHeight = (theWidth * bmpDST.height / bmpDST.width).toFloat()
        canvas.drawBitmap(bmpSRC, null, RectF(0f, 0f, theWidth, theHeight), mBitPaint)

        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        canvas.translate(0f, theHeight)

        canvas.drawBitmap(bmpDST, null, RectF(0f, 0f, theWidth, theHeight), mBitPaint)
        mBitPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bmpRevert, null, RectF(0f, 0f, theWidth, theHeight), mBitPaint)

        mBitPaint.xfermode = null
        canvas.restoreToCount(layerId)

    }
}