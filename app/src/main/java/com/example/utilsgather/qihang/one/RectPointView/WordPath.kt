package com.example.utilsgather.qihang.one.RectPointView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.utilsgather.logcat.LogUtil
import kotlin.math.cos
import kotlin.math.sin

class WordPath @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var radarPaint: Paint  //画网格的paint
    var valuePaint: Paint  //画数据线

    var radius = 0F  //网格最大半径，即最外面环的半径
    var centerX = 0  //中心X
    var centerY = 0  //中心Y

    val count = 6  //多少边形，多少个环
    val anglesCorrespondToRadians = (Math.PI*2/count);

    val data = arrayOf(2, 5, 1, 6, 4, 5)  //数据图的点
    val maxValue = 6F

    init{
        radarPaint = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
        }
        valuePaint = Paint().apply {
            color = Color.BLUE
            style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        LogUtil.d("回调 onDraw()")

        /*val CCWRectpath = Path()
        val rect1 = RectF(50F, 50f, 240f, 200f)
        CCWRectpath.addRect(rect1, Path.Direction.CCW)

        val CWRectpath = Path()
        val rect2 = RectF(290F, 50F, 480F, 200F)
        CWRectpath.addRect(rect2, Path.Direction.CW)

        canvas.drawPath(CCWRectpath, mPaint)
        canvas.drawPath(CWRectpath, mPaint)

        val text = "苦心人天不负，有志者事竟成"
        mPaint.color = Color.GREEN
        mPaint.textSize = 35F
        canvas.drawTextOnPath(text, CCWRectpath, 0f, 18f, mPaint)
        canvas.drawTextOnPath(text, CWRectpath, 0f, 18F, mPaint)*/

        /*val path = Path()
        path.addRect(100F, 100F, 300F, 300F, Path.Direction.CW)
        path.addCircle(300F, 300F, 100F, Path.Direction.CW)
        path.fillType = Path.FillType.INVERSE_EVEN_ODD
        canvas.drawPath(path, mPaint)*/

        /*val path = Path()
        path.fillType = Path.FillType.INVERSE_WINDING
        path.rewind()
        path.addCircle(100F, 100F, 50F, Path.Direction.CW)
        canvas.drawPath(path, mPaint)*/

        //绘制蜘蛛网格
        drawPolygon(canvas)
        //绘制网格中线
        drawLines(canvas)
//        //绘制数据图
        drawRegion(canvas)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        LogUtil.d("回调 onSizeChanged() ： $anglesCorrespondToRadians")
        radius = Math.min(h, w) / 2 * 0.9f
        centerX = w / 2
        centerY = h / 2
        postInvalidate()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    fun drawPolygon(canvas: Canvas) {
        val path = Path()
        val r = radius / count  //是中心点到第一个环顶点的距离，即第一个环的半径
        for (i in 1..count) {  //一共6个环，中心点不用绘制
            val curR = r * i  //当前环的半径
            path.reset()
            for (j in 0 until count) {
                if (j == 0) {  //先移动到中心点右边那个点
                    path.moveTo(centerX + curR, centerY.toFloat())
                } else {
                    val x = centerX + curR * cos(anglesCorrespondToRadians * j)
                    val y = centerY + curR * sin(anglesCorrespondToRadians * j)
                    path.lineTo(x.toFloat(), y.toFloat())
                }
            }
            path.close()
            canvas.drawPath(path, radarPaint)
        }
    }

    fun drawLines(canvas: Canvas) {
        val path = Path()
        for (i in 0 until count) {
            path.reset()
            path.moveTo(centerX.toFloat(), centerY.toFloat())

            val x = centerX + radius * cos(anglesCorrespondToRadians * i)
            val y = centerY + radius * sin(anglesCorrespondToRadians * i)
            path.lineTo(x.toFloat(), y.toFloat())
            canvas.drawPath(path, radarPaint)
        }
    }

    fun drawRegion(canvas: Canvas) {
        val path = Path()
        valuePaint.alpha = 127
        for (i in 0 until count) {
            val percent = data[i] / maxValue
            val x = centerX + radius * cos(anglesCorrespondToRadians * i) * percent
            val y = centerY + radius * sin(anglesCorrespondToRadians * i) * percent
            if (i == 0) {
                path.moveTo(x.toFloat(), centerY.toFloat())
            } else {
                path.lineTo(x.toFloat(), y.toFloat())
            }
            canvas.drawCircle(x.toFloat(), y.toFloat(), 10F, valuePaint)
        }
        valuePaint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawPath(path, valuePaint)
    }
}