package com.example.uioperate.custom_qiujuer;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.animation.PathInterpolatorCompat;

import com.example.utilsgather.logcat.LogUtil;

public class TouchFullView extends View {
    // 圆的画笔
    private Paint mCirclePaint;
    // 圆的半径
    private float mCircleRadius = 50;

    private float mCirclePointX;
    private float mCirclePointY;

    // 可拖动的高度
    private int mDragHeight = 300;

    // 进度值（灵魂，通过手指下拉的进度来桥接其他的各种进度）
    private float mProgress;

    // 目标宽度
    private int mTargetWidth = 400;

    // 贝塞尔曲线的路径以及画笔
    private Path mPath = new Path();
    private Paint mPathPaint;
    // 重心点最终高度，决定控制点的Y坐标
    private int mTargetGravityHeight = 10;
    // 角度变换 0 - 135 度
    private int mTargetAngle = 105;

    private Interpolator mProgressInterpolator = new DecelerateInterpolator();
    private Interpolator mTangentAngleInterpolator;

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
        p.setColor(0xFFFF4081);
        mCirclePaint = p;

        // 初始化路径部分画笔
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setAntiAlias(true);
        p.setDither(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xFFFF4081);
        mPathPaint = p;

        mTangentAngleInterpolator = PathInterpolatorCompat.create(
                (mCircleRadius * 2.0f) / mDragHeight,
                90.0f / mTargetAngle
        );

    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // 进行基础坐标参数改变
        int count = canvas.save();
        float tranX = (getWidth() - getValueByLine(getWidth(), mTargetWidth, mProgress)) / 2;
        canvas.translate(tranX, 0);

        canvas.drawPath(mPath, mPathPaint);

        canvas.drawCircle(mCirclePointX, mCirclePointY, mCircleRadius, mCirclePaint);

        canvas.restoreToCount(count);
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

        int iWidth = (int) (2 * mCircleRadius + getPaddingLeft() + getPaddingRight());
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
        // 当高度变化时进行路径更新
        updatePathLayout();
    }

    public void setProgress(float progress) {
        LogUtil.d("P: " + progress);
        mProgress = progress;
        // 请求重新进行测量，触发onMeasure()，之后会触发onSizeChanged()
        requestLayout();
    }

    /**
     * 更新我们的路径等相关操作
     */
    private void updatePathLayout() {
        // 获取进度
        final float progress = mProgressInterpolator.getInterpolation(mProgress);

        // 获取可绘制区域高度宽度
        final float w = getValueByLine(getWidth(), mTargetWidth, mProgress);
        final float h = getValueByLine(0, mDragHeight, mProgress);
        // 对称轴的参数，圆的圆心X
        final float cPointX = w / 2;  // x的坐标是宽度的中间
        // 圆的半径
        final float cRadius = mCircleRadius;
        // 圆的中心点Y坐标
        final float cPointY = h - cRadius;
        // 控制点结束Y的
        final float endControlY = mTargetGravityHeight;

        // 更新圆的坐标
        mCirclePointX = cPointX;
        mCirclePointY = cPointY;

        // 复位
        final Path path = mPath;
        path.reset();
        path.moveTo(0, 0);

        // 左边部分的结束点和控制点
        float lEndPointX, lEndPointY;
        float lControlPointX, lControlPointY;

        // 获取当前切线弧度
        float variationAngle = mTargetAngle * mTangentAngleInterpolator.getInterpolation(progress);
        double radian = Math.toRadians(variationAngle);
        float x = (float) (Math.sin(radian) * cRadius);
        float y = (float) (Math.cos(radian) * cRadius);

        lEndPointX = cPointX - x;
        lEndPointY = cPointY + y;

        // 控制点的坐标变化
        lControlPointY = getValueByLine(0, endControlY, progress);
        // 控制点与结束点 垂直方向的距离
        float tHeight = cPointY - lControlPointY;
        // 控制点与结束点 水平方向的距离
        // 控制点与结束点 水平方向的距离
        float tWidth = (float) (tHeight / Math.tan(radian));
        lControlPointX = lEndPointX - tWidth;

        // 贝塞尔曲线
        path.quadTo(lControlPointX, lControlPointY, lEndPointX, lEndPointY);
        // 链接到右边
        path.lineTo(cPointX + (cPointX - lEndPointX), lEndPointY);
        // 画右边的贝赛尔曲线
        path.quadTo(cPointX + cPointX - lControlPointX, lControlPointY, w, 0);
    }

    // 通过 起始值、结束值、当前进度，来获得当前值
    private float getValueByLine(float start, float end, float progress) {
        return start + (end - start) * progress;
    }

    private ValueAnimator valueAnimator;
    public void release() {
        if (valueAnimator == null) {
            ValueAnimator animator = ValueAnimator.ofFloat(mProgress, 0f);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(400);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                    Object val = animation.getAnimatedValue();
                    if (val instanceof Float) {
                        setProgress((float)val);
                    }
                }
            });
            valueAnimator = animator;
        } else {
            valueAnimator.cancel();
            valueAnimator.setFloatValues(mProgress, 0f);
        }
        valueAnimator.start();
    }
}
