package com.example.uioperate.touch_event_gcssloop.test_gesture;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.utilsgather.logcat.LogUtil;

public class ScaleGestureDemoView extends View {


    private ScaleGestureDetector mScaleGestureDetector;

    public ScaleGestureDemoView(Context context) {
        super(context);
    }

    public ScaleGestureDemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initScaleGestureDetector();
    }

    private void initScaleGestureDetector() {
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                LogUtil.i("--------------------------begin开始-------------------------------");
                LogUtil.i( "focusX = " + detector.getFocusX());       // 缩放中心，x坐标
                LogUtil.i( "focusY = " + detector.getFocusY());       // 缩放中心y坐标
                LogUtil.i( "scale = " + detector.getScaleFactor());   // 缩放因子
                LogUtil.i( "CurrentSpan = " + detector.getCurrentSpan());
                LogUtil.i( "PreviousSpan = " + detector.getPreviousSpan());
                LogUtil.i("--------------------------begin结束-------------------------------");
                return true;
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                LogUtil.i( "focusX = " + detector.getFocusX());       // 缩放中心，x坐标
                LogUtil.i( "focusY = " + detector.getFocusY());       // 缩放中心y坐标
                LogUtil.i( "scale = " + detector.getScaleFactor());   // 缩放因子
                return false;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                LogUtil.i("--------------------------end开始-------------------------------");
                LogUtil.i( "focusX = " + detector.getFocusX());       // 缩放中心，x坐标
                LogUtil.i( "focusY = " + detector.getFocusY());       // 缩放中心y坐标
                LogUtil.i( "scale = " + detector.getScaleFactor());   // 缩放因子
                LogUtil.i("--------------------------end结束-------------------------------");
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }
}