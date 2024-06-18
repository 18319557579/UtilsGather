package com.example.utilsuser.qihang.ten

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsgather.logcat.LogUtil
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

        createBmpByColors()

        setScenery()

        extractAlpha()
        extractAlpha2()
    }

    private fun createBmpByColors() {
        val width = 300;
        val height = 200
        val colors = initColors(width, height)
        val bmp = Bitmap.createBitmap(colors, width, height, Bitmap.Config.ARGB_8888)

        findViewById<ImageView>(R.id.iv_set2).apply {
            setImageBitmap(bmp)
        }
    }

    private fun initColors(width: Int, height: Int) :IntArray {
        val colors = IntArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                val r = x * 255 / (width - 1)
                val g = y * 255 / (width - 1)
                val b = 255 - Math.min(r, g)
                val a = Math.max(r, g)
                colors[y * width + x] = Color.argb(a, r, g, b)
            }
        }
        return colors;
    }

    private fun setScenery() {
        val src = BitmapFactory.decodeResource(resources, R.drawable.scenery)
        val bitmap = Bitmap.createScaledBitmap(src, 300, 200, true)

        findViewById<ImageView>(R.id.iv_set3).apply {
            setImageBitmap(bitmap)
        }
    }

    private fun extractAlpha() {
        val srcBitmap = BitmapFactory.decodeResource(resources, R.drawable.cat_dog)

        val bitmap = Bitmap.createBitmap(srcBitmap.width, srcBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            color = Color.CYAN
        }
        canvas.drawBitmap(srcBitmap.extractAlpha(), 0f, 0f, paint)

        val iv = findViewById<ImageView>(R.id.iv_extract)
        iv.setImageBitmap(bitmap)

        srcBitmap.recycle()
    }

    private fun extractAlpha2() {
        val srcBitmap = BitmapFactory.decodeResource(resources, R.drawable.cat_dog)

        val blurMaskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.NORMAL)
        val alphaPaint = Paint().apply {
            maskFilter = blurMaskFilter
        }
        val offsetXY = IntArray(2)
        val alphaBmp = srcBitmap.extractAlpha(alphaPaint, offsetXY)
        LogUtil.d("offsetX: ${offsetXY[0]}, offsetY: ${offsetXY[1]}")

        val bitmap = Bitmap.createBitmap(alphaBmp.width, alphaBmp.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            color = Color.CYAN
        }
        canvas.drawBitmap(alphaBmp, 0f, 0f, paint)

        //绘制原图像
        canvas.drawBitmap(srcBitmap, -offsetXY[0].toFloat(), -offsetXY[1].toFloat(), null)

        val iv = findViewById<ImageView>(R.id.iv_extract2)
        iv.setImageBitmap(bitmap)

        srcBitmap.recycle()
    }
}