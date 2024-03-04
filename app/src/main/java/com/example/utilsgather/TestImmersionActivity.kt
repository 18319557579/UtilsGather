package com.example.utilsgather

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ListView
import com.example.utilsgather.lifecycle_callback.CallbackActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.ui.immersion.ImmersionUtil
import com.example.utilsgather.ui.screen.ScreenFunctionUtils
import com.example.utilsgather.ui.status.OtherStatusBarUtil


class TestImmersionActivity : CallbackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_immersion)

        val listView = findViewById<ListView>(R.id.lv_launcher)
        GuideSettings.set(
            listView, arrayOf<GuideItemEntity>(
                GuideItemEntity("去StyleImmersionActivity") {
                    startActivity(Intent(this, StyleImmersionActivity::class.java))
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