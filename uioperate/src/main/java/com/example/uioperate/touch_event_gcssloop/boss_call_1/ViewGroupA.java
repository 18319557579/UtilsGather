package com.example.uioperate.touch_event_gcssloop.boss_call_1;

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
            LogUtil.d(Static.TAG3 + Static.dispatchTouchEvent + "老板要做淘宝,下周上线?");
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.d(Static.TAG3 + Static.onInterceptTouchEvent + "(看着不太靠谱,先问问小王怎么看)");
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.d(Static.TAG3 + Static.onTouchEvent + "小王说做不了");
        }
        return super.onTouchEvent(event);
    }
}
