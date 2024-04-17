package com.example.utilsuser.qihang.one.RectPointView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.utilsuser.R

class CustomCircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private lateinit var mBmp: Bitmap
    private lateinit var mPaint: Paint
    private lateinit var mPath: Path

    init {
        mBmp = BitmapFactory.decodeResource(resources, R.drawable.avator)
        mPaint = Paint()
        mPath = Path()
        val width = mBmp.width
        val height = mBmp.height
        mPath.addCircle(width / 2f, height / 2f, width / 2f, Path.Direction.CCW)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.clipPath(mPath)
        canvas.drawBitmap(mBmp, 0f, 0f, mPaint)
        canvas.restore()
    }

}