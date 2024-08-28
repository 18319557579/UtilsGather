package com.example.uioperate.custom_juejin_newki

import android.animation.ValueAnimator
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
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.annotation.StyleableRes
import androidx.core.graphics.withSave
import com.example.uioperate.R
import com.example.uioperate.custom_juejin_s10g.sp
import com.example.utilsgather.logcat.LogUtil
import kotlin.math.max

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
    private var mBgCirWidth: Float = 10f //圆环背景宽度

    //声明进度圆的画笔
    private lateinit var mCirPaint: Paint //画笔
    private var mCirColor = Color.YELLOW //颜色
    private var mCirWidth: Float = 18f //主圆的宽度

    //是否需要动画(默认需要动画)
    private var isAnim: Boolean = true

    //保留的小数位数(默认0位)
    private var mDigit: Int = 0

    //动画时间（默认一秒）
    private var mAnimTime: Int = 1000

    //进度值
//    private var mValue = 0
//    //最大值(默认为100)
//    private var mMaxValue = 100

//    private var mStartProgress = 0f
//    private var mTargetProgress = 1f
    private val mEndProgress = 1f  //结束进度永远为1
    private var mCurrentProgress = 0f

    //绘制的起始角度和滑过角度(默认从顶部开始绘制，绘制360度)
    private var mStartAngle: Float = 270f
    private var mSweepAngle: Float = 360f

    //绘制数值
    private lateinit var mValuePaint: TextPaint
    private var mValueSize = 25.sp
    private var mValueColor = Color.BLACK

    //绘制描述
    private var mHint = "进度"
    private lateinit var mHintPaint: TextPaint
    private var mHintSize = 13.sp
    private var mHintColor = Color.GRAY

    //阴影
    private var mShadowColor = Color.LTGRAY
    private var mShadowSize = 8f
    private var mShadowIsShow: Boolean = false

    //动画进度
//    private var mAnimPercent: Float = 0f

    //颜色渐变色
    private var isGradient = false
    private var mGradientColors: IntArray = intArrayOf(Color.RED, Color.GRAY, Color.BLUE)

    //属性动画
    private var mAnimator: ValueAnimator? = null

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
//        mValue = typedArray.getInt(R.styleable.MyCircleProgressView_value, mValue)
//        mMaxValue = typedArray.getInt(R.styleable.MyCircleProgressView_maxvalue, mMaxValue)
        mStartAngle = typedArray.getFloat(R.styleable.MyCircleProgressView_startAngle, mStartAngle)
        mSweepAngle = typedArray.getFloat(R.styleable.MyCircleProgressView_sweepAngle, mSweepAngle)
        mValueSize = typedArray.getDimension(R.styleable.MyCircleProgressView_valueSize, mValueSize)
        mValueColor = typedArray.getColor(R.styleable.MyCircleProgressView_valueColor, mValueColor)
        mHint = typedArray.getStringWithDefaultValue(R.styleable.MyCircleProgressView_hint, mHint)
        mHintSize = typedArray.getDimension(R.styleable.MyCircleProgressView_hintSize, mHintSize)
        mHintColor = typedArray.getColor(R.styleable.MyCircleProgressView_hintColor, mHintColor)
        mShadowColor = typedArray.getColor(R.styleable.MyCircleProgressView_shadowColor, mShadowColor)
        mShadowIsShow = typedArray.getBoolean(R.styleable.MyCircleProgressView_shadowShow, mShadowIsShow)
        mShadowSize = typedArray.getFloat(R.styleable.MyCircleProgressView_shadowSize, mShadowSize)
        isGradient = typedArray.getBoolean(R.styleable.MyCircleProgressView_isGradient, isGradient)
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
            it.right = centerPosition.x + raduis + maxCirWidth / 2
            it.bottom = centerPosition.y + raduis + maxCirWidth / 2
        }

        if (isGradient) {
            setupGradientCircle() //设置圆环画笔颜色渐变
        }
    }

    /**
     * 使用渐变色画圆
     */
    private fun setupGradientCircle() {
        mCirPaint.shader = SweepGradient(
            centerPosition.x,
            centerPosition.y,
            mGradientColors,
            null
        )
        // 渐变色如果使用ROUND的线头实在是太奇怪了，只能舍弃使用BUTT平头
        mCirPaint.strokeCap = Paint.Cap.BUTT
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*val rectPaint = Paint().apply {
            color = Color.RED
            strokeWidth = 3f
        }
        canvas.drawRect(mRectF, rectPaint)

        val pointPaint = Paint().apply {
            color = Color.GREEN
            strokeWidth = 5f
        }
        canvas.drawPoint(centerPosition.x, centerPosition.y, pointPaint)

        val outerPaint = Paint().apply {
            color = Color.BLUE
            strokeWidth = 3f
            style = Paint.Style.STROKE
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), outerPaint)*/

        drawText(canvas)
        drawCircle(canvas)
    }

    private fun drawText(canvas: Canvas) {
        // 绘制上方的数字
        canvas.drawText(
            CircleUtil.roundByScale((mCurrentProgress * 100).toDouble(), mDigit) + "%",
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

    private fun drawCircle(canvas: Canvas) {
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mBgCirPaint)

        if (mShadowIsShow) {
            mCirPaint.setShadowLayer(mShadowSize, 0f, 0f, mShadowColor)
        }

        if (isGradient) {
            gradientCorrector(canvas)
        } else {
            canvas.drawArc(mRectF, mStartAngle, mSweepAngle * mCurrentProgress, false, mCirPaint)
        }

    }

    private fun gradientCorrector(canvas: Canvas) {
        val eliminateDegrees = 90f
        canvas.withSave {
            canvas.rotate(-eliminateDegrees, centerPosition.x, centerPosition.y)
            canvas.drawArc(
                mRectF,
                mStartAngle + eliminateDegrees,
                mSweepAngle * mCurrentProgress,
                false,
                mCirPaint)
        }
    }

    fun setupValue(currentProgress: Float, targetProgress: Float) {
        if (!isAnim) {
            mCurrentProgress = targetProgress
            postInvalidate()
            return
        }
        startAnim(currentProgress, targetProgress)
    }

    private fun startAnim(currentProgress: Float, targetProgress: Float) {
        // 如果 mAnimator 不为 null，即已经有动画在了，先取消前面的
        mAnimator?.cancel()

        mCurrentProgress = currentProgress
        mAnimator = ValueAnimator.ofFloat(currentProgress, targetProgress).apply {
            duration = mAnimTime.toLong()
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                //得到当前的动画进度并赋值
                mCurrentProgress = it.animatedValue as Float
                LogUtil.d("打印当前进度值: $mCurrentProgress")
                //不停的重绘当前值-表现出动画的效果
                postInvalidate()
            }
        }
        mAnimator?.start()
    }
}