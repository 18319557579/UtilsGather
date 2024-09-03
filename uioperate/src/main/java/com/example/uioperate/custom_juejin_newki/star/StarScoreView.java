package com.example.uioperate.custom_juejin_newki.star;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.uioperate.R;
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
}
