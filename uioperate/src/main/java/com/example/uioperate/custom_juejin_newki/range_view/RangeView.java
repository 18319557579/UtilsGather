package com.example.uioperate.custom_juejin_newki.range_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uioperate.R;

public class RangeView extends View {
    public RangeView(Context context) {
        super(context);
        init();
    }

    public RangeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int mRangLineDefaultColor = Color.parseColor("#CDCDCD");  //默认颜色
    private int mRangLineCheckedColor = Color.parseColor("#0689FD");  //选中颜色
    private Paint mDefaultLinePaint;
    private Paint mSelectedLinePaint;

    private int mCircleRadius = getResources().getDimensionPixelSize(R.dimen.d_14dp); //圆半径
    private int mCircleStrokeWidth = getResources().getDimensionPixelSize(R.dimen.d_1d5dp); //圆边框的大小

    private int mStrokeRadius;  //半径+边框的总值

    private float mLeftCircleCenterX;    //左右两个圆心位置
    private float mLeftCircleCenterY;
    private float mRightCircleCenterX;
    private float mRightCircleCenterY;
    private float mLeftBorder;
    private float mRightBorder;

    private int mRangLineHeight = getResources().getDimensionPixelSize(R.dimen.d_4dp);  //中间圆角矩形线的高
    private int mRangLineCornerRadius = mRangLineHeight / 2;     //圆角矩形线的圆角半径

    private RectF mDefaultCornerLineRect = new RectF();
    private RectF mSelectedCornerLineRect = new RectF();     //选中颜色的圆角矩形

    private Paint mLeftCirclePaint;        //各种画笔
    private Paint mLeftCircleStrokePaint;
    private Paint mRightCirclePaint;
    private Paint mRightCircleStrokePaint;

