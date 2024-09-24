package com.example.uioperate.touch_event_gcssloop.boss_call_2;

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
            LogUtil.d(Static.TAG2 + Static.dispatchTouchEvent + "技术部,老板说按钮不好看,要加一道光.");
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.d(Static.TAG2 + Static.onInterceptTouchEvent);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.d(Static.TAG2 + Static.onTouchEvent + "。。。");
        }
        return super.onTouchEvent(event);
    }
}
