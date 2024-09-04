package com.example.uioperate.custom_juejin_newki.range_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uioperate.R;
import com.example.utilsgather.logcat.LogUtil;

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

    private boolean touchLeftCircle;

    private int maxValue = 100;  //最大值，默认为100

    private int leftValue;  //当前左边数值，
    private int rightValue;  //当前右边数值

    private int mTopDialogTextSize = getResources().getDimensionPixelSize(R.dimen.d_12dp);  //顶部文字的大小
    private int mTopDialogTextColor = Color.parseColor("#000000");  //顶部文字的颜色
    private int mTopDialogWidth = getResources().getDimensionPixelSize(R.dimen.d_70dp);  //顶部描述信息弹窗的宽度
    private int mTopDialogCornerRadius = getResources().getDimensionPixelSize(R.dimen.d_15dp);  //顶部描述信息弹窗圆角半径
    private int mTopDialogSpaceToProgress = getResources().getDimensionPixelSize(R.dimen.d_2dp); //顶部描述信息弹窗距离进度条的间距(配置)

    private RectF mTopDialogRect = new RectF();      //顶部表示具体数值的矩形
    private Path mTrianglePath;     //画小三角形路径
    private int mTriangleLength = 15;  //等边三角形边长
    private int mTriangleHeight;     //等边三角形的高
    private Paint textPaint;

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
        mRightCirclePaint.setColor(Color.parseColor("#00FF00"));

        //初始化右边圆的边框
        mRightCircleStrokePaint = new Paint();
        mRightCircleStrokePaint.setAntiAlias(true);
        mRightCircleStrokePaint.setDither(true);
        mRightCircleStrokePaint.setStyle(Paint.Style.STROKE);
        mRightCircleStrokePaint.setColor(Color.parseColor("#FFFFFF"));
        mRightCircleStrokePaint.setStrokeWidth(mCircleStrokeWidth);

        // 小三角形的高。用于确定控件的整体高度，以及绘制小三角形
        mTriangleHeight = (int) (mTriangleLength * Math.sqrt(3) / 2);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setDither(true);
        textPaint.setTextSize(mTopDialogTextSize);
        textPaint.setColor(mTopDialogTextColor);
        textPaint.setTextAlign(Paint.Align.CENTER);

        mTopDialogRect.top = getPaddingTop();
        mTopDialogRect.bottom = getPaddingTop() + mTopDialogCornerRadius * 2;
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
        int calHeightSize = mCircleRadius * 2 + mCircleStrokeWidth * 2 + getPaddingTop() + getPaddingBottom()
                + mTopDialogCornerRadius * 2 + mTriangleHeight + mTopDialogSpaceToProgress ;  //下面这行就是提示框的

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

        //顶部多的距离
        int topDialogHeight = mTopDialogCornerRadius * 2 + mTriangleHeight + mTopDialogSpaceToProgress;

        // 左边圆的圆心坐标
        mLeftCircleCenterX = mLeftBorder = getPaddingLeft() + mStrokeRadius;
        mLeftCircleCenterY = getPaddingTop() + mStrokeRadius
            + topDialogHeight;

        // 右边圆的圆心坐标
        mRightCircleCenterX = mRightBorder = w - getPaddingRight() - mStrokeRadius;
        mRightCircleCenterY = getPaddingTop() + mStrokeRadius
            + topDialogHeight;

        mDefaultCornerLineRect.left = mSelectedCornerLineRect.left = mLeftCircleCenterX;
        // 因为圆的高是支撑整个空间的高度的，所以限定中间条的高时，要去用 mStrokeRadius - mRangLineCornerRadius
        mDefaultCornerLineRect.top = mSelectedCornerLineRect.top = getPaddingTop() + mStrokeRadius - mRangLineCornerRadius
            + topDialogHeight;
        mDefaultCornerLineRect.right = mSelectedCornerLineRect.right = mRightCircleCenterX;
        mDefaultCornerLineRect.bottom = mSelectedCornerLineRect.bottom = getPaddingTop() + mStrokeRadius + mRangLineCornerRadius
            + topDialogHeight;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        drawDefaultCornerRectLine(canvas);
        drawSelectedRectLine(canvas);
        drawLeftCircle(canvas);
        drawRightCircle(canvas);
        drawTopTextRectDialog(canvas);
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
        if (downX < mLeftCircleCenterX) {
            return true;
        }
        if (downX > mRightCircleCenterX) {
            return false;
        }
        return Math.abs(mLeftCircleCenterX - downX) < Math.abs(mRightCircleCenterX - downX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        boolean touchLeftCircle;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float downX = event.getX();
            touchLeftCircle = checkTouchCircleLeftOrRight(downX);
            if (touchLeftCircle) {
                mLeftCircleCenterX = downX;
            } else {
                mRightCircleCenterX = downX;
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float moveX = event.getX();
            if (touchLeftCircle) {
                if (moveX > mRightCircleCenterX) {
                    touchLeftCircle = false;
                    // 这里在越过右边圆的时候，将右边圆的坐标给左边的。因为在快速滑动的过程中，event会回调得不够及时，导致left的坐标会滞后
                    mLeftCircleCenterX = mRightCircleCenterX;
                    mRightCircleCenterX = moveX;
                } else {
                    mLeftCircleCenterX = moveX;
                }

            } else {
                if (moveX < mLeftCircleCenterX) {
                    touchLeftCircle = true;
                    mRightCircleCenterX = mLeftCircleCenterX;
                    mLeftCircleCenterX = moveX;
                } else {
                    mRightCircleCenterX = moveX;
                }
            }



        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            if (mListener != null) mListener.onMoveValue(leftValue, rightValue);
        }


        limitMinAndMax();

        leftValue = getPercentMax(mLeftCircleCenterX);
        rightValue = getPercentMax(mRightCircleCenterX);

        mSelectedCornerLineRect.left = mLeftCircleCenterX;
        mSelectedCornerLineRect.right = mRightCircleCenterX;

        mTopDialogRect.left = (mRightCircleCenterX + mLeftCircleCenterX) / 2 - mTopDialogWidth / 2f;
        mTopDialogRect.right = (mRightCircleCenterX + mLeftCircleCenterX) / 2 + mTopDialogWidth / 2f;

        invalidate();
        return true;
    }

    private int getPercentMax(float distance) {
        // 完整的长度
        float totalDistance = mRightBorder - mLeftBorder;
        float diffValue = distance - mLeftBorder;
        return (int) ((diffValue / totalDistance) * maxValue);
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

    //顶部的文字框
    private void drawTopTextRectDialog(Canvas canvas) {

        // 绘制圆角矩形框
        canvas.drawRoundRect(mTopDialogRect, mTopDialogCornerRadius, mTopDialogCornerRadius, mSelectedLinePaint);

        // 绘制文字
        String textDesc = leftValue + " - " + rightValue;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float baseline = mTopDialogRect.centerY() + (fontMetrics.descent - fontMetrics.ascent) / 2f - fontMetrics.descent;
        canvas.drawText(textDesc, mTopDialogRect.centerX(), baseline, textPaint);
    }

    //回调区间值的监听
    private OnRangeValueListener mListener;
    public interface OnRangeValueListener {
        void onMoveValue(int leftValue, int rightValue);
    }
    public void setRangeValueListener(OnRangeValueListener listener) {
        this.mListener = listener;
    }
}
