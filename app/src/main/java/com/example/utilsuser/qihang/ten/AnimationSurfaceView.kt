package com.example.utilsuser.qihang.ten

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.utilsuser.R
import kotlin.concurrent.thread

class AnimationSurfaceView : SurfaceView {
    private var surfaceHolder: SurfaceHolder
    private var flag = false
    private lateinit var bitmap_bg: Bitmap

    private var mSurfaceWidth: Float = 0f
    private var mSurfaceHeight: Float = 0f
    private var mBitposX = 0
    private lateinit var mCanvas: Canvas
    private lateinit var thread: Thread

    enum class State{
        LEFT, RIGHT
    }
    private var state = State.LEFT
    private val  BITMAP_STEP = 1

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        surfaceHolder = holder
        surfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                flag = true
                startAnimation()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                flag = false
            }

        })
    }

    private fun startAnimation() {
        mSurfaceWidth = width.toFloat()
        mSurfaceHeight = height.toFloat()
        val mWidth = mSurfaceWidth * 3 / 2

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.scenery)
        bitmap_bg = Bitmap.createScaledBitmap(bitmap, mWidth.toInt(), mSurfaceHeight.toInt(), true)

        thread = thread {
            while (flag) {
                mCanvas = surfaceHolder.lockCanvas()
                DrawView()
                surfaceHolder.unlockCanvasAndPost(mCanvas)
                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

    }

    protected fun DrawView() {
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        mCanvas.drawBitmap(bitmap_bg, mBitposX.toFloat(), 0f, null)

        when(state) {
            State.LEFT -> {
                mBitposX -= BITMAP_STEP
            }
            State.RIGHT -> {
                mBitposX += BITMAP_STEP
            }
        }
        if (mBitposX <= -mSurfaceWidth / 2) {
            state = State.RIGHT
        }
        if (mBitposX >= 0) {
            state = State.LEFT
        }
    }
}