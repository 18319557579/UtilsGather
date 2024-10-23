package com.example.utilsuser

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ListView
import androidx.core.view.WindowCompat
import com.example.utilsgather.lifecycle_callback.LifecycleLogActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.ui.ColorUtil
import com.example.utilsgather.ui.immersion.ImmersionUtil
import com.example.utilsgather.ui.screen.ScreenFunctionUtils
import com.example.utilsgather.ui.status.OtherStatusBarUtil
import com.example.utilsuser.kt_room.KtRoomActivity
import com.example.utilsuser.kt_room.StatusBarUtils

// todo 发现 ImmersionUtil.screenFull(this) -> StatusBarUtils.setColor(this, ColorUtil.getRandomColor()) 可以实现cocos游戏效果
class TestImmersionActivity : LifecycleLogActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_immersion)

        supportActionBar?.hide()

        val listView = findViewById<ListView>(R.id.lv_launcher)
        GuideSettings.set(
            listView, arrayOf<GuideItemEntity>(
                GuideItemEntity("去StyleImmersionActivity") {
                    startActivity(Intent(this, StyleImmersionActivity::class.java))
                },
                GuideItemEntity("去StyleImmersion2Activity") {
                    startActivity(Intent(this, StyleImmersion2Activity::class.java))
                },
                GuideItemEntity("去KtRoomActivity") {
                    startActivity(Intent(this, KtRoomActivity::class.java))
                },
                /**
                 * 效果：
                 * ActionBar消失了，附带动画的
                 */
                GuideItemEntity("隐藏ActionBar") {
                    ScreenFunctionUtils.hideActionBar(this)
                },
                GuideItemEntity("隐藏虚拟按钮") {
                    ImmersionUtil.hideVirtualButton(this)
                },
                GuideItemEntity("设置全屏？") {
                    ImmersionUtil.screenFull(this)
                },
                GuideItemEntity("单独使用FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS") {
                    this.getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                },

                GuideItemEntity("设置状态栏颜色") {
                    OtherStatusBarUtil.setColor(this, 0x21303c, 0)
                },

                /**
                 * 效果：
                 * 1.状态栏为灰色半透明
                 * 2.内容嵌入了状态栏
                 * 3.ActionBar还在原来的位置
                 */
                GuideItemEntity("设置状态栏透明（4.4）") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }
                },

                /**
                 * 效果：
                 * 1.状态栏全透明
                 * 2.内容嵌入了状态栏
                 * 3.ActionBar还在原来的位置
                 */
                GuideItemEntity("设置状态栏透明（5.0）") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                        window.decorView.systemUiVisibility = option

                        window.statusBarColor = Color.TRANSPARENT
                    }
                },
                /**
                 * 1.嵌入到状态栏中
                 * 2.但是状态栏没有其他变化，还是原来的颜色，因此嵌入状态栏的视图压根看不到
                 */
                GuideItemEntity("设置状态栏透明（5.0）- 1") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                        window.decorView.systemUiVisibility = option
                    }
                },
                /**
                 * 1.状态栏变成透明了，啥都看不到
                 */
                GuideItemEntity("设置状态栏透明（5.0）- 2") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.statusBarColor = Color.TRANSPARENT
                    }
                },

                /**
                 * 1.嵌入到状态栏中
                 * 2.状态栏为透明
                 * 3.深色文字和图标（适用于浅色背景）
                 */
                GuideItemEntity("设置状态栏透明（6.0）") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        window.statusBarColor = Color.TRANSPARENT
                    }
                },
                /**
                 * 1.嵌入到状态栏中
                 * 2.状态栏为透明
                 * 3.浅色文字和图标（适用于深色背景），默认
                 */
                GuideItemEntity("设置状态栏透明（6.0），浅色文字") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        window.statusBarColor = Color.TRANSPARENT
                    }
                },

                /**
                 * window.decorView.systemUiVisibility所带来的影响都消除了
                 */
                GuideItemEntity("清除window.decorView.systemUiVisibility") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        window.decorView.systemUiVisibility = 0; // 移除所有标志
                    }

                },

                /**
                 * 状态栏不可见，但从顶端往下拉时又会出现，之后不再消失
                 */
                GuideItemEntity("让状态栏消失（16）") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        val decorView = window.decorView
                        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                        decorView.systemUiVisibility = uiOptions
                    }
                },
                /**
                 * 状态栏不可见，但从顶端往下拉时又会出现，之后不再消失
                 * 导航栏不可见，但从底部往上拉时又会出现，之后不再消失
                 */
                GuideItemEntity("让状态栏消失（19）") {
                    val decorView = window.decorView
                    val uiOptions = (View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE)
                    decorView.systemUiVisibility = uiOptions
                },

                /**
                 * 状态栏不可见，但从顶端往下拉时又会出现，之后会自动消失，可以循环本操作
                 * 导航栏不可见，但从底部往上拉时又会出现，之后会自动消失，可以循环本操作
                 */
                GuideItemEntity("让状态栏消失（19），可以重复出现") {
                    val decorView = window.decorView
                    val uiOptions = (View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                    decorView.systemUiVisibility = uiOptions

                },
                GuideItemEntity("-------KtRoom------------------------------------------------") {
                    window.statusBarColor = ColorUtil.getRandomColor()
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
                GuideItemEntity("------------------------------------------------") {

                },
                GuideItemEntity("实现状态栏沉浸式，只隐藏一次，后续只要下拉就会还原了") {
                    ImmersionUtil.screenFull_JustStatusBar_hide_once(this)

                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//                    window.statusBarColor = 0x00FF0000.toInt()
                },
                GuideItemEntity("实现状态栏沉浸式，粘性隐藏，下拉出来后稍等会自己回去") {
                    ImmersionUtil.screenFull_JustStatusBar_hide_sticky(this)

                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = 0x00FF0000.toInt()
                },
                GuideItemEntity("实现状态栏沉浸式，没有SYSTEM_UI_FLAG_FULLSCREEN，且不设置状态栏颜色") {
                    ImmersionUtil.screenFull_JustStatusBar_no_foremost_flag(this)

                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                },
                GuideItemEntity("实现状态栏沉浸式，没有SYSTEM_UI_FLAG_FULLSCREEN，状态栏设置为透明") {
                    ImmersionUtil.screenFull_JustStatusBar_no_foremost_flag(this)

                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = 0x00FF0000.toInt()
                },
            )
        )

        /*findViewById<Button>(R.id.btn_hide_virtual_button).setOnClickListener {
            ImmersionUtil.hideVirtualButton(this)
        }
        findViewById<Button>(R.id.btn_set_screen_full).setOnClickListener {
            ImmersionUtil.screenFull(this)
        }

        findViewById<Button>(R.id.btn_set_status_bar_color).setOnClickListener {
            OtherStatusBarUtil.setColor(this, 0x21303c, 0)
        }

        findViewById<Button>(R.id.btn_set_status_bar_translucent).setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }*/
    }
}