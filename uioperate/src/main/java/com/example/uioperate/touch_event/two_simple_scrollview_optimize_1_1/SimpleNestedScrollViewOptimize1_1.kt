package com.example.uioperate.touch_event.two_simple_scrollview_optimize_1_1

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView
import com.example.utilsgather.logcat.LogUtil
import java.lang.reflect.Field


class SimpleNestedScrollViewOptimize1_1(context: Context, attributeSet: AttributeSet) : ScrollView(context, attributeSet) {
    private var isFirstIntercept = true

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        LogUtil.d("isFirstIntercept: $isFirstIntercept 触发了事件: $ev, ")

        //当ACTION_DOWN事件时，将isFirstIntercept置回true，更好地模拟效果
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            isFirstIntercept = true
        }

        val result = super.onInterceptTouchEvent(ev)
        LogUtil.d("本ScrollView是否进行拦截: $result")


        /*外部的ScrollView在第一次result返回true时，不进行拦截，那么内部的ScrollView「申请外部不拦截」就刚好快了一步，所以当
        result一为true时，后面就接收不到onInterceptTouchEvent()事件了
         */
        if (result && isFirstIntercept) {
            isFirstIntercept = false
            return false
        }

        return result
    }

    private var isNeedRequestDisallowIntercept: Boolean? = null

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) isNeedRequestDisallowIntercept = null
        if (ev.actionMasked == MotionEvent.ACTION_MOVE) {
            if (isNeedRequestDisallowIntercept == false) return false

            if (isNeedRequestDisallowIntercept == null) {
                val offsetY = ev.y.toInt() - getInt("mLastMotionY")
                if (Math.abs(offsetY) > getInt("mTouchSlop")) { // 滑动距离足够判断滑动方向是上还是下后
                    // 判断自己是否能在对应滑动方向上进行滑动（不能则返回false）
                    if ((offsetY > 0 && isScrollToTop()) || (offsetY < 0 && isScrollToBottom())) {
                        isNeedRequestDisallowIntercept = false
                        return false
                    }
                }
            }
        }

        return super.onTouchEvent(ev)
    }

    private fun isScrollToTop() = scrollY == 0

    private fun isScrollToBottom(): Boolean {
        return scrollY + height - paddingTop - paddingBottom == getChildAt(0).height
    }

    private fun getInt(name: String): Int {
        var value = -1
        try {
            val fieldTag: Field = ScrollView::class.java.getDeclaredField(name)
            fieldTag.setAccessible(true)
            value = fieldTag.getInt(this)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return value
    }

    override fun scrollBy(x: Int, y: Int) {
        if ((y > 0 && isScrollToTop()) || (y < 0 && isScrollToBottom())) {
            (parent as View).scrollBy(x, y)
        } else {
            super.scrollBy(x, y)
        }
    }
}