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
import android.widget.LinearLayout
import androidx.core.graphics.withSave
import com.example.uioperate.R

class ApertureViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr){

    private val animator by lazy {
        // 改变字段 currentSpeed 的值
        ObjectAnimator.ofFloat(this, "currentSpeed", 0f, 360f).apply {
            repeatCount = ValueAnimator.INFINITE
            duration = mDuration.toLong()
            interpolator = LinearInterpolator()  // 匀速旋转
        }
    }

    // 使用setter，来多写一步操作 invalidate()
    private var currentSpeed = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val color1 by lazy {
        LinearGradient(
            width * 1f,
            height / 2f,
            width * 1f,
            height * 1f,
            intArrayOf(Color.TRANSPARENT, mColor1),
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
            intArrayOf(Color.TRANSPARENT, mColor2),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
    }

    private var mColor1 = Color.YELLOW  //颜色1是默认有的，即至少会有一种颜色在跑
    private var mColor2 = -1  //颜色2是默认没有的
    // 边框宽度
    private var mBorderWidth = 20.dp
    // 边框角度
    private var mBorderAngle = 0f
    // 光圈周期
    private var mDuration = 3000

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ApertureViewGroup)
        try {
            mColor1 = a.getColor(R.styleable.ApertureViewGroup_aperture_color1, mColor1)
            mColor2 = a.getColor(R.styleable.ApertureViewGroup_aperture_color2, mColor2)
            mBorderWidth = a.getDimension(R.styleable.ApertureViewGroup_aperture_border_width, mBorderWidth)
            setPadding(mBorderWidth.toInt(), mBorderWidth.toInt(),
                mBorderWidth.toInt(), mBorderWidth.toInt()
            )
            mBorderAngle = a.getDimension(R.styleable.ApertureViewGroup_aperture_border_angle, mBorderAngle)
            mDuration = a.getInt(R.styleable.ApertureViewGroup_aperture_duration, mDuration)

        } finally {
            a.recycle()
        }


        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, mBorderAngle)
            }
        }
        clipToOutline = true
    }

    private val path by lazy {
        Path().also {
            it.addRoundRect(rectF, mBorderAngle, mBorderAngle, Path.Direction.CCW)
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        animator.start()
    }

    // 内矩形
    private val rectF by lazy {
        val left = mBorderWidth
        val top = mBorderWidth
        val right = width - left
        val bottom = height - top
        RectF(left, top, right, bottom)
    }

    override fun dispatchDraw(canvas: Canvas) {
        val leftLocation = width / 2f
        val topLocation = height / 2f
        val rightLocation = leftLocation + width
        val bottomLocation = topLocation + height

        canvas.withSave {
            // 剪切后保留外面的
            canvas.clipOutPath(path)

            canvas.rotate(currentSpeed, width / 2f, height / 2f)

            // 绘制渐变view1
            paint.shader = color1
            canvas.drawRect(leftLocation, topLocation, rightLocation, bottomLocation, paint)
            paint.shader = null

            if (mColor2 != -1) {
                // 绘制渐变view2
                paint.shader = color2
                canvas.drawRect(leftLocation, topLocation, -rightLocation, -bottomLocation, paint)
                paint.shader = null
            }
        }

        super.dispatchDraw(canvas)
    }
}