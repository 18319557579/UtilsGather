package com.example.utilsuser.qihang.ten

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsuser.R

class CreateBitmapViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_bitmap_view)

        val src = BitmapFactory.decodeResource(resources, R.drawable.dog)

        val matrix = Matrix().apply {
            setScale(2f, 1f)
        }
        val cuteBmp = Bitmap.createBitmap(src, src.width / 3, src.height / 3,
            src.width / 3, src.height / 3, matrix, true)

        findViewById<ImageView>(R.id.iv_set).setImageBitmap(cuteBmp)
    }
}