package com.example.utilsgather.kt_room

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ListView
import android.widget.Toast
import com.example.utilsgather.R
import com.example.utilsgather.lifecycle_callback.CallbackActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsgather.ui.screen.ScreenFunctionUtils
import com.example.utilsgather.ui.status.OtherStatusBarUtil

/**
 * 配合style使用的Activity，但效果还是不佳
 */
class KtRoomActivity : CallbackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_immersion)

        val listView = findViewById<ListView>(R.id.lv_launcher)
        GuideSettings.set(
            listView, arrayOf<GuideItemEntity>(
                GuideItemEntity("blank") {

                },
                GuideItemEntity("隐藏ActionBar") {
                    ScreenFunctionUtils.hideActionBar(this)
                },
                GuideItemEntity("设置状态栏颜色") {
                    StatusBarUtils.setColor(this, 0xFF008B00.toInt())
                },
                GuideItemEntity("沉浸式 0.5的透明度") {
                    StatusBarUtils.immersive(this, 0xCD69C, 0.5f)
                },
                GuideItemEntity("沉浸式 0的透明度") {
                    StatusBarUtils.immersive(this, 0xCD69C, 0f)
                },
                GuideItemEntity("沉浸式 1的透明度") {
                    StatusBarUtils.immersive(this, 0xCD69C, 1f)
                },

                GuideItemEntity("设置状态栏黑色字体图标") {
                    StatusBarUtils.setStatusBarBlackText(this)
                },
                GuideItemEntity("设置状态栏白色字体图标") {
                    StatusBarUtils.setStatusBarWhiteText(this)
                },
                GuideItemEntity("获得状态栏的高度") {
                    val height = StatusBarUtils.getStatusBarHeight(this)
                    LogUtil.d("状态栏高度1：$height")

                    val height2 = OtherStatusBarUtil.getStatusBarHeight(this);
                    LogUtil.d("状态栏高度2：$height2")
                },
            )
        )
    }
}