package com.example.uioperate.storage

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.utilsgather.application_device_info.PackageInfoUtil
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

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
                GuideItemEntity("写入文件到files目录下") {
                    CoroutineScope(Job()).launch {
                        val filesDir: File = filesDir
                        val myFile = File(filesDir, "themyfile.txt")
                        val path = myFile.absolutePath
                        writeFile(path)
                    }
                },
                GuideItemEntity("读取files目录下的文件") {
                    CoroutineScope(Job()).launch {
                        val filesDir: File = filesDir
                        val myFile = File(filesDir, "themyfile.txt")
                        val path = myFile.absolutePath
                        readFile(path)
                    }
                },
                GuideItemEntity("创建数据库的同时，会产生databases目录") {
                    //这说明数据库目录是自动创建的
                    val dbHelper = TestSQLiteOpenHelper(this, TestSQLiteOpenHelper.DATABASE_NAME, 1)
                    dbHelper.getWritableDatabase()
                },
                GuideItemEntity("获得 TestDatabase 数据库的路径") {
                    val dbHelper = TestSQLiteOpenHelper(this, TestSQLiteOpenHelper.DATABASE_NAME, 1)
                    dbHelper.getWritableDatabase()

                    val databaseFile = getDatabasePath(TestSQLiteOpenHelper.DATABASE_NAME)
                    LogUtil.d("数据库 TestDatabase 的路径: ${databaseFile.path}")
                },
                GuideItemEntity("创建 SharedPreferences 目录") {
                    //sp的目录也是自动创建的
                    val sp = getSharedPreferences("test_sp", Context.MODE_PRIVATE)
                    val edit = sp.edit()
                    edit.putInt("id", 1)
                    edit.putString("name", "Daisy")
                    edit.commit()
                },
                GuideItemEntity("使用 openFileOutput()") {
                    val filename = "example.txt"
                    val fileContents = "Hello Internal!"
                    CoroutineScope(Job()).launch {
                        //其实就是获得 files 目录下的某路径的文件输出流
                        val fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE)
                        fileOutputStream.write(fileContents.toByteArray())
                        fileOutputStream.close()
                    }
                },
                GuideItemEntity("使用 openFileInput()") {
                    val filename = "example.txt"

                    //其实就是获得 files 目录下的某路径的文件输入流
                    val inputStream = openFileInput(filename)
                    val inputStreamReader = InputStreamReader(inputStream)
                    val bufferedReader = BufferedReader(inputStreamReader)
                    val stringBuilder = StringBuilder()
                    var line: String? = null

                    CoroutineScope(Job()).launch {
                        line = bufferedReader.readLine()
                        while ( line != null ) {
                            stringBuilder.append(line).append("\n")
                            line = bufferedReader.readLine()
                        }

                        inputStream.close()
                        val fileContents = stringBuilder.toString()
                        LogUtil.d("读取出的内容: $fileContents")
                    }
                },
                //调用下面这些方法，是可以自动创建 files 和 data 目录
                GuideItemEntity("应用自身外部存储的 files + data 目录") {
                    //例子：
                    //  /storage/emulated/0/Android/media/com.example.utilsuser
                    //  /storage/7A9A8DFF9A8DB861/Android/media/com.example.utilsuser
                    val externalMediaDirs = getExternalMediaDirs()
                    for (file in externalMediaDirs) {
                        LogUtil.d("getExternalMediaDirs: ${file.path}")
                    }
                    LogUtil.d("\n")

                    //应用自身外部存储的 files 目录
                    //例子：/storage/emulated/0/Android/data/com.example.utilsuser/files
                    val externalFilesDir = getExternalFilesDir(null)
                    LogUtil.d("getExternalFilesDir: ${externalFilesDir?.path}")
                    LogUtil.d("\n")

                    //应用自身外部存储的 files 目录（包括SD卡）
                    //例子：
                    //  /storage/emulated/0/Android/data/com.example.utilsuser/files
                    //  /storage/7A9A8DFF9A8DB861/Android/data/com.example.utilsuser/files
                    val fileList = getExternalFilesDirs(null)
                    for (file in fileList) {
                        LogUtil.d("getExternalFilesDirs: ${file.path}")
                    }
                    LogUtil.d("\n")

                    //应用自身外部存储的 cache 目录
                    //例子：/storage/emulated/0/Android/data/com.example.utilsuser/cache
                    val externalCacheDir = getExternalCacheDir()
                    LogUtil.d("getExternalCacheDir: ${externalCacheDir?.path}")
                    LogUtil.d("\n")

                    //应用自身外部存储的 cache 目录（包括SD卡）
                    //例子：
                    //  /storage/emulated/0/Android/data/com.example.utilsuser/cache
                    //  /storage/7A9A8DFF9A8DB861/Android/data/com.example.utilsuser/cache
                    val externalCacheDirs = getExternalCacheDirs()
                    for (file in externalCacheDirs) {
                        LogUtil.d("getExternalCacheDirs: ${file.path}")
                    }
                    LogUtil.d("\n")
                },
            )
        )
    }

    //从文件读取（通过字节流）
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

    //写入文件（通过字节流）
    private fun writeFile(filePath: String) {
        if (TextUtils.isEmpty(filePath)) return

        try {
            val file = File(filePath)
            val fileOutputStream: FileOutputStream = FileOutputStream(file)
            val bos: BufferedOutputStream = BufferedOutputStream(fileOutputStream)
            val writeContent = "hello world!\n"
            bos.write(writeContent.toByteArray())
            bos.flush()
            bos.close()
        } catch (e: java.lang.Exception) {
        }
    }
}