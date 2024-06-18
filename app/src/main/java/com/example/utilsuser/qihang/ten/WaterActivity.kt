package com.example.utilsuser.qihang.ten

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsuser.R

class WaterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.dog)
        val watermark = BitmapFactory.decodeResource(resources, R.drawable.watermark)
        val result = createWaterBitmap(bitmap, watermark)

        val imageView = findViewById<ImageView>(R.id.img)
        imageView.setImageBitmap(result)
    }

    private fun createWaterBitmap(src: Bitmap, watermark: Bitmap) : Bitmap?{
        if (src == null) {
            return null;
        }

        val w = src.width
        val h = src.height
        val ww = watermark.width
        val wh = watermark.height

        val newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val cv = Canvas(newb)
        cv.drawBitmap(src, 0f, 0f, null)
        cv.drawBitmap(watermark, w - ww + 5f, h - wh + 5f, null)

        return newb
    }
}