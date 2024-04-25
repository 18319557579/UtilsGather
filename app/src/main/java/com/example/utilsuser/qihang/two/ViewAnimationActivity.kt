package com.example.utilsuser.qihang.two

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsuser.R

class ViewAnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_animation)

//        bounce()
        rotate()
    }

    private fun rotate() {
        val imageView = findViewById<ImageView>(R.id.loading)

        RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f).also {
                it.repeatCount = Animation.INFINITE
                it.duration = 2000
                it.interpolator = LinearInterpolator()
                imageView.startAnimation(it)
        }

    }

    /*private fun bounce() {
        val imageView = findViewById<ImageView>(R.id.img_scenery)

        ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).also {
                it.fillAfter = true
            it.interpolator = BounceInterpolator()
            it.duration = 6000
            imageView.startAnimation(it)
        }
    }*/

    /*fun btnToClick(view: View) {
        findViewById<TextView>(R.id.tv).apply {
            TranslateAnimation(Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 500f,
                Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 0f).also {
                    it.duration = 1000
                    it.fillAfter = true
                    it.interpolator = AnticipateOvershootInterpolator(4f)
                    startAnimation(it)
            }

//            val alpha_Anim = AlphaAnimation(1.0f, 0.1f)

            *//*val rotate_Anim = RotateAnimation(0f, 720f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
                    duration = 5000
                    fillAfter = true
                    interpolator = LinearInterpolator()
            }

            val scale_Anim = ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
                    duration = 700
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            startAnimation(rotate_Anim)
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                        }

                    })
            }
            startAnimation(scale_Anim)*//*

            *//*AnimationSet(true).also {
                it.addAnimation(alpha_Anim)
                it.addAnimation(scale_Anim)
                it.addAnimation(rotate_Anim)

                it.duration = 3000
                it.fillAfter = true

                startAnimation(it)
            }*//*


            *//*RotateAnimation(0f, -650f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f).also {
                    it.duration = 3000
                    it.fillAfter = true
                    startAnimation(it)
            }*//*

            *//*AlphaAnimation(1.0f, 0.1f).let {
                it.duration = 3000
                it.fillBefore = true
                startAnimation(it)
            }*//*

            *//*ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, 0.5f, 0.5f).also {
                it.duration = 700
                startAnimation(it)
            }*//*

            *//*ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).also {
                    it.duration = 700
                    startAnimation(it)
            }*//*

            *//*val scaleAnim = ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            scaleAnim.duration = 700
            startAnimation(scaleAnim)*//*

            *//*startAnimation(
                AnimationUtils.loadAnimation(this@ViewAnimationActivity, R.anim.scaleanim)
            )*//*
        }
    }*/
}