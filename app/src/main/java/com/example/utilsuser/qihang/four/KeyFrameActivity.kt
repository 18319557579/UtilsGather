package com.example.utilsuser.qihang.four

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsuser.R

class KeyFrameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_frame)
    }

    fun startClick(view: View) {
        val ivPhone = findViewById<ImageView>(R.id.iv_phone)

        val frame0 = Keyframe.ofFloat(0f, 0f)
        val frame1 = Keyframe.ofFloat(0.1f, -20f).apply {
            interpolator = BounceInterpolator()
        }
        val frame2 = Keyframe.ofFloat(0.2f, 20f)
        val frame3 = Keyframe.ofFloat(0.3f, -20f)
        val frame4 = Keyframe.ofFloat(0.4f, 20f)
        val frame5 = Keyframe.ofFloat(0.5f, -20f).apply {
            interpolator = LinearInterpolator()
        }
        val frame6 = Keyframe.ofFloat(0.6f, 20f)
        val frame7 = Keyframe.ofFloat(0.7f, -20f)
        val frame8 = Keyframe.ofFloat(0.8f, 20f)
        val frame9 = Keyframe.ofFloat(0.9f, -20f)
        val frame10 = Keyframe.ofFloat(1f).apply {
//            value = 0f
        }


        val frameHolder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4,
            frame5, frame6, frame7, frame8, frame9, frame10)


        val scaleXframe0 = Keyframe.ofFloat(0f, 1f)
        val scaleXframe1 = Keyframe.ofFloat(0.1f, 1.1f)
        val scaleXframe9 = Keyframe.ofFloat(0.9f, 1.1f)
        val scaleXframe10 = Keyframe.ofFloat(1f, 1f)
        val frameHolder2 = PropertyValuesHolder.ofKeyframe("ScaleX", scaleXframe0,
            scaleXframe1, scaleXframe9, scaleXframe10)

        val scaleYframe0 = Keyframe.ofFloat(0f, 1f)
        val scaleYframe1 = Keyframe.ofFloat(0.1f, 1.1f)
        val scaleYframe9 = Keyframe.ofFloat(0.9f, 1.1f)
        val scaleYframe10 = Keyframe.ofFloat(1f, 1f)
        val frameHolder3 = PropertyValuesHolder.ofKeyframe("ScaleY", scaleYframe0,
            scaleYframe1, scaleYframe9, scaleYframe10)


        ObjectAnimator.ofPropertyValuesHolder(ivPhone, frameHolder, frameHolder2, frameHolder3).apply {
            duration = 3000
            start()
        }


    }
}