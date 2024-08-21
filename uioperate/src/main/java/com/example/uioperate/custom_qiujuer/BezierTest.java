package com.example.uioperate.custom_qiujuer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BezierTest extends View {
    public BezierTest(Context context) {
        super(context);
        init();
    }

    public BezierTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BezierTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path mPath = new Path();
    private void init() {
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        // 在当量操作的情况下（例如循环），或者成员变量写了三次或以上，可以将它用局部变量的方式来写
        Path path = mPath;

        // 一阶贝塞尔曲线
        path.moveTo(100, 100);
        path.lineTo(500, 500);

        // 二阶贝塞尔曲线
//        path.quadTo(500, 0, 700, 300);
        path.rQuadTo(200, -300, 400, 0);

        path.moveTo(400, 800);
        path.cubicTo(500, 600, 700, 1200, 800, 800);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        canvas.drawPoint(500, 0, mPaint);
    }
}
