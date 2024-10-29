package com.example.utilsuser.immersion

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.ui.immersion.ImmersionUtil
import com.example.utilsuser.R

class SimulationGameImmersionActivity : AppCompatActivity() {
    var isGameMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { // Android P及以上版本
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        setContentView(R.layout.activity_combination_immersion2)

        GuideSettings.set(
            findViewById<ListView>(R.id.lv_launcher), arrayOf<GuideItemEntity>(
                GuideItemEntity("游戏模式-只改变量") {
                    isGameMode = true
                },
                GuideItemEntity("非游戏模式-只改变量") {
                    isGameMode = false
                },

                GuideItemEntity("切换为 游戏模式") {
                    isGameMode = true
                    ImmersionUtil.screenFull(this)
                },
                GuideItemEntity("切换为 非游戏模式") {
                    isGameMode = false
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                },
            ))
    }

    // 当活动的当前窗口获得或失去焦点时被调用。这是判断该活动是否是与用户积极交互的实体的最佳指标。
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        resumeIfHasFocus()
    }

    override fun onResume() {
        super.onResume()
        resumeIfHasFocus()
    }

    private fun resumeIfHasFocus() {
        if (isGameMode) {
            ImmersionUtil.screenFull(this)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }
}