package com.example.utilsuser.immersion.fitsSystemWindows

import android.graphics.Color
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
class FitSystemWindows1Activity : LifecycleLogActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitsystemwindows)

        window.statusBarColor = Color.TRANSPARENT

        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        supportActionBar?.hide()
    }
}