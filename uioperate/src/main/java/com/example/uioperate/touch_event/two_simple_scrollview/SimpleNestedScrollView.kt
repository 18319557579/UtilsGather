package com.example.uioperate.touch_event.two_simple_scrollview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import com.example.utilsgather.logcat.LogUtil


class SimpleNestedScrollView(context: Context, attributeSet: AttributeSet) : ScrollView(context, attributeSet) {
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
}