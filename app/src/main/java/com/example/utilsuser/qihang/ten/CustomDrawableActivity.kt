package com.example.utilsuser.qihang.ten

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsuser.R

class CustomDrawableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_drawable)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avator)

        val iv = findViewById<ImageView>(R.id.img_custom)
        val drawable = CustomDrawable(bitmap)
        iv.setImageDrawable(drawable)

        val tv = findViewById<TextView>(R.id.tv_custom)
        tv.setBackgroundDrawable(drawable)
    }
}