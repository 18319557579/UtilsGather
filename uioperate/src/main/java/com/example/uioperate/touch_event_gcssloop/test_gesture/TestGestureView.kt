package com.example.uioperate.touch_event_gcssloop.test_gesture

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.utilsgather.logcat.LogUtil

class TestGestureView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr){

    init {
        setOnClickListener {
            LogUtil.d("触发了点击事件")
        }
        setOnLongClickListener {
            LogUtil.d("触发了长按事件")
            false
        }
    }

    private val detector = GestureDetector(this.context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            LogUtil.d("回调 onSingleTapConfirmed: $e")
            return super.onSingleTapConfirmed(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            LogUtil.d("回调 onDoubleTap: $e")
            return super.onDoubleTap(e)
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            LogUtil.d("回调 onDoubleTapEvent: $e")
            return super.onDoubleTapEvent(e)
        }

        override fun onLongPress(e: MotionEvent) {
            super.onLongPress(e)
            LogUtil.d("回调 onLongPress: $e")
        }

        // 就是用于让消费者选择是否消费掉down事件
        override fun onDown(e: MotionEvent): Boolean {
            LogUtil.d("回调 onDown: $e")
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            LogUtil.d("回调 onFling: 按下: $e1, \n抬起: $e2\n X轴速度: $velocityX, Y轴速度: $velocityY")
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        // 第一次回调的distanceX、distanceY是与初始按下时位置的差距，后续是的回调是和前一次回调的位置差距
        // 负值表示向右滚动，正值表示向左滚动
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            LogUtil.d("回调 onScroll: 按下: $e1, \n抬起: $e2\nX轴距离: $distanceX, Y轴距离: $distanceY")
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onShowPress(e: MotionEvent) {
            super.onShowPress(e)
            LogUtil.d("回调 onShowPress: $e")
        }
    })

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
//        LogUtil.d("回调 onTouchEvent: $event")

        return detector.onTouchEvent(event!!)
    }
}