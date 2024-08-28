package com.example.uioperate.custom_juejin_newki

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.SweepGradient
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StyleableRes
import com.example.uioperate.R

class MyCircleProgressView(context: Context, attrs: AttributeSet? = null) : View(context, attrs){
    //是否开启抗锯齿
    private var antiAlias: Boolean = true

    // 圆心位置
    private var centerPosition = PointF()

    //半径
    private var raduis = 0f

    //声明边界矩形
    private var mRectF = RectF()

    //声明背景圆画笔
    private lateinit var mBgCirPaint: Paint //画笔
    private var mBgCirColor = Color.GRAY //颜色
    private var mBgCirWidth: Float = 15f //圆环背景宽度

    //声明进度圆的画笔
    private lateinit var mCirPaint: Paint //画笔
    private var mCirColor = Color.YELLOW //颜色
    private var mCirWidth: Float = 15f //主圆的宽度

    //是否需要动画(默认需要动画)
    private var isAnim: Boolean = true

    //保留的小数位数(默认2位)
    private var mDigit: Int = 2

    //动画时间（默认一秒）
    private var mAnimTime: Int = 1000

    //进度值
    private var mValue = 0
    //最大值(默认为100)
    private var mMaxValue: Float = 100f

    //绘制的起始角度和滑过角度(默认从顶部开始绘制，绘制360度)
    private var mStartAngle: Float = 270f
    private var mSweepAngle: Float = 360f

    //绘制数值
    private lateinit var mValuePaint: TextPaint
    private var mValueSize = 15f
    private var mValueColor = Color.BLACK

    //绘制描述
    private var mHint: String? = null
    private lateinit var mHintPaint: TextPaint
    private var mHintSize = 15f
    private var mHintColor = Color.GRAY

    //绘制进度的后缀-默认为百分号%
    private var mUnit = "%"

    //阴影
    private var mShadowColor = Color.BLACK
    private var mShadowSize = 8f
    private var mShadowIsShow: Boolean = false

    //颜色渐变色
    private var isGradient = false
    private var mGradientColors: IntArray? = intArrayOf(Color.RED, Color.GRAY, Color.BLUE)
    private var mGradientColor = 0
    private var mSweepGradient: SweepGradient? = null

    init {
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        initAttrs(context, attrs)
        initPaint()
    }

