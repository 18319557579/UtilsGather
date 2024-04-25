package com.example.utilsuser.qihang.two

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsuser.R

class ViewAnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

//        bounce()
//        rotate()
//        scan()
//        startMusicPlayer()
        translationTest()
    }

    private fun translationTest() {
        val tv = findViewById<TextView>(R.id.tv)
        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            TranslateAnimation(Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 400f,
                Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 100F).also {
                    it.fillAfter = true
                    it.duration = 1000
                    tv.startAnimation(it)
            }
        }

        tv.setOnClickListener {
            Toast.makeText(this@ViewAnimationActivity, "clicked me", Toast.LENGTH_SHORT).show()
        }
    }

    /*private fun startMusicPlayer() {
        val image = findViewById<ImageView>(R.id.frame_image)
        val anim = AnimationDrawable()
        for (i in 1 .. 14) {
            val id = resources.getIdentifier("list_icon_gif_playing$i", "drawable", packageName)
            val drawable = resources.getDrawable(id)
            anim.addFrame(drawable, 60)
        }
        anim.isOneShot = false
        image.background = anim
        anim.start()

        findViewById<Button>(R.id.start_btn).setOnClickListener {
            anim.start()
        }
        findViewById<Button>(R.id.stop_btn).setOnClickListener {
            anim.stop()
        }
    }*/

    /*private fun scan() {
        val animation1 = AnimationUtils.loadAnimation(this, R.anim.scale_alpha_anim)
        val animation2 = AnimationUtils.loadAnimation(this, R.anim.scale_alpha_anim)
        val animation3 = AnimationUtils.loadAnimation(this, R.anim.scale_alpha_anim)
        val animation4 = AnimationUtils.loadAnimation(this, R.anim.scale_alpha_anim)

        val circle1 = findViewById<ImageView>(R.id.circle1)
        val circle2 = findViewById<ImageView>(R.id.circle2)
        val circle3 = findViewById<ImageView>(R.id.circle3)
        val circle4 = findViewById<ImageView>(R.id.circle4)

        findViewById<TextView>(R.id.start_scan).setOnClickListener {
            circle1.startAnimation(animation1)

            animation2.startOffset = 750
            circle2.startAnimation(animation2)
//            animation2.startOffset = 0

            animation3.startOffset = 1500
            circle3.startAnimation(animation3)
//            animation3.startOffset = 0

            animation4.startOffset = 2250
            circle4.startAnimation(animation4)
//            animation4.startOffset = 0
        }
    }*/

    /*private fun rotate() {
        val imageView = findViewById<ImageView>(R.id.loading)

        RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f).also {
                it.repeatCount = Animation.INFINITE
                it.duration = 2000
                it.interpolator = LinearInterpolator()
                imageView.startAnimation(it)
        }

    }*/

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