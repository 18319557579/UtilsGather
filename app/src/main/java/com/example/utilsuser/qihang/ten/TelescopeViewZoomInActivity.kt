package com.example.utilsuser.qihang.ten

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsgather.file_system.FilePathUtil
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import java.io.File

class TelescopeViewZoomInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telescope_view2)

        /*val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.dog, options)
        LogUtil.d("bitmap: $bitmap")
        LogUtil.d("realWidth: ${options.outWidth}, realHeight: ${options.outHeight}, mimeType: ${options.outMimeType}")*/

        //当使用这种方式后，就会抛弃原本的目录的dpi
        val options = BitmapFactory.Options()
            /*.apply {
            inDensity = 1
            inTargetDensity = 1
            inPreferredConfig = Bitmap.Config.RGB_565
        }*/
        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.scenery, options)
        LogUtil.d("bmpWidth: ${bitmap.width}, bmpHeight: ${bitmap.height}, bmpMemorySize: ${bitmap.byteCount}")
        LogUtil.d("是否可以改变: ${bitmap.isMutable}")

        val path = FilePathUtil.internalStoragePath(this) + File.separator + "scenery.png"
        bitmap = BitmapFactory.decodeFile(path)
        LogUtil.d("bmpWidth: ${bitmap.width}, bmpHeight: ${bitmap.height}, bmpMemorySize: ${bitmap.byteCount}")
        LogUtil.d("是否可以改变: ${bitmap.isMutable}")
    }

    private fun calSampleSize(options: BitmapFactory.Options, dstWidth: Int, dstHeight: Int) : Int{
        val rawWidth = options.outWidth
        val rawHeight = options.outHeight
        var inSampleSize = 1
        if (rawWidth > dstHeight || rawHeight > dstHeight) {
            val ratioHeight = rawHeight / dstHeight
            val ratioWidth = rawWidth / dstWidth
            inSampleSize = Math.min(ratioWidth, ratioHeight)
        }
        return inSampleSize
    }
}