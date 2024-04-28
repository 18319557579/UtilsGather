package com.example.utilsuser.qihang.three

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.FloatEvaluator
import android.animation.IntEvaluator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.core.animation.addListener
import androidx.core.animation.doOnRepeat
import androidx.core.animation.doOnStart
import com.example.utilsuser.R

class LoadingImageView : ImageView {
    private var mTop = 0
    private var mCurImgIndex = 0
    private var mImgCount = 3

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mTop = top
    }

    private fun init() {
        ValueAnimator.ofInt(0, 100, 0).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            duration = 2000
            interpolator = TimeInterpolator { input -> 3 - input }
            addUpdateListener {
                val dx = it.getAnimatedValue() as Int
                top = mTop - dx
            }
            addListener {
                doOnRepeat {
                    mCurImgIndex++
                    setImageDrawable(resources.getDrawable(when(mCurImgIndex % mImgCount) {
                        0 -> R.drawable.pic_1
                        1 -> R.drawable.pic_2
                        2 -> R.drawable.pic_3
                        else -> {0}
                    }))
                }
                doOnStart {
                    setImageDrawable(resources.getDrawable(R.drawable.pic_1))
                }
            }
            /*doOnRepeat {
                mCurImgIndex++
                setImageDrawable(resources.getDrawable(when(mCurImgIndex % mImgCount) {
                    0 -> R.drawable.pic_1
                    1 -> R.drawable.pic_2
                    2 -> R.drawable.pic_3
                    else -> {0}
                }))
            }
            doOnStart {
                setImageDrawable(resources.getDrawable(R.drawable.pic_1))
            }*/
            /*addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    setImageDrawable(resources.getDrawable(R.drawable.pic_1))
                }

                override fun onAnimationEnd(animation: Animator) {

                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {
                    mCurImgIndex++
                    setImageDrawable(resources.getDrawable(when(mCurImgIndex % mImgCount) {
                        0 -> R.drawable.pic_1
                        1 -> R.drawable.pic_2
                        2 -> R.drawable.pic_3
                        else -> {0}
                    }))
                }

            })*/
            setEvaluator(FloatEvaluator())
            start()
        }
    }
}