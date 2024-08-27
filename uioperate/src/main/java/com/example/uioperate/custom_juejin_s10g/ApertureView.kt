package com.example.uioperate.custom_juejin_s10g

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.view.animation.LinearInterpolator
import androidx.core.graphics.withSave

class ApertureView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {
    companion object {
        val DEF_WIDTH = 200.dp
        val DEF_HEIGHT = DEF_WIDTH
        private val RADIUS = 20.dp
    }

    private val animator by lazy {
        // 改变字段 currentSpeed 的值
        ObjectAnimator.ofFloat(this, "currentSpeed", 0f, 360f).apply {
            repeatCount = ValueAnimator.INFINITE
            duration = 2000L
            interpolator = LinearInterpolator()  // 匀速旋转
        }
    }

    // 使用setter，来多写一步操作 invalidate()
    private var currentSpeed = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val path by lazy {
        Path().also {
            it.addRoundRect(rectF, RADIUS, RADIUS, Path.Direction.CCW)
        }
    }

    private val color1 by lazy {
        LinearGradient(
            width * 1f,
            height / 2f,
            width * 1f,
            height * 1f,
            intArrayOf(Color.TRANSPARENT, Color.RED),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
    }
    private val color2 by lazy {
        LinearGradient(
            width / 2f,
            height / 2f,
            width / 2f,
            0f,
            intArrayOf(Color.TRANSPARENT, Color.GREEN),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
    }

    init {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, RADIUS)
            }
        }
        clipToOutline = true

        animator.start()
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(DEF_WIDTH.toInt(), widthMeasureSpec)
        val height = resolveSize(DEF_HEIGHT.toInt(), heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    // 内矩形
    private val rectF by lazy {
        val left = 0f + RADIUS / 2f
        val top = 0f + RADIUS / 2f
        val right = left + DEF_WIDTH - RADIUS
        val bottom = top + DEF_HEIGHT - RADIUS
        RectF(left, top, right, bottom)
    }
    override fun onDraw(canvas: Canvas) {
        // 包装了 save() 和 restoreToCount()
        canvas.withSave {
            // 剪切后保留外面的
            canvas.clipOutPath(path)

            canvas.rotate(currentSpeed, width / 2f, height / 2f)

            val left = rectF.left + rectF.width() / 2f
            val right = rectF.right + rectF.width() / 2
            val top = rectF.top + rectF.height() / 2f
            val bottom = rectF.bottom + rectF.height() / 2f
            // 绘制渐变view1
            paint.shader = color1
            canvas.drawRect(left, top, right, bottom, paint)
            paint.shader = null

            // 绘制渐变view2
            paint.shader = color2
            canvas.drawRect(left, top, -right, -bottom, paint)
            paint.shader = null
        }


    }
}