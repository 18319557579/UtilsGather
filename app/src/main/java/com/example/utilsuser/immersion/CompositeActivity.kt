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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsgather.ui.ColorUtil
import com.example.utilsuser.R


class CompositeActivity : BaseTabViewpagerActivity(), ShowFragment.OnFragmentInteractionListener {
    override fun addPairs(pairs: MutableList<Pair<String, ShowFragment>>) {
        pairs.apply {
            add(
                Pair("状态栏-修改背景颜色", ShowFragment.newInstance(R.layout.activity_email, FragmentTag.ONE))
            )
            add(
                Pair("状态栏-文本颜色切换", ShowFragment.newInstance(R.layout.activity_email, FragmentTag.TWO))
            )
            add(
                Pair("状态栏-内容是否延伸到状态栏", ShowFragment.newInstance(FragmentTag.THREE))
            )
            add(
                Pair("状态栏-显示与隐藏", ShowFragment.newInstance(FragmentTag.FOUR))
            )
            add(
                Pair("状态栏之隐藏模式", ShowFragment.newInstance(FragmentTag.FIVE))
            )
            add(
                Pair("导航栏背景颜色", ShowFragment.newInstance(FragmentTag.SIX))
            )
            add(
                Pair("导航栏按钮或条颜色", ShowFragment.newInstance(FragmentTag.SEVEN))
            )
            add(
                Pair("内容是否延伸到导航栏", ShowFragment.newInstance(FragmentTag.EIGHT))
            )
            add(
                Pair("导航栏显示与隐藏", ShowFragment.newInstance(FragmentTag.NINE))
            )
        }
    }

