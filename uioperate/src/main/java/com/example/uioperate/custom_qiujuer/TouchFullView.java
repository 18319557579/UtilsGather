package com.example.uioperate.custom_qiujuer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utilsgather.logcat.LogUtil;

public class TouchFullView extends View {
    // 圆的画笔
    private Paint mCirclePaint;
    // 圆的半径
    private int mCircleRadius = 100;

    private int mCirclePointX;
    private int mCirclePointY;

    // 可拖动的高度
    private int mDragHeight = 800;

    // 进度值
    private float mProgress;

    public TouchFullView(Context context) {
        super(context);
        init();
    }

    public TouchFullView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchFullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TouchFullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 设置抗锯齿
        p.setAntiAlias(true);
        // 设置抖动
        p.setDither(true);
        // 设置为填充方式
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xFF000000);
        mCirclePaint = p;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCirclePointX, mCirclePointY, mCircleRadius, mCirclePaint);
    }

    /**
     * 当进行测量时触发
     *
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int iWidth = 2 * mCircleRadius + getPaddingLeft() + getPaddingRight();
        int iHeight = (int) (mDragHeight * mProgress + 0.5f) + getPaddingTop() + getPaddingBottom();
        int measureWidth, measureHeight;


        if (widthMode == MeasureSpec.EXACTLY) {  // 确切的
            measureWidth = width;
        } else if (widthMode == MeasureSpec.AT_MOST) {  //最多
            measureWidth = Math.min(iWidth, width);  //削头。如果 iWidth 比 width 大的话，就用 width 好了
        } else {  //没约束
            measureWidth = iWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            measureHeight = height;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measureHeight = Math.min(iHeight, height);
        } else {
            measureHeight = iHeight;
        }

        //设置测量的高度 宽度
        setMeasuredDimension(measureWidth, measureHeight);
    }

    /**
     * 当大小改变时触发
     * @param w Current width of this view.
     * @param h Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCirclePointX = getWidth() >> 1;
        mCirclePointY = getHeight() >> 1;
    }

    public void setProgress(float progress) {
        LogUtil.d("P: " + progress);
        mProgress = progress;
        // 请求重新进行测量
        requestLayout();
    }
}
