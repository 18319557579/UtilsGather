package com.example.utilsuser.qihang.ten

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import kotlin.concurrent.thread

class RectView : SurfaceView {
    private lateinit var mPaint: Paint

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }

    private fun init() {
        mPaint = Paint().apply {
            color = Color.argb(0x1F, 0xFF, 0xFF, 0xFF)
            textSize = 30f
        }
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                drawText(holder)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {

            }

        })
    }

    private fun drawText(holder: SurfaceHolder) {
        thread {
            while (true) {
                val dirtyRect = Rect(0, 0, 1, 1)
                val canvas = holder.lockCanvas(dirtyRect)
                val canvasRect = canvas.clipBounds

                if (width == canvasRect.width() && height == canvasRect.height()) {
                    canvas.drawColor(Color.BLACK)
                    holder.unlockCanvasAndPost(canvas)
                } else {
                    holder.unlockCanvasAndPost(canvas)
                    break
                }
            }

            for (i in 0 until 10) {
                if (i == 0) {
                    val canvas = holder.lockCanvas(Rect(10, 10, 600, 600))
                    canvas.drawColor(Color.RED)
                    holder.unlockCanvasAndPost(canvas)
                }
                if (i == 1) {
                    val canvas = holder.lockCanvas(Rect(30, 30, 570, 570))
                    canvas.drawColor(Color.GREEN)
                    holder.unlockCanvasAndPost(canvas)
                }
                if (i == 2) {
                    val canvas = holder.lockCanvas(Rect(60, 60, 540, 540))
                    canvas.drawColor(Color.BLUE)
                    holder.unlockCanvasAndPost(canvas)
                }
                if (i == 3) {
                    val canvas = holder.lockCanvas(Rect(200, 200, 400, 400))
                    mPaint.color = Color.argb(0x3F, 0xFF, 0xFF, 0xFF)
                    canvas.drawCircle(300f, 300f,100f, mPaint)
                    holder.unlockCanvasAndPost(canvas)
                }
                if (i == 4) {
                    val canvas = holder.lockCanvas(Rect(250, 250, 350, 350))
                    mPaint.color = Color.RED
                    canvas.drawText("$i", 300f, 300f, mPaint)
                    holder.unlockCanvasAndPost(canvas)
                }
                Thread.sleep(800)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.color = Color.RED
        canvas.drawRect(Rect(10, 10, 600, 600), mPaint)

        mPaint.color = Color.GREEN
        canvas.drawRect(Rect(30, 30, 570, 570), mPaint)

        mPaint.color = Color.BLUE
        canvas.drawRect(Rect(60, 60, 540, 540), mPaint)

        mPaint.color = Color.argb(0x3F, 0xFF, 0xFF, 0xFF)
        canvas.drawCircle(300f, 300f, 100f, mPaint)

        mPaint.color = Color.GREEN
        canvas.drawText("6", 300f, 300f, mPaint)
    }
}