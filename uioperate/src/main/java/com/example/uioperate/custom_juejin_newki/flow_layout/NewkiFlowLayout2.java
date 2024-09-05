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
        // 让子View去测量自己的宽高，这一步是必须要的
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
