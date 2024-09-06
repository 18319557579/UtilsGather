package com.example.uioperate.custom_juejin_newki.flow_layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.utilsgather.ui.SizeTransferUtil;

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

    private int singleWidth = SizeTransferUtil.dip2px(200, getContext());
    private int singleHeight = SizeTransferUtil.dip2px(300, getContext());;

    private AbstractNineGridAdapter mAdapter;

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

        if (mAdapter == null || notGoneChildCount == 0 ) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 0);
            return;
        }

        if (notGoneChildCount == 1) {
            // 如果用于有自定义View的宽高，就使用用户自定义的，否则都使用最大宽度
            itemWidth = singleWidth > 0 ? singleWidth : widthSize;
            itemHeight = singleHeight > 0 ? singleHeight : widthSize;
            // 如果用户自定义的宽度比最大宽度还要大，那么就削宽度，并让高度以比例进行调整
            if (itemWidth > widthSize) {
                itemWidth = widthSize;
                itemHeight = (widthSize / singleWidth) * singleHeight;
            }
        } else {
            itemWidth = (widthSize - horizontalSpacing * 2) / 3;
            itemHeight = itemWidth;
        }

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
        int notGoneChildCount = getNotGoneChildCount();
        int position = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }

            int row;
            int column;
            if (notGoneChildCount == 4) {
                row = position / 2;
                column = position % 2;
            } else {
                row = position / 3;
                column = position % 3;
            }


            int x = getPaddingLeft() + column * (horizontalSpacing + itemWidth);
            int y = getPaddingTop() + row * (verticalSpacing + itemHeight);

            child.layout(x, y, x + itemWidth, y + itemHeight);

            position++;
            if (position == MAX_CHILDREN_COUNT)
                break;
        }

        performBind();
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

    public void setAdapter(AbstractNineGridAdapter adapter) {
        mAdapter = adapter;
        inflateAllViews();
    }

    private void inflateAllViews() {
        removeAllViewsInLayout();

        if (mAdapter == null || mAdapter.getItemCount() == 0) {
            return;
        }

        int displayCount = Math.min(mAdapter.getItemCount(), MAX_CHILDREN_COUNT);

        if (displayCount == 1) {
            View view = mAdapter.onCreateItemView(getContext(), this, -1);
            addView(view);
            requestLayout();
            return;
        }

        for (int i = 0; i < displayCount; i++) {
            int itemType = mAdapter.getItemViewType(i);
            View view = mAdapter.onCreateItemView(getContext(), this, itemType);
            view.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            addView(view);
        }
        requestLayout();
    }

    private void performBind() {
        if (mAdapter == null || mAdapter.getItemCount() == 0) {
            return;
        }

        post(() -> {
            for (int i = 0; i < getNotGoneChildCount(); i++) {
                int itemType = mAdapter.getItemViewType(i);
                View view = getChildAt(i);
                mAdapter.onBindItemView(view, itemType, i);
            }
        });
    }
}
