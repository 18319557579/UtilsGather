package com.example.uioperate.custom_marquee_textview

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
import androidx.annotation.ColorInt
import androidx.annotation.Px
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

class MarqueeTextView @JvmOverloads constructor(
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


    private var mHandler: MyHandler? = null

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

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val myHeightSize = CustomViewTextUtil.getWholeCharacterHeight(mTextPaint) + paddingTop + paddingBottom
        val measureHeight = when(heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(myHeightSize.toInt(), heightSize)
            else -> myHeightSize.toInt()
        }

        setMeasuredDimension(widthSize, measureHeight);
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

        if (mHandler == null) {
            mHandler = MyHandler(this)
            startInternal()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(mFinalDrawText, mXLocation, CustomViewTextUtil.getBaselineY(height, mTextPaint), mTextPaint)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        LogUtil.d("回调 onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        LogUtil.d("回调 onDetachedFromWindow")
    }

    private class MyHandler(view: MarqueeTextView) : Handler(Looper.getMainLooper()) {
        companion object {
            internal const val WHAT_RUN = 1000
        }
        private val mRef = WeakReference(view)
        override fun handleMessage(msg: Message) {
            if (msg.what == WHAT_RUN) {
                mRef.get()?.apply {
                    mXLocation -= speed

                    val absLocation = abs(mXLocation)
                    if (mXLocation < 0 && mSingleContentWidth <= absLocation) {
                        mXLocation = mSingleContentWidth - absLocation
                    }

                    invalidate()
                    sendEmptyMessageDelayed(WHAT_RUN, 16)
                }
            }
        }
    }

    private fun startInternal() {
        mHandler?.sendEmptyMessage(MyHandler.WHAT_RUN)
    }
}