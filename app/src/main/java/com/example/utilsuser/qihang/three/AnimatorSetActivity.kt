package com.example.utilsuser.qihang.three

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnRepeat
import androidx.core.animation.doOnStart
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R

class AnimatorSetActivity : AppCompatActivity() {
    lateinit var animatorSet: AnimatorSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator_set)
    }

    fun clickButtonCancel(view: View) {
        animatorSet.cancel()
    }

    fun clickButton(view: View) {
        val tv1 = findViewById<TextView>(R.id.tv_1)
        val tv2 = findViewById<TextView>(R.id.tv_2)

        val tv1BgAnimator = ObjectAnimator.ofInt(tv1, "BackgroundColor",
            0xffff00ff.toInt(), 0xffffff00.toInt(), 0xffff00ff.toInt()).apply {
//            repeatCount = ValueAnimator.INFINITE
        }
        val tv1TranslateY = ObjectAnimator.ofFloat(tv1, "translationY", 0f, 400f, 0f).apply {
//            repeatCount = ValueAnimator.INFINITE
        }

        val tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0f, 400f, 0f).apply {
            repeatCount = ValueAnimator.INFINITE
        }

        //implicit receiver
        animatorSet = AnimatorSet().apply {
            playTogether(tv1BgAnimator, tv2TranslateY)
            duration = 2000
            setTarget(tv2)
            /*addListener {

                doOnEnd {
                    LogUtil.d("animator end")
                }
                doOnCancel {
                    LogUtil.d("animator cancel")
                }
                doOnRepeat {
                    LogUtil.d("animator repeat")
                }
                doOnStart {
                    LogUtil.d("animator start")
                }
            }*/
            /*doOnStart {
                LogUtil.d("animator start")
            }
            doOnEnd {
                LogUtil.d("animator end")
            }
            doOnCancel {
                LogUtil.d("animator cancel")
            }*/
            /*addListener(onStart = (a) -> {

        })
            addListener {
                doOnStart {

                }
            }
            addListener((a) -> {}, (a) -> {}, (a) -> {}, (a) -> {})*/
            /*addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    LogUtil.d("animator start")
                }

                override fun onAnimationEnd(animation: Animator) {
                    LogUtil.d("animator end")
                }

                override fun onAnimationCancel(animation: Animator) {
                    LogUtil.d("animator cancel")
                }

                override fun onAnimationRepeat(animation: Animator) {
                    LogUtil.d("animator repeat")
                }

            })*/
            addListener(
                onEnd = { animator ->
                    LogUtil.d("Animation ended")
                },
                onStart = { animator ->
                    LogUtil.d("Animation started")
                },
                onCancel = { animator ->
                    LogUtil.d("Animation canceled")
                },
            )

            start()
        }
    }
}