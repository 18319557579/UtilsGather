package com.example.utilsgather.qihang.one.RectPointView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class CustomCircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private lateinit var mBmp: Bitmap
    private lateinit var mPaint: Paint
    private lateinit var mPath: Path

    init {
//        mBmp = BitmapFactory.decodeResource(resources, R.drawable.avator)
    }

}