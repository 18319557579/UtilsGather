package com.example.utilsgather.cutomerview.flow_optimize

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import java.util.jar.Attributes
import kotlin.math.max

class OptimizedFlowLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr){

    //子元素水平间隔
    private var itemHorizontalSpacing = 20
    //子元素纵向间距
    private var itemVerticalSpacing = 20
    //记录所有的子View（可见性非GONE）的集合
    private val allLineViews = ArrayList<ArrayList<View>>()
    //所有行的行高的集合
    private val lineHeights = ArrayList<Int>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        allLineViews.clear()
        lineHeights.clear()

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        // 流式布局允许的最大宽度
        val maxWidth = widthSize
        var lineWidth = 0
        var maxLineWidth = 0
        var lineHeight = 0
        var totalHeight = 0
        val childCount = childCount
        var lineViews = ArrayList<View>()
        var lineCount = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == View.GONE)
                continue

            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childMeasuredWidth = child.measuredWidth
            val childMeasuredHeight = child.measuredHeight
            val lp = child.layoutParams as MarginLayoutParams
            //当前子View与上一个子View的间隔，如果为一行的第一个元素则没有间隔
            val actualItemHorizontalSpacing = if (lineWidth == 0) 0 else itemHorizontalSpacing
            val actualChildWidth = childMeasuredWidth + lp.leftMargin + lp.rightMargin  //这里实际占用的宽度加上了左右margin
            val actualChildHeight = childMeasuredHeight + lp.topMargin + lp.bottomMargin  //这里实际占用的高度加上了上下margin

            //判断是否需要换行
                //在本行还可以放置一个子View
            if (lineWidth + actualItemHorizontalSpacing + actualChildWidth
                    <= maxWidth - paddingLeft - paddingRight) {
                lineWidth += actualItemHorizontalSpacing + actualChildWidth
                lineHeight = max(lineHeight, actualChildHeight )
                lineViews.add(child)

                //在本行不可以再放置一个子View，因此需要换行
            } else {
                maxLineWidth = max(lineWidth, maxLineWidth)
                lineCount++
                //如果换行后，行总数为1，代表现在是第二行，那么总行高不需要加上
                totalHeight += lineHeight + if (lineCount == 1) 0 else itemVerticalSpacing
                lineHeights.add(lineHeight)
                allLineViews.add(lineViews)

                //重置
                lineWidth = actualChildWidth
                lineHeight = actualChildHeight
                lineViews = ArrayList<View>()
                lineViews.add(child)
            }
            if (i == childCount - 1) {
                maxLineWidth = max(lineWidth, maxLineWidth)
                lineCount++
                totalHeight += lineHeight + if (lineCount == 1) 0 else itemVerticalSpacing
                lineHeights.add(lineHeight)
                allLineViews.add(lineViews)
            }
        }

        maxLineWidth += paddingLeft + paddingRight
        totalHeight += paddingTop + paddingBottom

        val measureWidth = if (widthMode == MeasureSpec.EXACTLY) widthSize else maxLineWidth
        val measureHeight = if (heightMode == MeasureSpec.EXACTLY) heightSize else totalHeight
        setMeasuredDimension(measureWidth, measureHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val lineCount = allLineViews.size
        var childLeft = paddingLeft
        var childTop = paddingTop
        for (i in 0 until lineCount) {
            val lineViews = allLineViews[i]  //拿出该行的元素
            val lineHeight = lineHeights[i]  //得到该行的高度位置
            for (j in 0 until lineViews.size) {
                val child = lineViews[j]

                val lp = child.layoutParams as MarginLayoutParams
                childLeft += lp.leftMargin

                val childMeasuredWidth = child.measuredWidth
                val childMeasuredHeight = child.measuredHeight
                child.layout(childLeft, childTop + lp.topMargin, childLeft + childMeasuredWidth, childTop + lp.topMargin + childMeasuredHeight)
                childLeft += childMeasuredWidth + itemHorizontalSpacing
            }
            //遍历完一行后，下一行的纵坐标为：前面的总行高 + 垂直间距
            childTop += lineHeight + itemVerticalSpacing
            //遍历完一行后，下一行的第一个元素还是在0的位置开始布局
            childLeft = paddingLeft
        }
    }

    //inflater 1.activity的setContentView()时，xml中加载子控件到FlowLayout 2.inflate()方法加载的控件添加到FlowLayout时
    //默认的实现只会从 AttributeSet 里解析 layout_width 和 layout_height 这两个属性，这里重写的目的是解析 margin 属性。
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    //动态添加View到FlowLayout中时（addView），没有设置LayoutParams，就会调用该方法获得默认的LayoutParams
    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }
}