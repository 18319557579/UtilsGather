package com.example.uioperate.custom_juejin_newki.flow_layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class NewkiFlowLayout2 extends ViewGroup {
    public NewkiFlowLayout2(Context context) {
        this(context, null);
    }

    public NewkiFlowLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewkiFlowLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            // 让子View去测量自己的宽高，这一步是必须要的
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.AT_MOST) {
            int layoutChildViewCurX = getPaddingLeft();

            int totalControlHeight = 0;

            for (int i = 0; i < getChildCount(); i++) {
                final View childView = getChildAt(i);
                if (childView.getVisibility() == View.GONE) {
                    continue;
                }

                final MyLayoutParams lp = (MyLayoutParams) childView.getLayoutParams();
                childView.measure(
                        getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), lp.width),
                        getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), lp.height)
                );
                // 获得子View测量出来的高度和宽度
                int width = childView.getMeasuredWidth();
                int height = childView.getMeasuredHeight();

                if (totalControlHeight == 0) {
                    totalControlHeight = height + lp.topMargin + lp.bottomMargin;
                }

                if (layoutChildViewCurX + width + lp.leftMargin + lp.rightMargin > widthSize) {
                    layoutChildViewCurX = getPaddingLeft();  // 超过之后，从左边重新开始
                    totalControlHeight += height + lp.topMargin + lp.bottomMargin;
                }
                layoutChildViewCurX += width + lp.leftMargin + lp.rightMargin;
            }

            // 满足一下父View的要求
            int cachedTotalWidth = resolveSize(widthSize, widthMeasureSpec);
            int cachedTotalHeight = resolveSize(totalControlHeight, heightMeasureSpec);
            // 提交自己的宽高
            setMeasuredDimension(cachedTotalWidth, cachedTotalHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int mViewGroupWidth = getMeasuredWidth();  // ViewGroup 的宽度

        int layoutChildViewCurX = l;  // 当前绘制view的X坐标
        int layoutChildViewCurY = t;  // 当前绘制View的Y坐标

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            // 获得子控件的信息
            int width = childView.getMeasuredWidth();
            int height = childView.getMeasuredHeight();
            final MyLayoutParams lp = (MyLayoutParams) childView.getLayoutParams();


            if (layoutChildViewCurX + width + lp.leftMargin + lp.rightMargin> mViewGroupWidth) {
                layoutChildViewCurX = 1;
                layoutChildViewCurY += height + lp.leftMargin + lp.rightMargin;
            }

            childView.layout(
                    layoutChildViewCurX + lp.leftMargin,
                    layoutChildViewCurY + lp.topMargin,
                    layoutChildViewCurX + width + lp.leftMargin + lp.rightMargin,
                    layoutChildViewCurY + height + lp.topMargin + lp.bottomMargin);
            layoutChildViewCurX += width + lp.leftMargin + lp.rightMargin;
        }
    }

    static class MyLayoutParams extends MarginLayoutParams {

        public MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public MyLayoutParams(int width, int height) {
            super(width, height);
        }

        public MyLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public MyLayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new MyLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyLayoutParams(getContext(), attrs);
    }
}
