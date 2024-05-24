package com.example.utilsuser.qihang.ten

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.ArcShape
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.PathShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.View

class ShapeView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val outerR = floatArrayOf(12f, 12f, 12f, 12f, 0f, 0f, 0f, 0f)
    private val inset = RectF(6f, 10f, 6f, 10f)
    private val innerR = floatArrayOf(50f, 12f, 0f, 0f, 12f, 50f, 0f, 0f)

    private val path = Path().apply {
        moveTo(0f, 0f)
        lineTo(100f, 0f)
        lineTo(100f, 100f)
        lineTo(0f, 100f)
        close()
    }

    private val mShapeDrawable: ShapeDrawable

    init {
        val rect1 = Rect(50, 0, 90, 150)
        val rect2 = Rect(0, 50, 250, 100)

        val region = Region(rect1)
        val region2 = Region(rect2)

        region.op(region2, Region.Op.XOR)

        mShapeDrawable = ShapeDrawable(RegionShape(region)).apply {
            bounds = Rect(0, 0, 250, 150)
            paint.color = Color.YELLOW
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mShapeDrawable.draw(canvas)
    }
}