    private void init() {
        mDefaultLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultLinePaint.setDither(true);
        mDefaultLinePaint.setColor(mRangLineDefaultColor);

        mSelectedLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectedLinePaint.setDither(true);
        mSelectedLinePaint.setColor(mRangLineCheckedColor);

        //初始化左边实心圆
        mLeftCirclePaint = new Paint();
        mLeftCirclePaint.setAntiAlias(true);
        mLeftCirclePaint.setDither(true);
        mLeftCirclePaint.setStyle(Paint.Style.FILL);
        mLeftCirclePaint.setColor(Color.parseColor("#0689FD"));

        //初始化左边圆的边框
        mLeftCircleStrokePaint = new Paint();
        mLeftCircleStrokePaint.setAntiAlias(true);
        mLeftCircleStrokePaint.setDither(true);
        mLeftCircleStrokePaint.setStyle(Paint.Style.STROKE);
        mLeftCircleStrokePaint.setColor(Color.parseColor("#FFFFFF"));
        mLeftCircleStrokePaint.setStrokeWidth(mCircleStrokeWidth);

        //初始化右边实心圆
        mRightCirclePaint = new Paint();
        mRightCirclePaint.setAntiAlias(true);
        mRightCirclePaint.setDither(true);
        mRightCirclePaint.setStyle(Paint.Style.FILL);
        mRightCirclePaint.setColor(Color.parseColor("#0689FD"));

        //初始化右边圆的边框
        mRightCircleStrokePaint = new Paint();
        mRightCircleStrokePaint.setAntiAlias(true);
        mRightCircleStrokePaint.setDither(true);
        mRightCircleStrokePaint.setStyle(Paint.Style.STROKE);
        mRightCircleStrokePaint.setColor(Color.parseColor("#FFFFFF"));
        mRightCircleStrokePaint.setStrokeWidth(mCircleStrokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize  = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int finalWidth, finalHeight;

        // 计算的宽度和高度
        int calWidthSize = mCircleRadius * 2 + mCircleStrokeWidth * 2 + getPaddingLeft() + getPaddingRight();
        int calHeightSize = mCircleRadius * 2 + mCircleStrokeWidth * 2 + getPaddingTop() + getPaddingBottom();

        if (widthMode == MeasureSpec.EXACTLY) {
            finalWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            finalWidth = Math.min(widthSize, calWidthSize);
        } else {
            finalWidth = calWidthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            finalHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            finalHeight = Math.min(heightSize, calHeightSize);
        } else {
            finalHeight = calHeightSize;
        }

        setMeasuredDimension(finalWidth, finalHeight);
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

//        int realWidth = w - getPaddingLeft() - getPaddingRight();
        mStrokeRadius = mCircleRadius + mCircleStrokeWidth;  //半径+边框的总值

        // 左边圆的圆心坐标
        mLeftCircleCenterX = mLeftBorder = getPaddingLeft() + mStrokeRadius;
        mLeftCircleCenterY = getPaddingTop() + mStrokeRadius;

        // 右边圆的圆心坐标
        mRightCircleCenterX = mRightBorder = w - getPaddingRight() - mStrokeRadius;
        mRightCircleCenterY = getPaddingTop() + mStrokeRadius;

        mDefaultCornerLineRect.left = mSelectedCornerLineRect.left = mLeftCircleCenterX;
        // 因为圆的高是支撑整个空间的高度的，所以限定中间条的高时，要去用 mStrokeRadius - mRangLineCornerRadius
        mDefaultCornerLineRect.top = mSelectedCornerLineRect.top = getPaddingTop() + mStrokeRadius - mRangLineCornerRadius;
        mDefaultCornerLineRect.right = mSelectedCornerLineRect.right = mRightCircleCenterX;
        mDefaultCornerLineRect.bottom = mSelectedCornerLineRect.bottom = getPaddingTop() + mStrokeRadius + mRangLineCornerRadius;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        drawDefaultCornerRectLine(canvas);
        drawSelectedRectLine(canvas);
        drawLeftCircle(canvas);
        drawRightCircle(canvas);
    }

    //左侧的控制圆与边框
    private void drawLeftCircle(Canvas canvas) {
        canvas.drawCircle(mLeftCircleCenterX, mLeftCircleCenterY, mCircleRadius, mLeftCirclePaint);
        canvas.drawCircle(mLeftCircleCenterX, mLeftCircleCenterY, mCircleRadius, mLeftCircleStrokePaint);
    }
    //右侧的控制圆与边框
    private void drawRightCircle(Canvas canvas) {
        canvas.drawCircle(mRightCircleCenterX, mRightCircleCenterY, mCircleRadius, mRightCirclePaint);
        canvas.drawCircle(mRightCircleCenterX, mRightCircleCenterY, mCircleRadius, mRightCircleStrokePaint);
    }
    //中心的圆角矩形进度条-默认的底色
    private void drawDefaultCornerRectLine(Canvas canvas) {
        canvas.drawRoundRect(mDefaultCornerLineRect, mRangLineCornerRadius, mRangLineCornerRadius, mDefaultLinePaint);
    }
    //中心的圆角矩形进度条-已经选中的颜色
    private void drawSelectedRectLine(Canvas canvas) {
        canvas.drawRoundRect(mSelectedCornerLineRect, mRangLineCornerRadius, mRangLineCornerRadius, mSelectedLinePaint);
    }

    /**
     * 判断当前移动的是左侧限制圆，还是右侧限制圆
     *
     * @param downX 按下的坐标点
     * @return true表示按下的左侧，false表示按下的右侧
     */
    private boolean checkTouchCircleLeftOrRight(float downX) {
        return Math.abs(mLeftCircleCenterX - downX) < Math.abs(mRightCircleCenterX - downX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean touchLeftCircle;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float downX = event.getX();
            touchLeftCircle = checkTouchCircleLeftOrRight(downX);
            if (touchLeftCircle) {
                mLeftCircleCenterX = downX;
            } else {
                mRightCircleCenterX = downX;
            }
        }
        limitMinAndMax();
        mSelectedCornerLineRect.left = mLeftCircleCenterX;
        mSelectedCornerLineRect.right = mRightCircleCenterX;

        invalidate();
        return true;
    }

    private void limitMinAndMax() {
        //如果是操作的左侧限制圆，超过最小值了
        if (mLeftCircleCenterX < mLeftBorder) {
            mLeftCircleCenterX = mLeftBorder;
        }
        //如果是操作的右侧限制圆，超过最小值了
        if (mRightCircleCenterX > mRightBorder) {
            mRightCircleCenterX = mRightBorder;
        }
    }
}
