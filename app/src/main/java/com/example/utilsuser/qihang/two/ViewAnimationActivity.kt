package com.example.utilsuser.qihang.two

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.Point
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
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
    val view: View by lazy {
        findViewById(R.id.tv)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

//        bounce()
//        rotate()
//        scan()
//        startMusicPlayer()
//        translationTest()
//        characterChange()
//        ballInit()
        objectAnimatorInit()
    }

    class FallingBallEvaluator : TypeEvaluator<Point> {
        val point = Point()
        override fun evaluate(fraction: Float, startValue: Point, endValue: Point): Point {
            point.x = (startValue.x + fraction * (endValue.x - startValue.x)).toInt()
            if (fraction * 2 <= 1) {
                point.y = (startValue.y + fraction * 2 * (endValue.y - startValue.y)).toInt()
            } else {
                point.y = endValue.y
            }
            return point
        }
    }

    private fun objectAnimatorInit() {
        val ballImg = findViewById<ImageView>(R.id.ball_img2)
        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            ObjectAnimator.ofObject(ballImg, "fallingPos", FallingBallEvaluator(),
                Point(0, 0), Point(500, 500)).apply {
                    duration = 2000
                    start()
            }
            /*AnimatorInflater.loadAnimator(this@ViewAnimationActivity, R.animator.value_animator).apply {
                (this as ValueAnimator).addUpdateListener {
                    val offset = it.getAnimatedValue() as Int
                    ballImg.layout(offset, offset, offset + ballImg.width, offset + ballImg.height)
                }
                start()
            }*/
            /*AnimatorInflater.loadAnimator(this@ViewAnimationActivity, R.animator.object_animator).apply {
                (this as ObjectAnimator).target = ballImg
                start()
            }*/
        }
    }

    /*private fun ballInit() {
//        val ballImg = findViewById<ImageView>(R.id.ball_img)
        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            ObjectAnimator.ofFloat(ballImg, "translationX", 0f, 200f, -200f, 0f).apply {
                duration = 2000
                start()
            }

            *//*ValueAnimator.ofObject(FallingBallEvaluator(), Point(0, 0), Point(500, 500)).apply {
                addUpdateListener {
                    val mCurPoint = it.getAnimatedValue() as Point
                    ballImg.layout(mCurPoint.x, mCurPoint.y, mCurPoint.x + ballImg.width, mCurPoint.y + ballImg.height)
                }
                duration = 2000
                start()
            }*//*
        }
    }*/

    class CharEvaluator : TypeEvaluator<Char> {
        override fun evaluate(
            fraction: Float,
            startValue: Char,
            endValue: Char
        ): Char {
            val startint = startValue.code  //Char转Int
            val endint = endValue.code  //Char转Int
            val curint = (startint + fraction * (endint - startint)).toInt()  //Float转Int
            val result = curint.toChar()  //Int转Char
            return result
        }
    }

    /*private fun characterChange() {
        val tv = findViewById<TextView>(R.id.tv)
        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            ValueAnimator.ofObject(CharEvaluator(), 'A', 'Z').apply {
                addUpdateListener {
                    val text = it.getAnimatedValue()
                    tv.text = text.toString()
                }
                duration = 10000
                interpolator = AccelerateInterpolator()
                start()
            }
        }


    }*/

    /*private fun translationTest() {
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
    }*/

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