package com.example.uioperate.custom_juejin_newki.flow_layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.utilsgather.ui.SizeTransferUtil;

public class NewkiFlowLayout1 extends ViewGroup {
    public NewkiFlowLayout1(Context context) {
        this(context, null);
    }

    public NewkiFlowLayout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewkiFlowLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mFixedHeight = SizeTransferUtil.dip2px(100, getContext());

    /*
    这里我没有重写 onMeasure() 方法，因此默认的 onMeasure 将会有自己的行为，在AT_MOST的情况，将会使用heightMeasureSpec
    中的高度，这里就是整屏的高度
     */

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(l, i * mFixedHeight, r, (i + 1) * mFixedHeight);
            }
        }
    }
}
