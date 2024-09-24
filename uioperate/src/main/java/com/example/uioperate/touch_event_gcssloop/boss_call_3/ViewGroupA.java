package com.example.uioperate.touch_event_gcssloop.boss_call_3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.uioperate.touch_event_gcssloop.Static;
import com.example.utilsgather.logcat.LogUtil;

public class ViewGroupA extends RelativeLayout {
    public ViewGroupA(Context context) {
        super(context);
    }

    public ViewGroupA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.d(Static.TAG3 + Static.dispatchTouchEvent + "项目进度?");
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.d(Static.TAG3 + Static.dispatchTouchEvent);
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.d(Static.TAG3 + Static.onTouchEvent + "正在测试,明天就测试完了");
        }

        // 这种情况的话，属于事件被ViewGroupA消费掉了，不会回调MyRootView和Situation3Activity的onTouchEvent了
//        return true;

        //这种情况，事件并没有被ViewGroupA消费掉，还会向上回传，因此会回调MyRootView和Situation3Activity的onTouchEvent
        return super.onTouchEvent(event);
    }
}