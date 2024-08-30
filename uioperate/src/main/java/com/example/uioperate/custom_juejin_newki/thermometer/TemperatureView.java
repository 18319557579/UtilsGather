package com.example.uioperate.custom_juejin_newki.thermometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsgather.ui.CustomViewUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class TemperatureView extends View {
    private final Point centerPosition = new Point();   //中心点
    private final RectF mRectF = new RectF();   //边界矩形
    private float radius = 0f;   //半径
    private float mSmallRadius = 0f;  //中间小圆的半径

    private float mStartAngle = 120f;  // 圆弧的起始角度
    private float mSweepAngle = 300f; //绘制的起始角度和滑过角度(绘制300度)
    private float mTargetAngle = 0f;  //刻度的角度(根据此计算需要绘制有色的进度)
    private float totalAngle = 0f;  //刻度的角度(根据此计算需要绘制有色的进度)

    private float mCurPercent = 0f;  //当前进度

    private final float TOTAL_DIAL = 100f;

    private Paint mDegreeCirPaint;  //刻度圆环
    private Paint mDegreelinePaint;  //刻度线

    //动画状态
    private boolean isAnimRunning;
    private Timer mAnimTimer;
    private int[] slow = {10, 10, 10, 8, 8, 8, 6, 6, 6, 6, 4, 4, 4, 4, 2};

    private Paint mSmallCirclePaint;  //小圆的画笔

    public TemperatureView(Context context) {
        super(context);
        init();
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Paint degreeCirPaint = new Paint();
        degreeCirPaint.setAntiAlias(true);
        degreeCirPaint.setStyle(Paint.Style.STROKE);
        degreeCirPaint.setStrokeWidth(2f);
        degreeCirPaint.setColor(Color.WHITE);
        degreeCirPaint.setStrokeCap(Paint.Cap.ROUND);
        mDegreeCirPaint = degreeCirPaint;

        Paint degreelinePaint = new Paint();
        degreelinePaint.setStrokeWidth(2f);
        degreelinePaint.setAntiAlias(true);
        mDegreelinePaint = degreelinePaint;

        Paint smallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smallCirclePaint.setDither(true);
        mSmallCirclePaint = smallCirclePaint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtil.d(CustomViewUtil.getWidthHeightInfo(widthMeasureSpec, heightMeasureSpec));

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // 如果不是限定宽度的情况，例如 UNSPECIFIED 和 AT_MOST，都自己设定为 200px
        int newWidthMeasureSpec = widthMode == MeasureSpec.EXACTLY ?
                widthMeasureSpec : MeasureSpec.makeMeasureSpec(200, MeasureSpec.EXACTLY);
        // 获取到最新的宽度
        int width = MeasureSpec.getSize(newWidthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        //我们要的是矩形，不管高度是多高，让它总是和宽度一致
        int height = width;

        centerPosition.x = width / 2;
        centerPosition.y = height / 2;
        radius = width / 2f;
        mSmallRadius = radius - 45f;
        mRectF.set(0f, 0f, width, height);

        super.onMeasure(newWidthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        drawDegreeLine(canvas);

        int evaluateColor = ViewUtil.getCurrentColor(Color.GREEN, Color.RED, mCurPercent);
        drawSmallCircle(canvas, evaluateColor);
    }

    /**
     * 画圆环。半径扩了2个像素
     */
    private void drawArc(Canvas canvas) {
        canvas.drawArc(
                mRectF.left + 2f, mRectF.top + 2f, mRectF.right - 2f, mRectF.bottom - 2f,
                mStartAngle, mSweepAngle, false, mDegreeCirPaint
        );
    }

    /**
     * 画刻度
     */
    private void drawDegreeLine(Canvas canvas) {
        int count = canvas.save();

        // 移动画布到圆心
        canvas.translate(radius, radius);
        // 旋转坐标系,以左下角的刻度为Y轴
        canvas.rotate(30);
        // 每次旋转的角度
        float rotateAngle = mSweepAngle / TOTAL_DIAL;
        // 累计叠加的角度
        float currentAngle = 0;

        for (int i = 0; i <= TOTAL_DIAL; i++) {
            if (currentAngle <= mTargetAngle && mTargetAngle != 0) {  //最后的目标刻度。当mTargetAngle为0时，代表还没开始，所以也要花白色
                // 计算累计划过的刻度百分比
                float percent = currentAngle / mSweepAngle;
                // 不同百分比位置使用不同的颜色刻度
                mDegreelinePaint.setColor(ViewUtil.getCurrentColor(Color.GREEN, Color.RED, percent));
            } else {
                mDegreelinePaint.setColor(Color.WHITE);
            }
            currentAngle += rotateAngle;
            // 从外面往里画 20px
            canvas.drawLine(0, radius, 0, radius - 20, mDegreelinePaint);
            // 画完一个刻度就要顺时针旋转一个刻度的位置
            canvas.rotate(rotateAngle);
        }

        canvas.restoreToCount(count);
    }

    private void drawSmallCircle(Canvas canvas, int evaluateColor) {
        mSmallCirclePaint.setColor(evaluateColor);
        // 其实这里就是将 alpha 的值改为65
        mSmallCirclePaint.setAlpha(65);
        canvas.drawCircle(centerPosition.x, centerPosition.y, mSmallRadius, mSmallCirclePaint);
    }

    // 设置温度，入口的开始
    public void setupTemperature(float temperature) {
        if (isAnimRunning) {
            return;
        }

        totalAngle = (temperature / 100) * mSweepAngle;
        mTargetAngle = 0f;
        mCurPercent = 0f;

        startTimerAnim();
    }

    // 使用定时任务做动画
    private void startTimerAnim() {

        AtomicInteger goIndex = new AtomicInteger(0);
        mAnimTimer = new Timer();
        mAnimTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                isAnimRunning = true;
                mTargetAngle += slow[goIndex.getAndIncrement()];
                if (goIndex.get() == slow.length) {
                    goIndex.getAndDecrement();
                }

                // 原来这里有一个校准的步骤，就是如果例如目标温度设置得比较小，例如2.5f，会导致mTargetAngle小于slow数组的第1个值，这里就会校正回来
                if (mTargetAngle >= totalAngle) {
                    mTargetAngle = totalAngle;
                    isAnimRunning = false;
                    mAnimTimer.cancel();
                }
                mCurPercent = mTargetAngle / mSweepAngle;
                postInvalidate();
            }
        }, 250, 30);
    }
}
