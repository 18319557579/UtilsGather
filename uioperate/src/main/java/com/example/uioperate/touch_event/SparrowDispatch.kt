package com.example.uioperate.touch_event

import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintSet.Motion

interface ViewParent {
    fun requestDisallowInterceptTouchEvent(isDisallowIntercept: Boolean)
}

open class MView {
    var parent: ViewParent? = null

    open fun dispatch(ev: MotionEvent): Boolean {
        return onTouch(ev)
    }

    open fun onTouch(ev: MotionEvent): Boolean {
        return false
    }
}

open class MViewGroup(private val child: MView) : MView(), ViewParent {
    private var isChildNeedEvent = false
    private var isSelfNeedEvent = false
    private var isDisallowIntercept = false

    init {
        child.parent = this
    }

    override fun dispatch(ev: MotionEvent): Boolean {
        var handled = false

        //先看DOWN事件
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            clearStatus()

            //如果没有不许拦截，并且onIntercept()中配置了拦截ACTION_DOWN（不过一般ViewGroup中不会拦截DOWN事件的）
            if (!isDisallowIntercept && onIntercept(ev)) {
                isSelfNeedEvent = true
                handled = onTouch(ev)
            } else {
                //分发给子View去处理
                handled = child.dispatch(ev)
                //如果子View对DOWN进行了处理，那么isChildNeedEvent标记为true
                if (handled) isChildNeedEvent = true

                if (!handled) { // 这里没有用 if else 是因为这样写上下一致，更清晰
                    handled = onTouch(ev)  //如果子View都不打算处理该DOWN事件，那么本ViewGroup尝试进行处理

                    //如果查了子View一圈没有处理，那么这时自己处理了，还是可以将isSelfNeedEvent标记为true，以后事件都发到这里来了
                    if (handled) isSelfNeedEvent = true
                }
            }

        } else {
            // 这里 isSelfNeedEvent 条件判断应该放在 isChildNeedEvent 前面
            // 因为两个都为真的情况只能是自己之后通过 onIntercept 抢了控制权，那这之后的控制权就不会去 child 那儿了
            if (isSelfNeedEvent) {
                handled = onTouch(ev)

                //如果子View响应了DOWN事件
            } else if (isChildNeedEvent) {
                //这时还要查看父View有没有拦截的想法
                //但是！父类想要拦截之前，还得看看子类是不是不让拦截
                if (!isDisallowIntercept && onIntercept(ev)) {  //两个同时满足，那还真给子View拦下来了
                    isSelfNeedEvent = true

                    val cancel = MotionEvent.obtain(ev).apply {
                        action = MotionEvent.ACTION_CANCEL
                    }
                    handled = child.dispatch(ev)
                    cancel.recycle()
                    //没有在给 child 分发CANCEL事件的同时继续把原事件分发给自己的onTouch。可能是为了让一个事件也只能有一个View处理，避免出现bug

                    //ViewGroup没有拦截想法的话，把事件交给子View处理
                } else {
                    handled = child.dispatch(ev)
                }
            }
            // 这里不用再 else 了，因为如果 isSelfNeedEvent 和 isChildNeedEvent 都不为 true，上面不会再发事件下来了（意思是事件压根不会到这里，会在之前的ViewGroup的消费掉了）

        }

        //如果是结束动作的话，那么将状态都进行恢复
        if (ev.actionMasked == MotionEvent.ACTION_UP
            || ev.actionMasked == MotionEvent.ACTION_CANCEL) {
            clearStatus()
        }

        return handled
    }

    /**
     * 恢复状态
     */
    private fun clearStatus() {
        isChildNeedEvent = false
        isSelfNeedEvent = false
        isDisallowIntercept = false
    }

    override fun onTouch(ev: MotionEvent): Boolean {
        return false
    }

    open fun onIntercept(ev: MotionEvent): Boolean {
        return false
    }

    //向父View递归调用requestDisallowInterceptTouchEvent()
    override fun requestDisallowInterceptTouchEvent(isDisallowIntercept: Boolean) {
        this.isDisallowIntercept = isDisallowIntercept
        parent?.requestDisallowInterceptTouchEvent(isDisallowIntercept)
    }

}