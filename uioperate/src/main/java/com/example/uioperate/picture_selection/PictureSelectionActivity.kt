package com.example.uioperate.picture_selection

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.databinding.ActivityPictureSelectionBinding
import com.example.utilsgather.encoding.Base64Util
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class PictureSelectionActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPictureSelectionBinding

    private val REQEUST_COE = 8001

    //必须要提前注册
    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            LogUtil.d("打印图片的uri: $it")
            it?.also {
                val job = Job()
                val scope = CoroutineScope(job)
                scope.launch {
                    showPicture(it)
                    val jsonResult = uploadPicture(it)
                    val base64Data = getBase64Data(jsonResult) ?: return@launch
                    val bitmap = base64ToBitmap(base64Data)
                    showNetworkPicture(bitmap)
                }
            }
        }
    private val takePictureLauncherMulti = registerForActivityResult(PictureMultiple()) {
        LogUtil.d("打印图片的uri: $it")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPictureSelectionBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
//        registerForActivityResult
        GuideSettings.set(
            mBinding.lvPicSelectionOperation, arrayOf(
                GuideItemEntity("单选图片") {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setType("image/*")
                    startActivityForResult(intent, REQEUST_COE)
                },
                GuideItemEntity("单选图片(用新的方式)") {
                    takePictureLauncher.launch("image/*")
                },
                GuideItemEntity("多选图片(用新的方式，并自定义)") {
                    takePictureLauncherMulti.launch(null)
                },
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQEUST_COE -> {
                if (resultCode == Activity.RESULT_OK) {
                    LogUtil.d("图片选择成功")
                    val uri = data?.data ?: ""
                    LogUtil.d("打印图片的uri: $uri")

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    LogUtil.d("图片选择失败")
                }
            }
        }
    }

    suspend fun showPicture(uri: Uri) {
        withContext(Dispatchers.Main) {
            val imageInputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(imageInputStream)
            mBinding.ivPicSelected.setImageBitmap(bitmap)
        }
    }

    suspend fun uploadPicture(uri: Uri) : String{
        return withContext(Dispatchers.IO) {
            //UploadFileTask_ChatGPT和UploadFileTask2_ChatGPT都是可以的
            UploadFileTask2_ChatGPT()
                .uploadImage("https://postman-echo.com/post", uri,
                this@PictureSelectionActivity)
        }
    }

    suspend fun getBase64Data(json: String) : String?{
        return withContext(Dispatchers.IO) {
            //2.获得base64数据
            val base64Value = JSONObject(json).run {
                val jsonObject = getJSONObject("files")

                val keys = jsonObject.keys()

                //Kotlin中的if 和 else 都是有返回值的
                if (keys.hasNext()) {  //只取第一张图片
                    val key = keys.next()
                    jsonObject.getString(key)
                } else {
                    return@withContext null
                }
            }

            //3.去除头部信息，提取实际数据
            base64Value.substring(base64Value.indexOf(",") + 1)
        }
    }

    suspend fun base64ToBitmap(base64Data: String): Bitmap {
        return withContext(Dispatchers.IO) {
            Base64Util.base64ToBitmap(base64Data)
        }
    }

    suspend fun showNetworkPicture(bitmap: Bitmap) {
        withContext(Dispatchers.Main) {
            mBinding.ivPicSelectedNetword.setImageBitmap(bitmap)
        }
    }
}