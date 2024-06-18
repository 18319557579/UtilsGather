package com.example.utilsuser.qihang.ten

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsgather.file_system.FileInfoGainUtil
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ScaleTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_type)

        setScaleImageView()

        setGreenImageView()

        setCompressImageView()
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

    private fun setCompressImageView() {
        val iv_1 = findViewById<ImageView>(R.id.img_compress_1)
        val iv_2 = findViewById<ImageView>(R.id.img_compress_2)

        val bmp = BitmapFactory.decodeResource(resources, R.drawable.cat)
        iv_1.setImageBitmap(bmp)

        val bos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.WEBP_LOSSY, 10, bos)
        val bytes = bos.toByteArray()
        val bmp1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        iv_2.setImageBitmap(bmp1)

        saveBmp(bmp)
    }

    private fun saveBmp(bitmap: Bitmap) {
        val fileDir = FileInfoGainUtil.internalStoragePath(this) +
                File.separator +
                "/lavor.webp"

        val file = File(fileDir)
        if (file.exists())
            file.delete()

        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 10, outputStream)
        outputStream.flush()
        outputStream.close()
    }
}