package com.example.utilsuser.qihang.ten

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet
import android.view.View
import com.example.utilsuser.R

class ShapeShader(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val mShapeDrawable: ShapeDrawable

    init {
        mShapeDrawable = ShapeDrawable(RectShape()).apply {
            bounds = Rect(100, 100, 300, 300)
        }
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avator)
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mShapeDrawable.paint.shader = bitmapShader

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mShapeDrawable.draw(canvas)
    }
}