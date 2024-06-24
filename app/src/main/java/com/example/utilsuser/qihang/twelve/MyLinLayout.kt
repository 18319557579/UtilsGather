package com.example.utilsuser.qihang.twelve

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class MyLinLayout : ViewGroup {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)
        val measureWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        var height = 0
        var width = 0
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            val lp = child.layoutParams as MarginLayoutParams
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin

            height += childHeight
            width = Math.max(childWidth, width)
        }

        setMeasuredDimension(
            if (measureWidthMode == MeasureSpec.EXACTLY) measureWidth else width,
            if (measureHeightMode == MeasureSpec.EXACTLY) measureHeight else height
        )

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var top = 0
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)

            val lp = child.layoutParams as MarginLayoutParams
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin

            child.layout(0, top, childWidth, top + childHeight)
            top += childHeight
        }
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }
}