    /**
     * 初始化自定义属性
     */
    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCircleProgressView)
        isAnim = typedArray.getBoolean(R.styleable.MyCircleProgressView_isanim, isAnim)
        mDigit = typedArray.getInt(R.styleable.MyCircleProgressView_digit, mDigit)
        mBgCirColor = typedArray.getColor(R.styleable.MyCircleProgressView_mBgCirColor, mBgCirColor)
        mBgCirWidth = typedArray.getDimension(R.styleable.MyCircleProgressView_mBgCirWidth, mBgCirWidth)
        mCirColor = typedArray.getColor(R.styleable.MyCircleProgressView_mCirColor, mCirColor)
        mCirWidth = typedArray.getDimension(R.styleable.MyCircleProgressView_mCirWidth, mCirWidth)
        mAnimTime = typedArray.getInt(R.styleable.MyCircleProgressView_animTime, mAnimTime)
        mValue = typedArray.getInt(R.styleable.MyCircleProgressView_value, mValue)
        mMaxValue = typedArray.getFloat(R.styleable.MyCircleProgressView_maxvalue, mMaxValue)
        mStartAngle = typedArray.getFloat(R.styleable.MyCircleProgressView_startAngle, mStartAngle)
        mSweepAngle = typedArray.getFloat(R.styleable.MyCircleProgressView_sweepAngle, mSweepAngle)
        mValueSize = typedArray.getDimension(R.styleable.MyCircleProgressView_valueSize, mValueSize)
        mValueColor = typedArray.getColor(R.styleable.MyCircleProgressView_valueColor, mValueColor)
        mHint = typedArray.getString(R.styleable.MyCircleProgressView_hint)
        mHintSize = typedArray.getDimension(R.styleable.MyCircleProgressView_hintSize, mHintSize)
        mHintColor = typedArray.getColor(R.styleable.MyCircleProgressView_hintColor, mHintColor)
        mUnit = typedArray.getStringWithDefaultValue(R.styleable.MyCircleProgressView_unit, mUnit)
        mShadowColor = typedArray.getColor(R.styleable.MyCircleProgressView_shadowColor, mShadowColor)
        mShadowIsShow = typedArray.getBoolean(R.styleable.MyCircleProgressView_shadowShow, mShadowIsShow)
        mShadowSize = typedArray.getFloat(R.styleable.MyCircleProgressView_shadowSize, mShadowSize)
        isGradient = typedArray.getBoolean(R.styleable.MyCircleProgressView_isGradient, isGradient)
        mGradientColor = typedArray.getResourceId(R.styleable.MyCircleProgressView_gradient, mGradientColor)
        if (mGradientColor != 0) {
            mGradientColors = resources.getIntArray(mGradientColor)
        }
        typedArray.recycle()
    }

    /**
     * 初始化画笔
     */
    private fun initPaint() {
        //圆画笔（主圆的画笔设置）
        mCirPaint = Paint().apply {
            isAntiAlias = antiAlias //是否开启抗锯齿
            style = Paint.Style.STROKE //画笔样式
            strokeCap = Paint.Cap.ROUND  //笔刷样式（圆角的效果）
            strokeWidth = mCirWidth //画笔宽度
            color = mCirColor//画笔颜色
        }

        //背景圆画笔（一般和主圆一样大或者小于主圆的宽度）
        mBgCirPaint = Paint().apply {
            isAntiAlias = antiAlias
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = mBgCirWidth
            color = mBgCirColor
        }

        //初始化主题文字的字体画笔
        mValuePaint = TextPaint().apply {
            isAntiAlias = antiAlias  //是否抗锯齿
            textAlign = Paint.Align.CENTER //从中间向两边绘制，不需要再次计算文字
            textSize = mValueSize  //字体大小
            color = mValueColor  //字体颜色
        }

        //初始化提示文本的字体画笔
        mHintPaint = TextPaint().apply {
            isAntiAlias = antiAlias
            textAlign = Paint.Align.CENTER
            textSize = mHintSize
            color = mHintColor
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerPosition.x = w / 2f
        centerPosition.y = h / 2f

        // 用两个圆中较宽的来做尺度
        val maxCirWidth = Math.max(mCirWidth, mBgCirWidth)
        // 用长、宽两个方向中较小的作为内圆的宽度
        val minWidth = Math.min(
            w - paddingLeft - paddingRight - 2 * maxCirWidth,
            h - paddingBottom - paddingTop - 2 * maxCirWidth
        )

        // 内圆的半径就定下来了
        raduis = minWidth / 2

        mRectF.also {
            it.left = centerPosition.x - raduis - maxCirWidth / 2
            it.top = centerPosition.y - raduis - maxCirWidth / 2
            it.right = centerPosition.x + raduis - maxCirWidth / 2
            it.bottom = centerPosition.y + raduis - maxCirWidth / 2
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawText(canvas)
    }

    private fun drawText(canvas: Canvas) {
        // 绘制上方的数字
        canvas.drawText(
            mValue.toString() + mUnit,
            centerPosition.x,
            centerPosition.y,
            mValuePaint
        )

        // 绘制下方的提示
        if (mHint == null) {
            return
        }
        val fontMetrics = mHintPaint.fontMetrics
        // 两行文字的 baseline 间距
        val lineSpace = fontMetrics.bottom - fontMetrics.top + fontMetrics.leading
        canvas.drawText(
            mHint!!,
            centerPosition.x,
            centerPosition.y + lineSpace,
            mHintPaint
        )
    }
}