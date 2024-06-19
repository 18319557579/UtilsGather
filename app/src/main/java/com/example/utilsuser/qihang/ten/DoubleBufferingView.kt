package com.example.utilsuser.qihang.ten

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.concurrent.thread

class DoubleBufferingView : SurfaceView {
    private lateinit var mPaint: Paint

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        mPaint = Paint().apply {
            color = Color.RED
            textSize = 60f
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
            for (i in 0 until 10) {
                val canvas = holder.lockCanvas()
                canvas?.drawText("$i", i * 30f, 50f, mPaint)
                holder.unlockCanvasAndPost(canvas)

                Thread.sleep(16)
            }
        }
    }
}