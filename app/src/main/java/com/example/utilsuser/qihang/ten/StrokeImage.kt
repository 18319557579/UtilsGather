package com.example.utilsuser.qihang.ten

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.widget.ImageView

class StrokeImage : ImageView {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onFinishInflate() {
        super.onFinishInflate()
        val p = Paint().apply {
            color = Color.CYAN
        }
        setStateDrawable(this, p)
    }

    private fun setStateDrawable(v: ImageView, paint: Paint) {
        val bd = v.drawable as BitmapDrawable
        val srcBmp = bd.bitmap

        //制作纯色背景
        val bitmap = Bitmap.createBitmap(srcBmp.width, srcBmp.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawBitmap(srcBmp.extractAlpha(), 0f, 0f, paint)

        //添加状态
        val sld = StateListDrawable()
        sld.addState(intArrayOf(android.R.attr.state_pressed), BitmapDrawable(bitmap))

        v.setBackgroundDrawable(sld)
    }
}