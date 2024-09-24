package com.example.uioperate.custom_qiujuer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utilsgather.logcat.LogUtil;


public class BezierView extends View {
    public BezierView(Context context) {
        super(context);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Path mBezier = new Path();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private void init() {
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLUE);

        // 在子线程中改变path，达到逐渐画的效果
        new Thread(new Runnable() {
            @Override
            public void run() {
                initBezier();
            }
        }).start();
    }

    private void initBezier() {
        float[] xPoints = new float[]{0, 300, 200, 500, 900, 700, 200, 1000};
        float[] yPoints = new float[]{0, 300, 700, 500, 800, 1000, 900, 1000};

        Path path = mBezier;

        int fps = 100000;
        for (int i = 0; i <= fps; i++) {
            float progress = (float) i / fps;
            float x = calculateBezier(progress, xPoints);
            float y = calculateBezier(progress, yPoints);
            path.lineTo(x, y);

            //刷新界面
            postInvalidate();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 计算某时刻的贝塞尔所处的值（x 或 y）
     * @param t 时间（0-1）
     * @param values 贝塞尔点集合
     * @return 当前t时刻的贝塞尔所处点
     */
    private float calculateBezier(float t, float... values) {
        // 采用双层循环
        final int len = values.length;
        for (int i = len - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                //两个点运算，存到前一个点数组中
                values[j] = values[j] + (values[j + 1] - values[j]) * t;
            }
        }

        // 运算时结果保存在第一位
        return values[0];
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mBezier, mPaint);
    }

//    @SuppressLint("ClickableViewAccessibility")




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();



//        LogUtil.d("当前index: " + index);
        performClick();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("第1个手指按下");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("最后1个手指抬起");
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    LogUtil.i("pointerIndex = " + i + ", pointerId = " + event.getPointerId(i));
                }


                LogUtil.d("发生了移动");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                LogUtil.d("第"+(index+1)+"个手指按下");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                LogUtil.d("第"+(index+1)+"个手指抬起");
                break;
        }
        return super.onTouchEvent(event);
    }


}
