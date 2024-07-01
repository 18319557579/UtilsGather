package com.example.uioperate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.databinding.ActivityUiOperateEntranceBinding
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
        ))
    }
}