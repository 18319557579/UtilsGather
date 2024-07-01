package com.example.uioperate.picture_selection

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.databinding.ActivityPictureSelectionBinding
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil


class PictureSelectionActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPictureSelectionBinding

    private val REQEUST_COE = 8001

    //必须要提前注册
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
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