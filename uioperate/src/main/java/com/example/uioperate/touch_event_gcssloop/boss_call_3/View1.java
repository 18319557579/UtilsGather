package com.example.uioperate.touch_event_gcssloop.boss_call_3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.uioperate.touch_event_gcssloop.Static;
import com.example.utilsgather.logcat.LogUtil;

public class View1 extends View {
    public View1(Context context) {
        super(context);
    }

    public View1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public View1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.d(Static.TAG4 + Static.dispatchTouchEvent + "333333");
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.d(Static.TAG4 + Static.onTouchEvent + "33333333");
        }
        return true;
    }
}
