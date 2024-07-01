package com.example.uioperate.picture_selection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.uioperate.databinding.ActivityPictureSelectionBinding
import com.example.uioperate.databinding.ActivityUiOperateEntranceBinding
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings

class PictureSelectionActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPictureSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPictureSelectionBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        GuideSettings.set(mBinding.lvPicSelectionOperation, arrayOf(
            GuideItemEntity("单选图片") {
            },
        ))
    }
}