package com.example.uioperate.touch_event_gcssloop.boss_call_1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.uioperate.touch_event_gcssloop.Static;
import com.example.utilsgather.logcat.LogUtil;

public class MyRootView extends RelativeLayout {
    public MyRootView(Context context) {
        super(context);
    }

    public MyRootView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.d(Static.TAG2 + Static.dispatchTouchEvent + "呼叫技术部,老板要做淘宝,下周上线.");
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //Log.i(TAG, Static.onTouchEvent+"报告老板, 技术部说做不了");
            LogUtil.d(Static.TAG2 + Static.onTouchEvent + "报告老板, 技术部说做不了");
        }
        return super.onTouchEvent(event);
    }
}
