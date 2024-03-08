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
import com.example.utilsgather.ui.immersion.ImmersionUtil

/**
 * 已经可以实现全沉浸式了
 */
class StyleImmersion2Activity : CallbackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_immersion)

        ImmersionUtil.hideVirtualButton(this)

        ImmersionUtil.screenFull(this)
    }
}