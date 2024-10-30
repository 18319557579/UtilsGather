package com.example.utilsuser.immersion

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R

class InsetsListenerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        setContentView(R.layout.activity_insets_listener)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            findViewById<Button>(R.id.btn_top).setOnApplyWindowInsetsListener { v, insets ->
                val displayCutout = insets.getDisplayCutout()
                if (displayCutout != null) {
                    val left = displayCutout.getSafeInsetLeft();
                    val top = displayCutout.getSafeInsetTop();
                    val right = displayCutout.getSafeInsetRight();
                    val bottom = displayCutout.getSafeInsetBottom();
                    LogUtil.d("btn_top left: $left, top: $top, right: $right, bottom: $bottom")

                    // 如果当前为竖屏模式
                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        val topButtonParams = v.layoutParams as RelativeLayout.LayoutParams
                        topButtonParams.setMargins(left, top, right, bottom)
                    }
                    val orientation = resources.configuration.orientation
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        // 当前为横屏模式
                        val topButtonParams = v.layoutParams as RelativeLayout.LayoutParams
                        topButtonParams.setMargins(0, 0, 0, 0)
                    } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        // 当前为竖屏模式
                        val topButtonParams = v.layoutParams as RelativeLayout.LayoutParams
                        topButtonParams.setMargins(left, top, right, bottom)
                    }
                }
                insets
            }

            // 这段代码在实践情况下是错误的
            /*findViewById<Button>(R.id.btn_top).setOnApplyWindowInsetsListener { v, insets ->
                val displayCutout = insets.getDisplayCutout()

                if (displayCutout != null) {
                    val rects = displayCutout.boundingRects
                    val left = displayCutout.getSafeInsetLeft();
                    val top = displayCutout.getSafeInsetTop();
                    val right = displayCutout.getSafeInsetRight();
                    val bottom = displayCutout.getSafeInsetBottom();
                    LogUtil.d("btn_top left: $left, top: $top, right: $right, bottom: $bottom")

                    if (rects.isNotEmpty()) {
                        for (rect in rects) {
                            LogUtil.d("打印rect: $rect")
                            if (rect.intersects(v.left, v.top, v.right, v.bottom)) {
                                LogUtil.d("有所重叠")
                                val topButtonParams = v.layoutParams as RelativeLayout.LayoutParams
                                topButtonParams.setMargins(rect.left, rect.top, rect.right, rect.bottom)
                            } else {
                                LogUtil.d("没有发生重叠")
                            }
                        }
                    } else {
                        LogUtil.d("区域为空")
                    }
                }
                insets
            }*/

            findViewById<Button>(R.id.btn_side).setOnApplyWindowInsetsListener { v, insets ->
                val displayCutout = insets.getDisplayCutout()
                if (displayCutout != null) {
                    val left = displayCutout.getSafeInsetLeft();
                    val top = displayCutout.getSafeInsetTop();
                    val right = displayCutout.getSafeInsetRight();
                    val bottom = displayCutout.getSafeInsetBottom();
                    LogUtil.d("btn_side left: $left, top: $top, right: $right, bottom: $bottom")

                    val orientation = resources.configuration.orientation
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        // 当前为横屏模式
                        val sideButtonParams = v.layoutParams as RelativeLayout.LayoutParams
                        sideButtonParams.setMargins(left, top, right, bottom)
                    } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        // 当前为竖屏模式
                        val sideButtonParams = v.layoutParams as RelativeLayout.LayoutParams
                        sideButtonParams.setMargins(0, 0, 0, 0)
                    }

                }
                insets
            }


        }
    }
}