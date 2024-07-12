package com.example.uioperate.storage

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.utilsgather.application_device_info.PackageInfoUtil
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class StorageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        GuideSettings.set(
            findViewById(R.id.lv_launcher),
            arrayOf(
                GuideItemEntity("内部存储，的各种路径") {
                    val packageName = PackageInfoUtil.getPackageName(this)
                    val filePath = "/data/data/$packageName/files"
                    val filePath2 = "/data/user/0/$packageName/files"
                    LogUtil.d("拼接files路径: $filePath")
                    LogUtil.d("拼接files路径2: $filePath2")

                    //其实下面这些内部存储的路径，都可以用类似上面的方式进行拼接

                    val filesDir: File = filesDir
                    LogUtil.d("api获得files路径: ${filesDir.path}")

                    val cacheDir: File = cacheDir
                    LogUtil.d("api获得cache路径: ${cacheDir.path}")

                    val codeCacheDir: File = codeCacheDir
                    LogUtil.d("api获得code_cache路径: ${codeCacheDir.path}")

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        val dataDir = dataDir
                        LogUtil.d("api获得内部存储的应用根目录: ${dataDir.path}")
                    }

                    //该目录不会被备份（避免备份到云端）
                    LogUtil.d("4: ${noBackupFilesDir}")


                    //没搞清楚什么作用（可能压根就不是内部存储的）
                    LogUtil.d("1: ${packageCodePath}")
                    LogUtil.d("2: ${packageResourcePath}")
                    LogUtil.d("3: ${obbDirs}")
                    LogUtil.d("5: ${externalCacheDir}")
                    LogUtil.d("6: ${externalMediaDirs}")
                },
                GuideItemEntity("") {

                },
            )
        )
    }

    //从文件读取
    private fun readFile(filePath: String) {
        if (TextUtils.isEmpty(filePath)) return

        try {
            val file: File = File(filePath)
            val fileInputStream: FileInputStream = FileInputStream(file)
            val bis: BufferedInputStream = BufferedInputStream(fileInputStream)
            val readContent = ByteArray(1024)
            var readLen = 0
            while (readLen != -1) {
                readLen = bis.read(readContent, 0, readContent.size)
                if (readLen > 0) {
                    val content = String(readContent)
                    LogUtil.d("read content:" + content.substring(0, readLen))
                }
            }
            fileInputStream.close()
        } catch (e: Exception) {
        }
    }

    //写入文件
    private fun writeFile(filePath: String) {
        if (TextUtils.isEmpty(filePath)) return

        try {
            val file = File(filePath)
            val fileOutputStream: FileOutputStream = FileOutputStream(file)
            val bos: BufferedOutputStream = BufferedOutputStream(fileOutputStream)
            val writeContent = "hello world\n"
            bos.write(writeContent.toByteArray())
            bos.flush()
            bos.close()
        } catch (e: java.lang.Exception) {
        }
    }
}