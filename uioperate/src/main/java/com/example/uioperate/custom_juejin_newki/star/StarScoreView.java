package com.example.uioperate.custom_juejin_newki.star;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uioperate.R;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsgather.source_file.BitmapGainUtil;

public class StarScoreView extends View {
    public StarScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StarScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private int mStarDistance = 5;  // 星星的间距
    private int mStarSize = 20;    // 星星的宽高。每一个星星的宽度和高度是一致的
    private int mStarCount = 5;  // 星星的个数
    private Drawable mStarScoredDrawable;  //已经评分的星星图片
    private Drawable mStarUnscoredDrawable;  //还未评分的星星图片

    private float mScoreNum = 2.5F;  //当前的评分值

    private Paint mPaint;

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StarScoreView);
        this.mStarDistance = typedArray.getDimensionPixelSize(R.styleable.StarScoreView_starDistance, mStarDistance);
        this.mStarSize = typedArray.getDimensionPixelSize(R.styleable.StarScoreView_starSize, mStarSize);
        this.mStarCount = typedArray.getInteger(R.styleable.StarScoreView_starCount, mStarCount);
        this.mStarScoredDrawable = typedArray.getDrawable(R.styleable.StarScoreView_starScoredDrawable);
        this.mStarUnscoredDrawable = typedArray.getDrawable(R.styleable.StarScoreView_starUnscoredDrawable);
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new BitmapShader(
                BitmapGainUtil.drawableToBitmap(mStarScoredDrawable, mStarSize, mStarSize),
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP)
        );
        mPaint = paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(); 其实内部还是调用了 setMeasuredDimension

        // 星星的数量 * 星星的宽度再加上中间的间距 * 数量-1，就是控件的宽度
        int measureWidth = mStarSize * mStarCount + mStarDistance * (mStarCount - 1);
        // 星星的高度就是控件的高度
        setMeasuredDimension(measureWidth, mStarSize);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mStarCount; i++) {
            mStarUnscoredDrawable.setBounds((mStarDistance + mStarSize) * i, 0, (mStarDistance + mStarSize) * i + mStarSize, mStarSize);
            mStarUnscoredDrawable.draw(canvas);
        }

        int integerPart = (int) mScoreNum; // 获取整数部分
        float decimalPart = mScoreNum - integerPart; // 获取小数部分

        canvas.save();

        // 整数部分的星星
        for (int i = 0; i < integerPart; i++) {
            canvas.drawRect(0, 0, mStarSize, mStarSize, mPaint);
            canvas.translate(mStarDistance + mStarSize, 0);
        }
        // 小数部分的星星
        canvas.drawRect(0, 0, mStarSize * decimalPart, mStarSize, mPaint);

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("触摸行为: " + event.toString());

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                updateScoreNum(event);

                // 因为可能放置在可滚动的布局内，例如ScrollView，会导致滑动时触发父View的滚动，导致收到ACTION_CANCEL
                // 这里让父View不要拦截事件了
                getParent().requestDisallowInterceptTouchEvent(true);
                return true;

            case MotionEvent.ACTION_MOVE:
                updateScoreNum(event);
        }
        return super.onTouchEvent(event);
    }

    private void updateScoreNum(MotionEvent event) {
        //x轴的宽度做一下最大最小的限制
        int x = (int) event.getX();
        if (x < 0) {
            x = 0;
        }
        if (x > getMeasuredWidth()) {
            x = getMeasuredWidth();
        }

        mScoreNum = x * 1.0f / (getMeasuredWidth() * 1.0f / mStarCount);
        invalidate();
    }
}
