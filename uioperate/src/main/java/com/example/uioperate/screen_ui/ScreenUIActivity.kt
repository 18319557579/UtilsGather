package com.example.uioperate.screen_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.utilsgather.context.ApplicationGlobal
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsgather.ui.screen.ScreenSizeUtil

class ScreenUIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_common)

        GuideSettings.set(
            findViewById(R.id.lv_launcher),
            arrayOf(
                GuideItemEntity("打印屏幕尺寸信息") {
                    LogUtil.d("getScreenWidth: ${ScreenSizeUtil.getScreenWidth(ApplicationGlobal.getInstance())}")
                    LogUtil.d("getScreenHeight: ${ScreenSizeUtil.getScreenHeight(ApplicationGlobal.getInstance())}")
                    LogUtil.d("getScreenWidthReal: ${ScreenSizeUtil.getScreenWidthReal(ApplicationGlobal.getInstance())}")
                    LogUtil.d("getScreenHeightReal: ${ScreenSizeUtil.getScreenHeightReal(ApplicationGlobal.getInstance())}")
                },
            )
        )
    }


}