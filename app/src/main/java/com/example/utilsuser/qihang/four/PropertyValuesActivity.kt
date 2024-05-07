package com.example.utilsuser.qihang.four

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsuser.R
import com.example.utilsuser.qihang.two.ViewAnimationActivity

class PropertyValuesActivity : AppCompatActivity() {
    lateinit var btnStart: Button
    lateinit var tvRectangle: TextView
    lateinit var tvChar: TextViewChar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_values)

//        init()
        init2()

    }

    private fun init2() {
        btnStart = findViewById(R.id.btn_start)
        tvChar = findViewById(R.id.tv_char)
        println()
        btnStart.setOnClickListener {
            /*val charHolder = PropertyValuesHolder.ofObject("charText",
                MyCharEvaluator(),
                'A', 'Z')
            ObjectAnimator.ofPropertyValuesHolder(tvChar, charHolder).apply {
                duration = 3000
                interpolator = AccelerateInterpolator()
                start()
            }*/
            ObjectAnimator.ofObject(tvChar, "CharText", MyCharEvaluator(),
                'A', 'Z').apply {
                    /*addUpdateListener {
                        val text = it.getAnimatedValue()
                        tvChar.text = text.toString()
                    }*/
                start()
            }
        }
    }

    class MyCharEvaluator : TypeEvaluator<Char> {
        override fun evaluate(fraction: Float, startValue: Char, endValue: Char): Char {
            val startInt = startValue.code
            val endInt = endValue.code
            val curInt = (startInt + fraction * (endInt - startInt)).toInt()
            val result = curInt.toChar()
            return result
        }
    }

    /*private fun init1() {
        btnStart = findViewById(R.id.btn_start)
        tvRectangle = findViewById(R.id.tv_rectangle)

        btnStart.setOnClickListener {
            val rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f,
                -20f, 20f, 10f, -10f, 0f)
            val alphaHolder = PropertyValuesHolder.ofFloat("alpha", 0.1f, 1f, 0.1f, 1f)
            ObjectAnimator.ofPropertyValuesHolder(tvRectangle, rotationHolder, alphaHolder).apply {
                duration = 3000
                start()
            }
        }
    }*/
}