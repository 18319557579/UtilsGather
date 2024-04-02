package com.example.utilsgather.cutomerview.flow;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.utilsgather.R;
import com.example.utilsgather.logcat.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private List<List<View>> mAllView = new ArrayList<>();  //每一个元素
    private List<Integer> mLineHeight = new ArrayList<>();  //每一行的行高
    private int mMaxLines;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        mMaxLines = a.getInt(R.styleable.FlowLayout_maxLines, Integer.MAX_VALUE);

//        int[] ml = new int[]{android.R.attr.maxLines};
//        TypedArray a = context.obtainStyledAttributes(attrs, ml);
//        mMaxLines = a.getInt(0, Integer.MAX_VALUE);

        a.recycle();
        LogUtil.d("maxLines = " + mMaxLines);
    }

    /**
     * 最终我们在onMeasure()中，要得到FlowLayout的宽度和高度
     * 宽度：一定是确定的
     * 高度：wrap_content，exactly，unspecified
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mAllView.clear();
        mLineHeight.clear();

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.d("FlowLayout的onMeasure()回调");

        //widthMeasureSpec可能面临的情况（建议宽度 + mode）
        //1 300dp + exactly
        //2 parent width + at_most
        //3 0,parent width + unspecified

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int lineWidth = 0;  //目前行宽（当前行所有元素的累加宽度）
        int lineHeight = 0;  //当前行高，取决于当前行中最高的元素
        int height = 0;  //总高度

        // widthMeasureSpec
        int cCount = getChildCount();

        List<View> lineViews = new ArrayList<>();  //保存当前行的元素

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE) {
                continue;
            }

            //child也要确定宽高（建议尺寸 + mode）
            //取决于：
            //1.子控件在xml里面写30dp，match_parent，wrap_content
            //2.父控件当前的mode + 建议尺寸MeasureSpec
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            //子控件所占的空间包括margin
            int cWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int cHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + cWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {  //换行处理
                height += lineHeight;  //发生了换行，那么总高度累加上上一行的高度

                mLineHeight.add(lineHeight);  //将上一行的高度依次存入mLineHeight List中，这样就能得到每一行的高度了
                mAllView.add(lineViews);  //换行的时候，将上一行的view添加到mAllView中去

                //重置
                lineViews = new ArrayList<>();
                lineViews.add(child);

                lineWidth = cWidth;  //如果发生了换行，那么当前行宽就等于最新这个元素的cWidth
                lineHeight = cHeight;  //如果发生了换行，那么当前行高等于最新这个元素的高度

            } else {
                lineWidth += cWidth;  //如果没发生换行，那么累加本行宽
                lineHeight = Math.max(lineHeight, cHeight);

                lineViews.add(child);
            }

            if (i == cCount - 1) {
                height += lineHeight;  //如果遍历结束了，那么代表现在在最后一行了，height的高度是未包括最后一行的，所以要加上
                mLineHeight.add(lineHeight);
                mAllView.add(lineViews);
            }
        }

        //todo 前移优化
        if (mMaxLines < mLineHeight.size()) {
            height = getMaxLinesHeight();
        }

        //如果为确切值，那么就白算了。todo 可以前移优化
        if (modeHeight == MeasureSpec.EXACTLY) {
            height = sizeHeight;

        } else if (modeHeight == MeasureSpec.AT_MOST){  //如果有上限，那么不能超过上限
            height += getPaddingTop() + getPaddingBottom();
            height = Math.min(sizeHeight, height);

        } else if (modeHeight == MeasureSpec.UNSPECIFIED) {//如果无限制，那么就用height
            height += getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(sizeWidth, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtil.d("FlowLayout的onLayout()回调");

        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lineNums = mAllView.size();  //总行数
        for (int i = 0; i < lineNums; i++) {
            List<View> lineViews = mAllView.get(i);
            int lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineViews.size(); j++)  {
                View child = lineViews.get(j);

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = lp.leftMargin + left;
                int tc = lp.topMargin + top;
                int rc = lc + child.getMeasuredWidth();  //这里摆放child的位置时，不需要右和底margin
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                //用于给下一个元素的左位置，所以要加上右margin
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }

            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    private int getMaxLinesHeight() {
        int height = 0;
        for (int i = 0; i < mMaxLines; i++) {
            height += mLineHeight.get(i);
        }
        return height;
    }

    //动态添加View到FlowLayout中时（addView），没有设置LayoutParams，就使用这个默认的LayoutParams
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LogUtil.d("回调 generateDefaultLayoutParams()");
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    //inflater 1.activity的setContentView()时，xml中加载子空间到ViewGroup 2.Inflater加载
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        LogUtil.d("回调 generateLayoutParams() AttributeSet: " + attrs);
        return new MarginLayoutParams(getContext(), attrs);
    }

    //当checkLayoutParams()返回false时，会调用该方法。在该方法中做一些转换（不能强转）
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        LogUtil.d("回调 generateLayoutParams() LayoutParams: " + p);
        return new MarginLayoutParams(p);
    }

    //要确保这个LayoutParams p你能用才返回true；不能直接使用就返回false，这样就会将该LayoutParams传入上面这个方法
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        LogUtil.d("回调 checkLayoutParams(): " + p + ", 结果：" + (p instanceof MarginLayoutParams));
        return p instanceof MarginLayoutParams;
    }
}
