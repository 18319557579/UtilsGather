package com.example.utilsuser.immersion

import android.graphics.Color
import android.graphics.Insets
import android.os.Build
import android.util.Pair
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsgather.ui.ColorUtil
import com.example.utilsuser.R


class CompositeActivity : BaseTabViewpagerActivity() {
    override fun addPairs(pairs: MutableList<Pair<String, ShowFragment>>) {
        pairs.apply {
            add(
                Pair("状态栏-修改背景颜色", ShowFragment.newInstance(R.layout.activity_email,
                arrayOf(
                    InnerItemEntity("全红色") {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = 0xFFFF0000.toInt()
                    },
                    InnerItemEntity("半透明红色") {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = 0x80FF0000.toInt()
                    },
                    InnerItemEntity("全透明") {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            // 避免全透明时，系统自动给背景加上蒙层。（其实默认值就为false了，这里可以不用写）
                            window.isStatusBarContrastEnforced = false
                        }
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = Color.TRANSPARENT
                    },
                    InnerItemEntity("随机颜色") {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = ColorUtil.getRandomColorARGB()
                    },
                )
            ))
            )
            add(
                Pair("状态栏-文本颜色切换", ShowFragment.newInstance(R.layout.activity_email,
                arrayOf(
                    InnerItemEntity("（Android11过时）setSystemUiVisibility() 与 WTFs 实现 ---------------------") {  },
                    InnerItemEntity("亮色模式，状态栏文本为黑色") {
                        val decorView = window.decorView
                        decorView.systemUiVisibility =
                            decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

                        LogUtil.d("是否有FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS：${getWindow().getAttributes().flags and WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS != 0}")
                        LogUtil.d("是否有FLAG_TRANSLUCENT_STATUS：${getWindow().getAttributes().flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS != 0}")
                    },
                    InnerItemEntity("暗色模式，状态栏文本为白色") {
                        val decorView = window.decorView
                        decorView.systemUiVisibility =
                            decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()

                        LogUtil.d("是否有FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS：${getWindow().getAttributes().flags and WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS != 0}")
                        LogUtil.d("是否有FLAG_TRANSLUCENT_STATUS：${getWindow().getAttributes().flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS != 0}")
                    },
                    InnerItemEntity("（Android11开始） ---------------------") {  },
                    InnerItemEntity("亮色模式，状态栏文本为黑色") {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            val controller = window.insetsController
                            controller?.setSystemBarsAppearance(
                                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                        }  // 亮色模式-字体黑色
                    },
                    InnerItemEntity("暗色模式，状态栏文本为白色") {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            val controller = window.insetsController;
                            controller?.setSystemBarsAppearance(
                                0, // 不设置任何外观标志
                                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                        }  // 暗色模式-字体白色
                    },
                    InnerItemEntity("（AndroidX兼容库） ---------------------") {  },
                    InnerItemEntity("亮色模式，状态栏文本为黑色") {
                        WindowCompat.getInsetsController(window, window.decorView)
                            .isAppearanceLightStatusBars = true  // 亮色模式-字体黑色
                    },
                    InnerItemEntity("暗色模式，状态栏文本为白色") {
                        WindowCompat.getInsetsController(window, window.decorView)
                            .isAppearanceLightStatusBars = false  // 暗色模式-字体白色
                    },
                )
            ))
            )
            add(
                Pair("状态栏-内容是否延伸到状态栏", ShowFragment.newInstance(
                arrayOf(
                    InnerItemEntity("（Android11过时）setSystemUiVisibility() 与 WTFs 实现 ---------------------") {  },
                    InnerItemEntity("将内容延伸到状态栏") {
                        // 将内容延伸到状态栏
                        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    },
                    InnerItemEntity("取消内容延伸到状态栏") {
                        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv()
                    },

                    InnerItemEntity("（Android11开始） ---------------------") {  },
                    InnerItemEntity("将内容延伸到状态栏") {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            window.setDecorFitsSystemWindows(false)  // 将内容延伸到了状态栏和导航栏
                        }
                    },
                    InnerItemEntity("取消内容延伸到状态栏") {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            window.setDecorFitsSystemWindows(true)  // 内容从状态栏和导航栏出来了
                        }
                    },
                    // 这里发现很难做取消内容延伸到状态栏，不过考虑到其实很少会说内容延伸到状态栏了，又取消延伸（游戏模式那种除外）
                    InnerItemEntity("将内容延伸到状态栏-防止延伸到导航栏") {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            findViewById<ViewGroup>(R.id.main).setOnApplyWindowInsetsListener { view, windowInsets ->
                                val insets: Insets = windowInsets.getInsets(WindowInsets.Type.navigationBars())
                                LogUtil.d("navigationBars-insets: $insets")
                                view.setPadding(
                                    view.getPaddingLeft(),
                                    view.getPaddingTop(),
                                    view.getPaddingRight(),
                                    insets.bottom
                                )
                                WindowInsets.CONSUMED
                            }
                            window.setDecorFitsSystemWindows(false)  // 将内容延伸到了状态栏和导航栏
                        }
                    },

                    InnerItemEntity("（AndroidX兼容库） ---------------------") {  },
                    InnerItemEntity("将内容延伸到状态栏") {
                        WindowCompat.setDecorFitsSystemWindows(window, false) // 让内容延伸到系统窗口边界
                    },
                    InnerItemEntity("取消内容延伸到状态栏") {
                        WindowCompat.setDecorFitsSystemWindows(window, true)  // 内容从状态栏和导航栏出来了
                    },
                )
            ))
            )
            add(
                Pair("状态栏", ShowFragment.newInstance(
                arrayOf(
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                )
            ))
            )
        }
    }
}