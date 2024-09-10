package com.example.uioperate.custom_shangshan

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class ProtractorView @JvmOverloads constructor(
    private val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){

    private val rectF = RectF()  // 中间小矩形
    private var radius = 0f  // 半径

    private var minLength = 0f  // 最短刻度线

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val rectCenterX = w / 2f
        val rectCenterY = h / 2f

        val newWidth = w * 0.9f
        val newHeight = h * 0.9f
        rectF.set(
            rectCenterX - newWidth / 2f,
            rectCenterY - newHeight / 2f,
            rectCenterX + newWidth / 2f,
            rectCenterY + newHeight /2f
        )

        // 半径取小矩形宽度一半和高度中的最小值
        radius = min(rectF.width() / 2f, rectF.height())

        minLength = radius / 14f


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        val greenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            setColor(Color.GREEN)
        }
        canvas.drawRect(rectF, greenPaint)

        val centerCircle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            setColor(Color.RED)
            strokeWidth = 10f
            strokeCap = Paint.Cap.ROUND
        }

        val centerX = width / 2f
        val centerY = height / 2f + rectF.height() / 2f
        canvas.drawPoint(centerX, centerY, centerCircle)

        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            setColor(Color.parseColor("#80A9A9A9"))
        }



        canvas.withSave {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                clipOutRect(0, centerY.toInt() + 30, width, height)
            }
            canvas.drawCircle(centerX, centerY, radius, bgPaint)
        }

        val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            strokeWidth = 1f
            strokeCap = Paint.Cap.ROUND
        }

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = 20f
            textAlign = Paint.Align.CENTER  // 居中绘制能解决非常多的计算问题
        }

        val unit = PI / 180
        var angle = 0
        while (angle <= 180) {
            val startX = centerX + radius * cos(angle * unit)
            val startY = centerY - radius * sin(angle * unit)

            val lineLength = when {
                angle % 10 == 0 -> {
                    (minLength * 2f).also {  // 下面是画度数
                        val startTextX = centerX + (radius - it * 1.5f) * cos(angle * unit)
                        val startTextY = centerY - (radius - it * 1.5f) * sin(angle * unit)
                        val valueString = "$angle°"

                        canvas.withSave {
                            rotate(90f - angle, startTextX.toFloat(), startTextY.toFloat())
                            drawText(valueString, startTextX.toFloat(), startTextY.toFloat(), textPaint)
                        }
                    }
                }
                angle % 5 == 0 -> minLength * 1.5f
                else -> minLength
            }

            val endX = centerX + (radius - lineLength) * cos(angle * unit)
            val endY = centerY - (radius - lineLength) * sin(angle * unit)
            canvas.drawLine(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat(), linePaint)
            angle ++
        }
    }

}