package com.example.uioperate.storage

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.uioperate.R
import com.example.uioperate.storage.image_selector.PhotoActivity
import com.example.uioperate.storage.image_selector.SelectionBean
import com.example.utilsgather.application_device_info.PackageInfoUtil
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader


class StorageActivity : AppCompatActivity() {
    val SAF_CODE = 100
    val SAF_CODE_DELETE = 101
    val STORAGE_MANAGE_CODE = 1000
    val TO_PHOTOACTIVITY_DELETE = 2000

    val TO_PHOTOACTIVITY_TRASH = 2001
    val TO_PHOTOACTIVITY_BIN = 2002
    val DELETE_CODE = 2100
    val TRASH_CODE = 2101


    val ivShow by lazy {
        findViewById<ImageView>(R.id.iv_show)
    }

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
                GuideItemEntity("应用自身外部存储的 files 目录下的子目录") {
                    //会在应用自身外部存储（机身 + sd卡）自动创建 DCIM 目录。下面的都是同理
                    //  /storage/emulated/0/Android/data/com.example.utilsuser/files/DCIM
                    //  /storage/7A9A8DFF9A8DB861/Android/data/com.example.utilsuser/files/DCIM
                    var dcims = getExternalFilesDirs(Environment.DIRECTORY_DCIM)
                    for (file in dcims) {
                        LogUtil.d("DIRECTORY_DCIM: ${file.path}")
                    }
                    LogUtil.d("\n")

                    //  /storage/emulated/0/Android/data/com.example.utilsuser/files/Music
                    //  /storage/7A9A8DFF9A8DB861/Android/data/com.example.utilsuser/files/Music
                    dcims = getExternalFilesDirs(Environment.DIRECTORY_MUSIC )
                    for (file in dcims) {
                        LogUtil.d("DIRECTORY_MUSIC: ${file.path}")
                    }
                    LogUtil.d("\n")

                    //  /storage/emulated/0/Android/data/com.example.utilsuser/files/Podcasts
                    //  /storage/7A9A8DFF9A8DB861/Android/data/com.example.utilsuser/files/Podcasts
                    dcims = getExternalFilesDirs(Environment.DIRECTORY_PODCASTS )
                    for (file in dcims) {
                        LogUtil.d("DIRECTORY_PODCASTS : ${file.path}")
                    }
                    LogUtil.d("\n")

                    //  /storage/emulated/0/Android/data/com.example.utilsuser/files/Ringtones
                    //  /storage/7A9A8DFF9A8DB861/Android/data/com.example.utilsuser/files/Ringtones
                    dcims = getExternalFilesDirs(Environment.DIRECTORY_RINGTONES )
                    for (file in dcims) {
                        LogUtil.d("DIRECTORY_RINGTONES : ${file.path}")
                    }
                    LogUtil.d("\n")

                    //  /storage/emulated/0/Android/data/com.example.utilsuser/files/Alarms
                    //  /storage/7A9A8DFF9A8DB861/Android/data/com.example.utilsuser/files/Alarms
                    dcims = getExternalFilesDirs(Environment.DIRECTORY_ALARMS )
                    for (file in dcims) {
                        LogUtil.d("DIRECTORY_ALARMS : ${file.path}")
                    }
                    LogUtil.d("\n")

                    //  /storage/emulated/0/Android/data/com.example.utilsuser/files/Notifications
                    //  /storage/7A9A8DFF9A8DB861/Android/data/com.example.utilsuser/files/Notifications
                    dcims = getExternalFilesDirs(Environment.DIRECTORY_NOTIFICATIONS )
                    for (file in dcims) {
                        LogUtil.d("DIRECTORY_NOTIFICATIONS : ${file.path}")
                    }
                    LogUtil.d("\n")

                    //  /storage/emulated/0/Android/data/com.example.utilsuser/files/Pictures
                    //  /storage/7A9A8DFF9A8DB861/Android/data/com.example.utilsuser/files/Pictures
                    dcims = getExternalFilesDirs(Environment.DIRECTORY_PICTURES )
                    for (file in dcims) {
                        LogUtil.d("DIRECTORY_PICTURES : ${file.path}")
                    }
                    LogUtil.d("\n")

                    //  /storage/emulated/0/Android/data/com.example.utilsuser/files/Movies
                    //  /storage/7A9A8DFF9A8DB861/Android/data/com.example.utilsuser/files/Movies
                    dcims = getExternalFilesDirs(Environment.DIRECTORY_MOVIES )
                    for (file in dcims) {
                        LogUtil.d("DIRECTORY_MOVIES : ${file.path}")
                    }
                    LogUtil.d("\n")

                    //  /storage/emulated/0/Android/data/com.example.utilsuser/files/Download
                    //  /storage/7A9A8DFF9A8DB861/Android/data/com.example.utilsuser/files/Download
                    dcims = getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS )
                    for (file in dcims) {
                        LogUtil.d("DIRECTORY_DOWNLOADS : ${file.path}")
                    }
                    LogUtil.d("\n")
                },
                GuideItemEntity("申请存储权限") {
                    ActivityCompat.requestPermissions(this@StorageActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                },

                GuideItemEntity("共享存储空间-媒体文件-直接构造路径") {
                    //获取目录：/storage/emulated/0/
                    val externalRoot = Environment.getExternalStorageDirectory()
                    LogUtil.d("输出根路径: $externalRoot")

                    val imagePath = externalRoot.absolutePath + File.separator +
                            Environment.DIRECTORY_DCIM + File.separator +
                            "hsf.jpg"

                    CoroutineScope(Job()).launch(Dispatchers.IO) {
                        //这一步必须要动态申请存储权限，否则得到的bitmap为null
                        val bitmap = BitmapFactory.decodeFile(imagePath)
                        LogUtil.d("打印一下bitmap: $bitmap")

                        withContext(Dispatchers.Main) {
                            ivShow.setImageBitmap(bitmap)
                        }
                    }

                },

                GuideItemEntity("共享存储空间-媒体文件-通过MediaStore获取路径: 遍历所有图片") {
                    CoroutineScope(Job()).launch(Dispatchers.IO) {
                        //这一步必须要动态申请存储权限，否则会闪退
                        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            null, null, null, null)
                        var bitmap: Bitmap? = null
                        while (cursor!!.moveToNext()) {
                            val dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                            val imagePath = cursor.getString(dataIndex)
                            LogUtil.d("打印图片路径: $imagePath")
                            bitmap = BitmapFactory.decodeFile(imagePath)

                            //在Android 9 以下，还没有分区存储，路径可以读也可以写
                            //在Android11及以上，可以用路径来读，但是不可以写
                            if (bitmap != null) {
                                LogUtil.d("打印图片信息: $bitmap, width: ${bitmap.width}, height: ${bitmap.height}")
                            } else {

                                //在Android 10上，如果没有禁用分区存储，且targetsdk >= 28，那么bitmap就为null，因为通过路径获得InputStream会报错
                                LogUtil.d("bitmap为null imagePath: $imagePath")
                            }
                        }

                        withContext(Dispatchers.Main) {
                            ivShow.setImageBitmap(bitmap)
                        }
                    }
                },

                GuideItemEntity("共享存储空间-媒体文件-通过MediaStore获取Uri: 遍历所有图片") {
                    CoroutineScope(Job()).launch(Dispatchers.IO) {
                        //这一步必须要动态申请存储权限，否则会闪退
                        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            null, null, null, null)
                        var bitmap: Bitmap? = null
                        while (cursor!!.moveToNext()) {
                            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                            val id = cursor.getLong(idIndex)
                            LogUtil.d("打印图片ID: $id")
                            val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                            LogUtil.d("打印图片Uri: $uri")

                            val fis = contentResolver.openInputStream(uri)
                            bitmap = BitmapFactory.decodeStream(fis)
                        }

                        withContext(Dispatchers.Main) {
                            ivShow.setImageBitmap(bitmap)
                        }
                    }
                },

                //通过SAF并不能直接拿到图片的路径，图片的信息封装在Uri里
                //通过SAF不需要存储权限
                GuideItemEntity("共享存储空间-媒体文件-通过SAF访问: 选择一张图片") {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        setType("image/*")
                    }
                    startActivityForResult(intent, SAF_CODE)
                },

                GuideItemEntity("共享存储空间-文档-通过SAF访问：选择一个文档（通过设置mimeTypes）") {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        setType("*/*")
                        val mimeTypes = arrayOf("text/html",
                            "text/css",
                            "application/javascript",
                            "application/pdf",
                            "text/plain",
                            "application/msword")
                        putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                    }
                    startActivityForResult(intent, SAF_CODE)
                },

                //外部存储的其他目录需要存储权限
                GuideItemEntity("其他目录-直接构造路径：创建一个目录，并在目录下创建一个文件") {
                    val externalRoot = Environment.getExternalStorageDirectory()
                    val imagePath = externalRoot.absolutePath + File.separator + "myDir"
                    LogUtil.d("拼凑出的路径: $imagePath")

                    val myDirFile = File(imagePath)
                    if (!myDirFile.exists()) {
                        myDirFile.mkdirs()
                    }

                    val myFile = File(myDirFile, "createAFile.txt")
                    val path = myFile.absolutePath
                    LogUtil.d("要创建的文件路径: $path")
                    writeFile(path)
                },
                GuideItemEntity("其他目录-直接构造路径：读取上面创建的文件中的内容") {
                    val externalRoot = Environment.getExternalStorageDirectory()
                    val imagePath = externalRoot.absolutePath + File.separator + "myDir"
                            LogUtil.d("拼凑出的路径: $imagePath")

                    val myFile = File(imagePath, "createAFile.txt")

                    readFile(myFile.absolutePath)
                },
                GuideItemEntity("用直接构造路径的方式访问DCIM目录下的文件") {
                    val externalRoot = Environment.getExternalStorageDirectory()
                    val imagePath = externalRoot.absolutePath + File.separator + "DCIM" + File.separator + "daisy.jpg"
                    LogUtil.d("拼凑出的路径: $imagePath")

                    val bitmap = BitmapFactory.decodeFile(imagePath)
                    ivShow.setImageBitmap(bitmap)
                },
                GuideItemEntity("用直接构造路径的方式访问DCIM目录下，在里面创建一个文件") {
                    val externalRoot = Environment.getExternalStorageDirectory()
                    val imagePath = externalRoot.absolutePath + File.separator + "DCIM" + File.separator + "newnewfile.txt"
                    writeFile(imagePath)
                },

                GuideItemEntity("插入图片到相册中，包括更新数据库与本地文件") {
                    CoroutineScope(Job()).launch(Dispatchers.IO) {
                        val inputStream = getImageInputStream(this@StorageActivity, R.drawable.dog)
                        insert2Album(inputStream, "dog.jpg")
                    }
                },

                /**
                 * 我发现，除了Android 10 以外，都可以遍历外部存储目录，且无需存储权限
                 * 而，Android 10 即使给了存储权限，也还是会闪退
                 */
                GuideItemEntity("遍历sdcard目录") {
                    CoroutineScope(Job()).launch(Dispatchers.IO) {
                        val file = Environment.getExternalStorageDirectory()
                        val list = file.listFiles()
                        for (item in list) {
                            LogUtil.d("fileName: ${item.name}")
                        }
                    }
                },

                /**
                 * 申请了存储权限，但是没有申请存储管理权限
                 *
                 * 1.Android10 以下，没问题
                 * 2.Android10及以上都闪退了
                 *
                 *
                 * 申请了管理存储权限后：
                 * Android11及以上的设备可以了（Android10压根没有这个权限）
                 *
                 */
                GuideItemEntity("往/sdcard/目录下写入文件") {
                    val rootFile = Environment.getExternalStorageDirectory()
                    CoroutineScope(Job()).launch(Dispatchers.IO) {
                        val file = File(rootFile, "hsfTest.txt")
                        val fos = FileOutputStream(file)
                        val content = "hsfTest world!!33"
                        fos.write(content.toByteArray())
                        fos.flush()
                        fos.close()
                    }
                },

                //我发现可申请管理存储权限后，可以不用申请管理权限了（猜测是管理存储权限更加高级）
                GuideItemEntity("申请存储管理权限") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        if (Environment.isExternalStorageManager()) {
                            LogUtil.d("已有存储管理权限")
                        } else {
                            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                            startActivityForResult(intent, STORAGE_MANAGE_CODE)
                        }
                    } else {
                        LogUtil.d("在Android11以下，还没有这个权限")
                    }
                },
                GuideItemEntity("企图通过SAF删除一张照片") {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        setType("image/*")
                    }
                    startActivityForResult(intent, SAF_CODE_DELETE)
                },

                GuideItemEntity("跳转PhotoActivity，删除一张图片") {
                    val intent = Intent(this, PhotoActivity::class.java)

                    //列
                    val projection = arrayOf(
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media._ID
                    )

                    //全部图片
                    val where = (MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?")

                    //指定格式
                    val whereArgs = arrayOf("image/jpeg", "image/png", "image/jpg")

                    //排序方式
                    val sort = MediaStore.Images.Media.DATE_MODIFIED + " desc "

                    val selectionBean = SelectionBean(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        where,
                        whereArgs,
                        sort
                    )

                    intent.putExtra(PhotoActivity.SELECTION_BEAN, selectionBean)
                    startActivityForResult(intent, TO_PHOTOACTIVITY_DELETE)
                },
                GuideItemEntity("跳转PhotoActivity，放置到回收站") {
                    val intent = Intent(this, PhotoActivity::class.java)

                    //列
                    val projection = arrayOf(
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media._ID
                    )

                    //全部图片
                    val where = (MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?")

                    //指定格式
                    val whereArgs = arrayOf("image/jpeg", "image/png", "image/jpg")

                    //排序方式
                    val sort = MediaStore.Images.Media.DATE_MODIFIED + " desc "

                    val selectionBean = SelectionBean(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        where,
                        whereArgs,
                        sort
                    )

                    intent.putExtra(PhotoActivity.SELECTION_BEAN, selectionBean)
                    startActivityForResult(intent, TO_PHOTOACTIVITY_TRASH)
                },
                //我发现查找回收站中的内容，始终为空
                GuideItemEntity("查找回收站中的内容") {
                    val intent = Intent(this, PhotoActivity::class.java)

                    //列
                    val projection = arrayOf(
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.IS_TRASHED
                    )

                    //全部图片
                    val where = MediaStore.Images.Media.IS_TRASHED + "=?";

                    //指定格式
                    val whereArgs = arrayOf("1")

                    //排序方式
                    val sort = null

                    val selectionBean = SelectionBean(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        where,
                        whereArgs,
                        sort
                    )

                    intent.putExtra(PhotoActivity.SELECTION_BEAN, selectionBean)
                    startActivityForResult(intent, TO_PHOTOACTIVITY_BIN)
                },

                //申请了MANAGE_MEDIA权限后，前面的删除和移动到回收站中将不会弹出确认框
                //这个权限适配的意义不大，主要用于那些要大量修改媒体文件的情况
                GuideItemEntity("申请管理媒体权限") {
                    //todo 根本就没有方法可以知道是否授予了管理媒体权限
                    val manageMedia = ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_MEDIA) == PackageManager.PERMISSION_GRANTED
                    LogUtil.d("是否已经授权: $manageMedia")

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12 或更高
                        val intent = Intent(Settings.ACTION_REQUEST_MANAGE_MEDIA)
                        startActivity(intent)
                    }

                },
            )
        )
    }

    fun getImageInputStream(context: Context, drawableId: Int): InputStream {
        // 获取Resources对象
        val resources = context.resources

        // 通过资源ID获取Drawable对象
        val drawable = resources.getDrawable(drawableId, context.theme)

        // 将Drawable转换为Bitmap
        val bitmap = (drawable as BitmapDrawable).bitmap

        // 创建一个ByteArrayOutputStream
        val stream = ByteArrayOutputStream()

        // 将Bitmap压缩成PNG格式，100表示不压缩，数据存储到stream中
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        // 使用ByteArrayOutputStream创建ByteArrayInputStream
        val inputStream = ByteArrayInputStream(stream.toByteArray())

        // 返回InputStream
        return inputStream
    }

    //fileName为需要保存到相册的图片名
    private fun insert2Album(inputStream: InputStream, fileName: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName)

            //Android 10.0(含)之后，DATA 被废弃，取而代之的是使用MediaStore.Images.ImageColumns.RELATIVE_PATH，表示相对路径
            //比如指定RELATIVE_PATH为Environment.DIRECTORY_PICTURES，表示之后的图片将会放到Environment.DIRECTORY_PICTURES目录下。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.ImageColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            //Android 10.0之前，MediaStore.Images.ImageColumns.DATA 字段记录的是图片的绝对路径
            else {
                val dstPath = Environment.getExternalStorageDirectory().path + File.separator +
                        Environment.DIRECTORY_PICTURES + File.separator +
                        fileName
                put(MediaStore.Images.ImageColumns.DATA, dstPath)
            }
        }
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
        write2File(uri, inputStream)
    }

    //uri 关联着待写入的文件
    //inputStream 表示原始的文件流
    private fun write2File(uri: Uri, inputStream: InputStream) {
        val outputStream = contentResolver.openOutputStream(uri)!!
        val inMid = ByteArray(1024)

        var len = inputStream.read(inMid)
        while (len != -1) {
            outputStream.write(inMid, 0, len)
            outputStream.flush()
            len = inputStream.read(inMid)
        }
        inputStream.close()
        outputStream.close()
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
            LogUtil.d("读取出错了: $e")
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
            LogUtil.d("写入出错了: $e")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            SAF_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = data!!.data!!
                    LogUtil.d("打印选择的图片的uri：$uri")

                    val fis = contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(fis)
                    ivShow.setImageBitmap(bitmap)

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    LogUtil.d("取消选择")
                }
            }
            SAF_CODE_DELETE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = data!!.data!!
                    LogUtil.d("打印选择的图片的uri：$uri")

                    /*CoroutineScope(Job()).launch(Dispatchers.IO) {
                        val deletedNum = contentResolver.delete(uri, null, null)
                        LogUtil.d("删除的行数: $deletedNum")
                    }*/

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        //注意，哪怕有了MANAGE_MEDIA权限，这里的删除代码还是要这么写的，只是没有确认框弹出了，系统会默认用户确认

                        // 创建删除请求
                        val pendingIntent = MediaStore.createDeleteRequest(contentResolver, listOf(uri))

                        // 发送 Intent 提示用户批准删除操作
                        startIntentSenderForResult(pendingIntent.intentSender, 200, null, 0, 0, 0, null)
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    LogUtil.d("取消选择")
                }
            }

            STORAGE_MANAGE_CODE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        LogUtil.d("访问所有文件权限申请成功")
                    } else {
                        LogUtil.d("访问所有文件权限 还是没有")
                    }
                }
            }

            //这里MediaStore中查找出来的图片包括了一张前面插入到其他目录中的小狗
            TO_PHOTOACTIVITY_DELETE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val returnedData = data?.getStringExtra("data_return")
                    val returnedDataUri = data?.getParcelableExtra<Uri>("data_return_uri")
                    LogUtil.d("打印返回的数据: $returnedData, Uri: $returnedDataUri")

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        // 创建删除请求
                        val pendingIntent = MediaStore.createDeleteRequest(contentResolver, listOf(returnedDataUri))

                        // 发送 Intent 提示用户批准删除操作
                        startIntentSenderForResult(pendingIntent.intentSender, DELETE_CODE, null, 0, 0, 0, null)
                    }
                }
            }
            TO_PHOTOACTIVITY_TRASH -> {
                if (resultCode == Activity.RESULT_OK) {
                    val returnedData = data?.getStringExtra("data_return")
                    val returnedDataUri = data?.getParcelableExtra<Uri>("data_return_uri")
                    LogUtil.d("打印返回的数据: $returnedData, Uri: $returnedDataUri")

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        // 创建删除请求
                        //value值为true代表移入回收站，value值为false代表移出回收站
                        val pendingIntent = MediaStore.createTrashRequest(contentResolver, listOf(returnedDataUri), true)

                        // 发送 Intent 提示用户批准删除操作
                        startIntentSenderForResult(pendingIntent.intentSender, TRASH_CODE, null, 0, 0, 0, null)
                    }
                }
            }
            DELETE_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    LogUtil.d("删除成功")
                } else {
                    LogUtil.d("删除失败")
                }
            }
            TRASH_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    LogUtil.d("移动到回收站成功")
                } else {
                    LogUtil.d("移动到回收站失败")
                }
            }
        }
    }
}