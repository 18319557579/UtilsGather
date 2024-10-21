package com.example.uioperate.custom_marquee_textview

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.graphics.withSave
import com.example.uioperate.R
import com.example.utilsgather.kotlin.spread.getStringWithDefaultValue
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsgather.ui.CustomViewUtil
import com.example.utilsgather.ui.custom_view.CustomViewTextUtil
import java.lang.ref.WeakReference
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.roundToInt

class MarqueeTextViewThree @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){
    companion object {
        const val BLANK = " "
    }

    var mText = ""

    @ColorInt
    var mTextColor = Color.BLACK

    @Px
    var mTextSize = 12f

    private lateinit var mTextPaint: Paint

    // 单个文本间的间隔
    @Px
    var textItemDistance = 50f

    // 单个显示内容的宽度
    private var mSingleContentWidth: Float = 0f
    private lateinit var mSingleContent: String

    // 最终绘制显示的文本
    private lateinit var mFinalDrawText: String

    private var mXLocation = 0f //文本的x坐标

    var speed = 5f

    private var mObjectAnimator: ObjectAnimator? = null

    fun setMXLocation(progress: Float) {
        mXLocation = -(mSingleContentWidth * progress)
        invalidate()
    }

    init {
        initAttrs(context, attrs)
        initPaint()
        initData()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.MarqueeTextView).apply {
            mText = getStringWithDefaultValue(R.styleable.MarqueeTextView_android_text, mText)
            mTextColor = getColor(R.styleable.MarqueeTextView_android_textColor, mTextColor)
            mTextSize = getDimension(R.styleable.MarqueeTextView_android_textSize, mTextSize)
        }.recycle()
    }

    private fun initPaint() {
        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = mTextColor
            textSize = mTextSize
            density = resources.displayMetrics.density
        }
    }

    private fun initData() {
        mSingleContent = (mText + getItemEndBlank()).also { singleContent ->
            mSingleContentWidth = CustomViewTextUtil.getMeasureTextWidth(singleContent, mTextPaint)
        }

        // 计算出单个内容的宽度（包括了尾部的空白字符串）

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        LogUtil.d("回调onMeasure \n" + CustomViewUtil.getWidthHeightInfo(widthMeasureSpec, heightMeasureSpec))

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val myHeightSize = CustomViewTextUtil.getWholeCharacterHeight(mTextPaint) + paddingTop + paddingBottom
        val measureHeight = when(heightMode) {
            MeasureSpec.EXACTLY -> heightSize  // 如果是确切的，那么直接使用给的
            MeasureSpec.AT_MOST -> min(myHeightSize.toInt(), heightSize)  // 如果有上限，不超过上限的话就用我们测量的，否则只能使用上限
            else -> myHeightSize.toInt()  // 如果不约束，那么直接使用我们的
        }

        val measureWidth = when(widthMode) {
            MeasureSpec.EXACTLY -> widthSize  // 如果是确切的，那么直接使用给的
            MeasureSpec.AT_MOST -> widthSize  // 如果宽度有上限，那直接使用上限吧
            else -> mSingleContentWidth.toInt() + paddingLeft + paddingRight  // 如果不约束，那么直接使用我们的
        }

        setMeasuredDimension(measureWidth, measureHeight);
    }

    /**
     * 用户设置的间隔除以一个空格字符串的宽度，空格字符串的数量，加到文本的末尾
     */
    private fun getItemEndBlank(): String {
        // 一个空格的宽度
        val oneBlankWidth = CustomViewTextUtil.getMeasureTextWidth(BLANK, mTextPaint)
        // 算出空格的数量
        val blankCount = (textItemDistance / oneBlankWidth).roundToInt()  // 四舍五入策略
        return StringBuilder(blankCount).apply {
            repeat(blankCount) {
                append(BLANK)
            }
        }.toString()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        LogUtil.d("回调onSizeChanged")

        val singleContent = mSingleContent

        // 向上取整后 + 1
        val maxVisibleCount = ceil(width / mSingleContentWidth).toInt() + 1

        // 计算出最终显示的文本内容
        mFinalDrawText = StringBuilder(singleContent.length * maxVisibleCount).apply {
            repeat(maxVisibleCount) {
                append(singleContent)
            }
        }.toString()

        if (mObjectAnimator == null) {
            mObjectAnimator = ObjectAnimator.ofFloat(this, "mXLocation", 0f, 1f).apply {
                setDuration(5000)
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.RESTART
                interpolator = LinearInterpolator()
                start()
            }
        }

    }

    override fun onDraw(canvas: Canvas) {
        canvas.withSave {
            // 略掉四边的padding，只留下中间显示的部分
            clipRect(paddingLeft, paddingTop, measuredWidth - paddingRight, measuredHeight - paddingBottom)
            // 实际的高度
            val actualHeight = measuredHeight - paddingTop - paddingBottom
            // 注意，开始的起点要加上paddingLeft，因为左右的padding是显示不了的
            // 注意，算出来baselineY之后，要加上paddingTop，这样才能实现在显示的范围内使用垂直居中
            drawText(mFinalDrawText, mXLocation + paddingLeft,
                CustomViewTextUtil.getBaselineY(actualHeight, mTextPaint) + paddingTop, mTextPaint)
        }

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        LogUtil.d("回调 onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        LogUtil.d("回调 onDetachedFromWindow")
    }
}