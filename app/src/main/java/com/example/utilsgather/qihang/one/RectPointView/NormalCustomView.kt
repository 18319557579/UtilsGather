package com.example.utilsgather.qihang.one.RectPointView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region
import android.graphics.RegionIterator
import android.util.AttributeSet
import android.view.View


class NormalCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*val paint = Paint().apply {
            color = Color.RED
            textSize = 80F
        }
//        val pos = arrayOf(80F, 100F, 80F, 200F, 80F, 300F, 80F, 400F)
        val pos = floatArrayOf(80F, 100F, 80F, 200F, 80F, 300F, 80F, 400F)
        canvas.drawPosText("床前明月", pos, paint)*/

        /*val paint = Paint()
        paint.color = Color.RED
        paint.strokeWidth = 5f
        paint.textSize = 45f
        paint.style = Paint.Style.STROKE


//先创建两条相同的圆形路径，并画出两条路径原形
        val circlePath: Path = Path()
        circlePath.addCircle(220F, 300F, 150F, Path.Direction.CCW) //逆向绘制
        canvas.drawPath(circlePath, paint) //绘制出路径原形
        val circlePath2: Path = Path()
        circlePath2.addCircle(600F, 300F, 150F, Path.Direction.CCW)
        canvas.drawPath(circlePath2, paint) //绘制出路径原形

//绘制原始文字与偏移文字
        val string = "床前明月光,疑是地上霜"
        paint.color = Color.GREEN


//将 hOffset、vOffset 参数值全部设为 0，看原始状态是怎样的
        canvas.drawTextOnPath(string, circlePath, 0F, 0F, paint)


//第二条路径，改变 hOffset、vOffset 参数值
        canvas.drawTextOnPath(string, circlePath2, 80F, 30F, paint)*/

        /*val paint = Paint()
        paint.color = Color.RED
        paint.setTypeface(Typeface.MONOSPACE)
        paint.textSize = 100f
        canvas.drawText("床前明月光 Hello World 1230", 10F, 100F, paint)*/

        /*val typeface = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
        val paint = Paint()
        paint.color = Color.RED
        paint.setTypeface(typeface)
        paint.textSize = 50f
        canvas.drawText("床前明月光", 10F, 100F, paint)*/

        /*val paint = Paint()
        paint.color = Color.RED
        paint.textSize = 90f
        val familyName = "楷体"
        val font = Typeface.create(familyName, Typeface.NORMAL)
        paint.setTypeface(font)
        canvas.drawText("床前明月光", 10F, 100F, paint)*/


        /*//自定义字体，迷你简萝卜
        val paint = Paint()
        paint.color = Color.RED
        paint.textSize = 60f
        val mgr: AssetManager = context.getAssets() //得到 AssetManager

//根据路径得到 Typeface
        val typeface = Typeface.createFromAsset(mgr, "fonts/jian_luobo.ttf")
        paint.setTypeface(typeface)
        canvas.drawText("床前明月光,疑是地上霜", 10F, 100F, paint)*/

        /*val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
*/
        /*val region = Region(Rect(50, 50, 200, 100))
        drawRegion(canvas, region, paint)*/

        /*val ovalPath = Path()
        val rect = RectF(50F, 50F, 200F, 500F)
        ovalPath.addOval(rect, Path.Direction.CCW)

        val rgn = Region()
        rgn.setPath(ovalPath, Region(50, 50, 200, 500))
        drawRegion(canvas, rgn, paint)*/

        /*val region = Region(10, 10, 200, 100)
        region.union(Rect(10, 10, 50, 300))
        drawRegion(canvas, region, paint)*/

        /*val rect1 = Rect(100, 100, 400, 200)
        val rect2 = Rect(200, 0, 300, 300)

        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
        canvas.drawRect(rect1, paint)
        canvas.drawRect(rect2, paint)

        val region1 = Region(rect1)
        val region2 = Region(rect2)
//        region.op(region2, Region.Op.INTERSECT)
        val region = Region().apply {
            op(region1, region2, Region.Op.INTERSECT)
        }

        val paint_fill = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.FILL
        }
        drawRegion(canvas, region, paint_fill)*/

        /*val paint_green = generatePath(Color.GREEN, Style.FILL, 5f)
        val paint_red = generatePath(Color.RED, Style.STROKE, 5f)
        val rect1 = Rect(300, 10, 500, 100)
        canvas.drawRect(rect1, paint_red)
        canvas.rotate(30f)
        canvas.drawRect(rect1, paint_green)
*/

        /*val paint_green = generatePath(Color.GREEN, Style.STROKE, 5f)
        val paint_red = generatePath(Color.RED, Style.STROKE, 5f)
        val rect1 = Rect(10, 10, 200, 100)
        canvas.drawRect(rect1, paint_green)
        canvas.skew(1.732f, 0f)
        canvas.drawRect(rect1, paint_red)*/

        canvas.drawColor(Color.RED)
        canvas.save()

        canvas.clipRect(Rect(100, 100, 800, 800))
        canvas.drawColor(Color.GREEN)
        canvas.save()

        canvas.clipRect(Rect(200, 200, 700, 700))
        canvas.drawColor(Color.BLUE)
        val ccc = canvas.save()

        canvas.clipRect(Rect(300, 300, 600, 600))
        canvas.drawColor(Color.BLACK)
        canvas.save()

        canvas.clipRect(Rect(400, 400, 500, 500))
        canvas.drawColor(Color.WHITE)

        canvas.restoreToCount(ccc)
        canvas.drawColor(Color.YELLOW)
    }

    private fun generatePath(color: Int, style: Style, width: Float) = Paint().apply {
        setColor(color)
        setStyle(style)
        strokeWidth = width
    }

    private fun drawRegion(canvas: Canvas, rgn: Region, paint: Paint) {
        val iter = RegionIterator(rgn)
        val r = Rect()
        while (iter.next(r)) {
            canvas.drawRect(r, paint)
        }
    }
}