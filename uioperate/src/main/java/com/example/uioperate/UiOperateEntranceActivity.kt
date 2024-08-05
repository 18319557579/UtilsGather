package com.example.uioperate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.databinding.ActivityUiOperateEntranceBinding
import com.example.uioperate.easy_recyclerview.EasyRecyclerViewActivity
import com.example.uioperate.fragment.FragmentRouterActivity
import com.example.uioperate.picture_selection.PictureSelectionActivity
import com.example.uioperate.storage.StorageActivity
import com.example.uioperate.touch_event.TouchEventActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings

class UiOperateEntranceActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityUiOperateEntranceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUiOperateEntranceBinding.inflate(layoutInflater).also {
            mBinding = it
            setContentView(mBinding.root)
        }

        GuideSettings.set(mBinding.uioperateLvLauncher, arrayOf(
            GuideItemEntity("替换ViewStud") { },
            GuideItemEntity("跳转 PictureSelectionActivity") {
                startActivity(Intent(this, PictureSelectionActivity::class.java))
            },
            GuideItemEntity("跳转 TouchEventActivity") {
                startActivity(Intent(this, TouchEventActivity::class.java))
            },
            GuideItemEntity("跳转 StorageActivity") {
                startActivity(Intent(this, StorageActivity::class.java))
            },
            GuideItemEntity("跳转 FragmentRouterActivity") {
                startActivity(Intent(this, FragmentRouterActivity::class.java))
            },
            GuideItemEntity("跳转 EasyRecyclerViewActivity") {
                startActivity(Intent(this, EasyRecyclerViewActivity::class.java))
            },
        ))
    }
}