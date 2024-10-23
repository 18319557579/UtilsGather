package com.example.utilsuser.kt_room

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ListView
import androidx.core.view.WindowCompat
import com.example.utilsgather.lifecycle_callback.LifecycleLogActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsgather.ui.ColorUtil
import com.example.utilsgather.ui.screen.ScreenFunctionUtils
import com.example.utilsgather.ui.status.OtherStatusBarUtil
import com.example.utilsuser.R


/**
 * 配合style使用的Activity，但效果还是不佳
 */
class KtRoomActivity : LifecycleLogActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_immersion)

        supportActionBar?.hide()

        val listView = findViewById<ListView>(R.id.lv_launcher)
        GuideSettings.set(
            listView, arrayOf<GuideItemEntity>(
                GuideItemEntity("blank") {

                },
                GuideItemEntity("隐藏ActionBar") {
                    ScreenFunctionUtils.hideActionBar(this)
                },
                GuideItemEntity("设置状态栏背景颜色。用一行代码简单实现") {
                    window.statusBarColor = ColorUtil.getRandomColor()
                },
                GuideItemEntity("设置状态栏背景颜色") {
                    StatusBarUtils.setColor(this, ColorUtil.getRandomColor())
                },
                GuideItemEntity("设置状态栏字体 亮色模式-字体黑色。AndroidX的兼容实现") {
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightStatusBars = true
                },
                GuideItemEntity("设置状态栏字体 暗色模式-字体白色。AndroidX的兼容实现") {
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightStatusBars = false
                },
                GuideItemEntity("沉浸式 0.5的透明度。用一行代码简单实现，日间模式会出现白底，夜间模式会出现黑底") {
                    window.statusBarColor = 0x80CD69C
                },
                GuideItemEntity("沉浸式 0.5的透明度。只这么设置的话（内容不嵌入），是半透明红色遮盖在白色上（日间）或黑色上（夜间）。这里的白色和黑色我认为是主题颜色，后续可以证实") {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = 0x80FF0000.toInt()
                },
                GuideItemEntity("沉浸式 0的透明度。只这么设置的话（内容不嵌入）") {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = 0x00FF0000.toInt()
                },
                GuideItemEntity("沉浸式 1的透明度。只这么设置的话（内容不嵌入）") {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = 0xFFFF0000.toInt()
                },
                GuideItemEntity("内容嵌入状态栏") {
                    var systemUiVisibility = window.decorView.systemUiVisibility
                    systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    window.decorView.systemUiVisibility = systemUiVisibility
                },
                GuideItemEntity("沉浸式 0.5的透明度") {
                    StatusBarUtils.immersive(this, 0xCD69C, 0.5f)
                },
                GuideItemEntity("沉浸式 0.8的透明度") {
                    StatusBarUtils.immersive(this, 0xCD69C, 0.8f)
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
                GuideItemEntity("") {
                },
                GuideItemEntity("") {
                },
                GuideItemEntity("") {
                },
            )
        )
    }
}