package com.example.utilsuser.qihang.ten

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsuser.R

class GradientDrawableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient_drawable)

        val tv = findViewById<TextView>(R.id.shape_tv)
        findViewById<Button>(R.id.add_shape_corner).setOnClickListener {
            val drawable = tv.background as GradientDrawable
            drawable.cornerRadius = 20f
        }
    }
}