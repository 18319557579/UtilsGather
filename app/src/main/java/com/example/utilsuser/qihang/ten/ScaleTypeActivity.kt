package com.example.utilsuser.qihang.ten

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R

class ScaleTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_type)

        setScaleImageView()

        setGreenImageView()
    }

    private fun setScaleImageView() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.cat_dog)

        val iv1 = findViewById<ImageView>(R.id.img1)
        iv1.setImageBitmap(bitmap)
        val density = bitmap.density
        LogUtil.d("density: $density, width: ${bitmap.width}, height: ${bitmap.height}")

        val scaledDensity = density * 2
        bitmap.density = scaledDensity
        findViewById<ImageView>(R.id.img2).setImageBitmap(bitmap)
        LogUtil.d("density: ${bitmap.density}, width: ${bitmap.width}, height: ${bitmap.height}")
    }

    private fun setGreenImageView() {
        val srcBmp = BitmapFactory.decodeResource(resources, R.drawable.dog)
        findViewById<ImageView>(R.id.img_green_1).setImageBitmap(srcBmp)

        val desBmp = srcBmp.copy(Bitmap.Config.ARGB_8888, true)
        for (h in 0 until srcBmp.height) {
            for (w in 0 until srcBmp.width) {
                val originColor = srcBmp.getPixel(w, h)
                val red = Color.red(originColor)
                val alpha = Color.alpha(originColor)
                var green = Color.green(originColor)
                val blue = Color.blue(originColor)

                if (green < 200)
                    green += 30

                desBmp.setPixel(w, h, Color.argb(alpha, red, green, blue))
            }
        }
        findViewById<ImageView>(R.id.img_green_2).setImageBitmap(desBmp)
    }
}