package com.example.uioperate.picture_selection

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
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
                val imageInputStream = contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(imageInputStream)
                mBinding.ivPicSelected.setImageBitmap(bitmap)

                val job = Job()
                val scope = CoroutineScope(job)
                scope.launch(Dispatchers.IO) {
                    //1.发起网络请求，获得返回的数据
                    val jsonResult = UploadFileTask().uploadImage("https://postman-echo.com/post", it,
                        this@PictureSelectionActivity)
                    LogUtil.d("打印最终结果: $jsonResult")

                    //2.获得base64数据
                    val base64Value = JSONObject(jsonResult).run {
                        val jsonObject = getJSONObject("files")

                        val keys = jsonObject.keys()

                        //Kotlin中的if 和 else 都是有返回值的
                        if (keys.hasNext()) {  //只取第一张图片
                            val key = keys.next()
                            jsonObject.getString(key)
                        } else {
                            //这里的返回值为Nothing
                            return@launch
                        }
                    }

                    //3.去除头部信息，提取实际数据
                    val base64Data: String = base64Value.substring(base64Value.indexOf(",") + 1)

                    //4.base64转为bitmap
                    val decodedByte = Base64Util.base64ToBitmap(base64Data)

                    //5.展示图片
                    withContext(Dispatchers.Main) {
                        mBinding.ivPicSelectedNetword.setImageBitmap(decodedByte)
                    }

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
}