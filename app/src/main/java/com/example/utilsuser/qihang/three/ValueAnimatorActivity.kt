package com.example.utilsuser.qihang.three

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R

class ValueAnimatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_animator)

//        startValueAnimator()
//        translationTest()
//        LinearInterpolator
    }

    private fun startValueAnimator() {
        ValueAnimator.ofInt(0, 400).apply {
            duration = 1000
            addUpdateListener {animation ->
                val curValue = animation.getAnimatedValue() as Int
                LogUtil.d("curValue: $curValue")
            }
            start()
        }
    }

    /*private fun translationTest() {
        val tv = findViewById<TextView>(R.id.tv)
        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            ValueAnimator.ofFloat(0f, 400f, 50f, 300f).apply {
                duration = 3000
                addUpdateListener {animation ->
                    val curValue = animation.getAnimatedValue() as Float
                    tv.layout(curValue.toInt(), curValue.toInt(),
                        (curValue + tv.width).toInt(), (curValue + tv.height).toInt()
                    )
                }
                start()
            }
        }

        tv.setOnClickListener {
            Toast.makeText(this@ValueAnimatorActivity, "clicked me", Toast.LENGTH_SHORT).show()
        }
    }*/
}