    override fun getEntities(fragmentTag: Int): Array<InnerItemEntity> {
        return when(fragmentTag) {
            FragmentTag.ONE -> arrayOf(
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
            FragmentTag.TWO -> arrayOf(
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
            FragmentTag.THREE -> arrayOf(
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
                // 这里发现很难做取消内容延伸到导航栏，不过考虑到其实很少会说内容延伸到状态栏了，但取消延伸到导航栏（游戏模式那种除外）
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
                // 这里发现很难做取消内容延伸到导航栏，不过考虑到其实很少会说内容延伸到状态栏了，但取消延伸到导航栏（游戏模式那种除外）
                InnerItemEntity("将内容延伸到状态栏-防止延伸到导航栏") {
                    ViewCompat.setOnApplyWindowInsetsListener(findViewById<ViewGroup>(R.id.main)) { v: View, windowInsets: WindowInsetsCompat ->
                        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
                        // 使用维护的状态变量来决定是否应用 padding
                        v.setPadding(
                            v.paddingLeft, v.paddingTop, v.paddingRight, insets.bottom
                        )
                        WindowInsetsCompat.CONSUMED
                    }
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                },
            )
            FragmentTag.FOUR -> arrayOf(
                InnerItemEntity("（Android11过时）setSystemUiVisibility() 与 WTFs 实现 ---------------------") {  },
                InnerItemEntity("隐藏状态栏") {
                    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_FULLSCREEN  // 隐藏状态栏
                },
                InnerItemEntity("显示状态栏") {
                    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and
                            View.SYSTEM_UI_FLAG_FULLSCREEN.inv()  // 显示状态栏
                },
                InnerItemEntity("（Android11开始） ---------------------") {  },
                InnerItemEntity("隐藏状态栏") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val controller = window.insetsController
                        controller?.hide(WindowInsets.Type.statusBars())
                    }  // 隐藏状态栏
                },
                InnerItemEntity("显示状态栏") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val controller = window.insetsController
                        controller?.show(WindowInsets.Type.statusBars())
                    }  // 显示状态栏
                },
                InnerItemEntity("（AndroidX兼容库） ---------------------") {  },
                InnerItemEntity("隐藏状态栏") {
                    val windowInsetsController =
                        WindowCompat.getInsetsController(window, window.decorView)
                    windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())  // 隐藏状态栏
                },
                InnerItemEntity("显示状态栏") {
                    val windowInsetsController =
                        WindowCompat.getInsetsController(window, window.decorView)
                    windowInsetsController.show(WindowInsetsCompat.Type.statusBars())  // 显示状态栏
                },
            )
            FragmentTag.FIVE -> arrayOf(
                InnerItemEntity("（Android11过时）setSystemUiVisibility() 与 WTFs 实现 ---------------------") {  },
                InnerItemEntity("当调用这个方法后，之后就是会自动隐藏的了") {
                    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                },
                InnerItemEntity("当调用这个方法后，之后就是会临时隐藏模式") {
                    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
                },
                InnerItemEntity("（Android11开始） ---------------------") {  },
                InnerItemEntity("当调用这个方法后，之后就是会自动隐藏的了") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val controller = window.insetsController
                        // 当调用这个方法后，之后就是会自动隐藏的了
                        //（但是只调用这个方法的话不会有表现出来，要通过hide/show/手动，才能感觉得出来有所改变）
                        controller?.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
                    }
                },
                InnerItemEntity("当调用这个方法后，之后就是会临时隐藏模式") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val controller = window.insetsController
                        // 当调用这个方法后，之后就是会临时隐藏模式
                        //（但是只调用这个方法的话不会有表现出来，要通过hide/show/手动，才能感觉得出来有所改变）
                        controller?.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_DEFAULT);
                    }
                },
                InnerItemEntity("（AndroidX兼容库） ---------------------") {  },
                InnerItemEntity("当调用这个方法后，之后就是会自动隐藏的了") {
                    val windowInsetsController =
                        WindowCompat.getInsetsController(window, window.decorView)
                    // 当调用这个方法后，之后就是会自动隐藏的了
                    //（但是只调用这个方法的话不会有表现出来，要通过hide/show/手动，才能感觉得出来有所改变）
                    windowInsetsController.setSystemBarsBehavior(
                        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE)
                },
                InnerItemEntity("当调用这个方法后，之后就是会临时隐藏模式") {
                    val windowInsetsController =
                        WindowCompat.getInsetsController(window, window.decorView)
                    // 当调用这个方法后，之后就是会临时隐藏模式
                    //（但是只调用这个方法的话不会有表现出来，要通过hide/show/手动，才能感觉得出来有所改变）
                    windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_DEFAULT)
                },
            )
            FragmentTag.SIX -> arrayOf(
                InnerItemEntity("设置导航栏背景颜色-全红") {
                    window.apply {
                        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        navigationBarColor = Color.parseColor("#FFFF0000")
                    }
                },
                InnerItemEntity("设置导航栏背景颜色-半透明红色") {
                    window.apply {
                        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        navigationBarColor = Color.parseColor("#80FF0000")
                    }
                },
                InnerItemEntity("设置导航栏背景颜色-全透明") {
                    window.apply {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            // 在导航栏背景全透明的情况下，不要让自动出现蒙层
                            isNavigationBarContrastEnforced = false
                        }
                        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        navigationBarColor = Color.TRANSPARENT
                    }
                },
                InnerItemEntity("设置导航栏背景颜色-随机颜色") {
                    window.apply {
                        window.apply {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                // 在导航栏背景全透明的情况下，不要让自动出现蒙层
                                isNavigationBarContrastEnforced = false
                            }
                            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                            navigationBarColor = ColorUtil.getRandomColorARGB()
                        }
                    }
                },
            )
            FragmentTag.SEVEN -> arrayOf(
                InnerItemEntity("（Android11过时）setSystemUiVisibility() 与 WTFs 实现 ---------------------") {  },
                InnerItemEntity("设置导航栏按钮或条颜色-亮色模式-会深点") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        window.decorView.systemUiVisibility =
                            window.decorView.systemUiVisibility or
                                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    }
                },
                InnerItemEntity("设置导航栏按钮或条颜色-暗色模式-会浅点") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        window.decorView.systemUiVisibility =
                            window.decorView.systemUiVisibility and
                                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
                    }
                },
                InnerItemEntity("（Android11开始） ---------------------") {  },
                InnerItemEntity("亮色模式，导航栏文本为黑色") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val controller = window.insetsController
                        controller?.setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)
                    }  // 亮色模式-字体黑色
                },
                InnerItemEntity("暗色模式，导航栏文本为白色") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val controller = window.insetsController;
                        controller?.setSystemBarsAppearance(
                            0, // 不设置任何外观标志
                            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)
                    }  // 暗色模式-字体白色
                },
                InnerItemEntity("（AndroidX兼容库） ---------------------") {  },
                InnerItemEntity("亮色模式，导航栏文本为深色") {
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightNavigationBars = true  // 亮色模式-字体黑色
                },
                InnerItemEntity("暗色模式，状态栏文本为浅色") {
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightNavigationBars = false  // 暗色模式-字体白色
                },
            )
            FragmentTag.EIGHT -> arrayOf(
                InnerItemEntity("（Android11过时）setSystemUiVisibility() 与 WTFs 实现 ---------------------") {  },
                InnerItemEntity("内容延伸到导航栏") {
                    // （但是我发现内容也同时延伸到状态栏了）
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility or
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                },
                InnerItemEntity("取消 内容延伸到导航栏") {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility and
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION.inv()
                },
                InnerItemEntity("（Android11开始） ---------------------") {  },
                InnerItemEntity("将内容延伸到导航栏") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        window.setDecorFitsSystemWindows(false)  // 将内容延伸到了状态栏和导航栏
                    }
                },
                InnerItemEntity("取消内容延伸到导航栏") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        window.setDecorFitsSystemWindows(true)  // 内容从状态栏和导航栏出来了
                    }
                },

                InnerItemEntity("将内容延伸到导航栏-防止延伸到状态栏") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        findViewById<ViewGroup>(R.id.main).setOnApplyWindowInsetsListener { view, windowInsets ->
                            val insets: Insets = windowInsets.getInsets(WindowInsets.Type.statusBars())
                            LogUtil.d("navigationBars-insets: $insets")
                            view.setPadding(
                                view.getPaddingLeft(),
                                insets.top,
                                view.getPaddingRight(),
                                view.paddingBottom,
                            )
                            WindowInsets.CONSUMED
                        }
                        window.setDecorFitsSystemWindows(false)  // 将内容延伸到了状态栏和导航栏
                    }
                },

                InnerItemEntity("（AndroidX兼容库） ---------------------") {  },
                InnerItemEntity("将内容延伸到导航栏") {
                    WindowCompat.setDecorFitsSystemWindows(window, false) // 让内容延伸到系统窗口边界
                },
                InnerItemEntity("取消内容延伸到导航栏") {
                    WindowCompat.setDecorFitsSystemWindows(window, true)  // 内容从状态栏和导航栏出来了
                },
                InnerItemEntity("将内容延伸到导航栏-防止延伸到状态栏") {
                    ViewCompat.setOnApplyWindowInsetsListener(findViewById<ViewGroup>(R.id.main)) { v: View, windowInsets: WindowInsetsCompat ->
                        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
                        // 使用维护的状态变量来决定是否应用 padding
                        v.setPadding(
                            v.paddingLeft, insets.top, v.paddingRight, v.paddingBottom,
                        )
                        WindowInsetsCompat.CONSUMED
                    }
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                },
            )
            FragmentTag.NINE -> arrayOf(
                InnerItemEntity("（Android11过时）setSystemUiVisibility() 与 WTFs 实现 ---------------------") {  },
                InnerItemEntity("隐藏导航栏") {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility or
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                },
                InnerItemEntity("隐藏导航栏，加SYSTEM_UI_FLAG_IMMERSIVE") {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility or
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                                View.SYSTEM_UI_FLAG_IMMERSIVE
                },
                InnerItemEntity("隐藏导航栏，为自动隐藏模式") {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility or
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                },
                InnerItemEntity("显示导航栏") {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility and
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
                },
                InnerItemEntity("（Android11开始） ---------------------") {  },
                InnerItemEntity("隐藏状态栏") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val controller = window.insetsController
                        controller?.hide(WindowInsets.Type.navigationBars())
                    }  // 隐藏状态栏
                },
                InnerItemEntity("显示状态栏") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val controller = window.insetsController
                        controller?.show(WindowInsets.Type.navigationBars())
                    }  // 显示状态栏
                },
                InnerItemEntity("（AndroidX兼容库） ---------------------") {  },
                InnerItemEntity("隐藏状态栏") {
                    val windowInsetsController =
                        WindowCompat.getInsetsController(window, window.decorView)
                    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())  // 隐藏状态栏
                },
                InnerItemEntity("显示状态栏") {
                    val windowInsetsController =
                        WindowCompat.getInsetsController(window, window.decorView)
                    windowInsetsController.show(WindowInsetsCompat.Type.navigationBars())  // 显示状态栏
                },
            )

            else -> {arrayOf()}
        }
    }
}