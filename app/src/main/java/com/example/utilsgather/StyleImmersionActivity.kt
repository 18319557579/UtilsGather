package com.example.utilsgather

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ListView
import com.example.utilsgather.lifecycle_callback.CallbackActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings

/**
 * 配合style使用的Activity，但效果还是不佳
 */
class StyleImmersionActivity : CallbackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_immersion)

        val listView = findViewById<ListView>(R.id.lv_launcher)
        GuideSettings.set(
            listView, arrayOf<GuideItemEntity>(
                GuideItemEntity("blank"){

                },
                GuideItemEntity("设置状态栏颜色") {
                    val window: Window = window

                    // 设置透明状态栏
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = Color.TRANSPARENT
                    }

                    // 隐藏状态栏和导航栏的UI，实现沉浸式体验
                    val decorView: View = window.getDecorView()
                    val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // 内容显示在状态栏后面
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // 内容显示在导航栏后面
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                    decorView.systemUiVisibility = uiOptions
                },
            )
        )
    }
}