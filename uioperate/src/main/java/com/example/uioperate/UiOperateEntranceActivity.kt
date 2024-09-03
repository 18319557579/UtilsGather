package com.example.uioperate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.custom_github_blankj.CustomGithubBlankjActivity
import com.example.uioperate.custom_juejin_newki.progress_ring.CustomJuejinNewkiActivity
import com.example.uioperate.custom_juejin_newki.star.StarScoreViewActivity
import com.example.uioperate.custom_juejin_newki.thermometer.TemperatureViewActivity
import com.example.uioperate.custom_juejin_s10g.JuejinS10gActivity
import com.example.uioperate.custom_qiujuer.MoocBezierActivity
import com.example.uioperate.custom_qiujuer.CustomQiujuerActivity
import com.example.uioperate.custom_qiujuer.DrawableTestActivity
import com.example.uioperate.databinding.ActivityUiOperateEntranceBinding
import com.example.uioperate.easy_recyclerview.EasyRecyclerViewActivity
import com.example.uioperate.fragment.FragmentRouterActivity
import com.example.uioperate.picture_selection.PictureSelectionActivity
import com.example.uioperate.shortcus.ShortcutsActivity
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
            GuideItemEntity("跳转 自定义View Qiujuer") {
                startActivity(Intent(this, CustomQiujuerActivity::class.java))
            },
            GuideItemEntity("跳转 BezierActivity") {
                startActivity(Intent(this, MoocBezierActivity::class.java))
            },
            GuideItemEntity("跳转 DrawableTestActivity") {
                startActivity(Intent(this, DrawableTestActivity::class.java))
            },
            GuideItemEntity("桌面快捷方式 ShortcutsActivity") {
                startActivity(Intent(this, ShortcutsActivity::class.java))
            },
            GuideItemEntity("自定义View-掘金-s10g JuejinS10gActivity") {
                startActivity(Intent(this, JuejinS10gActivity::class.java))
            },
            GuideItemEntity("自定义View-Github-blankj CustomGithubBlankjActivity") {
                startActivity(Intent(this, CustomGithubBlankjActivity::class.java))
            },
            GuideItemEntity("自定义View-Github-Newki CustomJuejinNewkiActivity") {
                startActivity(Intent(this, CustomJuejinNewkiActivity::class.java))
            },
            GuideItemEntity("自定义View-Github-Newki TemperatureViewActivity") {
                startActivity(Intent(this, TemperatureViewActivity::class.java))
            },
            GuideItemEntity("自定义View-Github-Newki StarScoreViewActivity") {
                startActivity(Intent(this, StarScoreViewActivity::class.java))
            },
        ))
    }
}