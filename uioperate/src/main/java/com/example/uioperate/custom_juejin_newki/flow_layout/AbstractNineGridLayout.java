package com.example.uioperate.custom_juejin_newki.flow_layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AbstractNineGridLayout extends ViewGroup {
    public AbstractNineGridLayout(Context context) {
        super(context);
        init(context);
    }

    public AbstractNineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AbstractNineGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private static int MAX_CHILDREN_COUNT = 9;
    private int horizontalSpacing = 20;  // 左右边距
    private int verticalSpacing = 20;  // 上下边距

    private int itemWidth;
    private int itemHeight;

    private void init(Context context) {
        for (int i = 0; i < MAX_CHILDREN_COUNT; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            imageView.setBackgroundColor(Color.RED);
            addView(imageView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int notGoneChildCount = getNotGoneChildCount();

        itemWidth = (widthSize - horizontalSpacing * 2) / 3;
        itemHeight = itemWidth;

        measureChildren(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));

        notGoneChildCount = Math.min(notGoneChildCount, MAX_CHILDREN_COUNT);
        int heightSize = calculateRows(notGoneChildCount) * (itemHeight + verticalSpacing) - verticalSpacing
                + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int position = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }

            int row = position / 3;
            int column = position % 3;

            int x = getPaddingLeft() + column * (horizontalSpacing + itemWidth);
            int y = getPaddingTop() + row * (verticalSpacing + itemHeight);

            child.layout(x, y, x + itemWidth, y + itemHeight);

            position++;
            if (position == MAX_CHILDREN_COUNT)
                break;
        }
    }

    private int getNotGoneChildCount() {
        int childCount = getChildCount();
        int notGoneCount = 0;
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).getVisibility() != View.GONE) {
                notGoneCount++;
            }
        }
        return notGoneCount;
    }

    private int calculateRows(int x) {
        return (x + 2) / 3;
    }
}
