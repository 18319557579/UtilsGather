package com.example.utilsuser.qihang.ten

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.utilsuser.R

class TelescopeViewZoomIn(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private lateinit var bitmap: Bitmap
    private lateinit var drawable: ShapeDrawable
    private val RADIUS = 150
    private val FACTOR = 3
    private val matrix = Matrix()

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (! ::bitmap.isInitialized) {
            val bmp = BitmapFactory.decodeResource(resources, R.drawable.scenery)
            //根据原图像，生成占满View的图像
            bitmap = Bitmap.createScaledBitmap(bmp, width, height, false)

            val shader = BitmapShader(
                Bitmap.createScaledBitmap(bitmap, bitmap.width * FACTOR, bitmap.height * FACTOR, true),
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

            drawable = ShapeDrawable(OvalShape()).apply {
                paint.shader = shader
                setBounds(0, 0, RADIUS * 2, RADIUS * 2)
            }


        }
//画背景图
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        //画望远镜
        drawable.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        matrix.setTranslate(RADIUS - x * FACTOR, RADIUS - y * FACTOR)
        drawable.paint.shader.setLocalMatrix(matrix)  //设置望远镜的内容在着色器上的位置

        //设置望远镜框的位置
        drawable.setBounds((x - RADIUS).toInt(), (y - RADIUS).toInt(), (x + RADIUS).toInt(),
            (y +RADIUS).toInt()
        )
        invalidate()
        return true
    }
}