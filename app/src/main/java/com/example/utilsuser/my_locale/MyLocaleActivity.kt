package com.example.utilsuser.my_locale


import android.os.Bundle
import android.widget.ListView
import com.example.utilsgather.lifecycle_callback.LifecycleLogActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import java.util.Locale


/**
 * 配合style使用的Activity，但效果还是不佳
 */
class MyLocaleActivity : LifecycleLogActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_common)

        supportActionBar?.hide()

        val listView = findViewById<ListView>(R.id.lv_launcher)
        GuideSettings.set(
            listView, arrayOf<GuideItemEntity>(
                GuideItemEntity("当前系统的Locale"){
                    val currentLocale = Locale.getDefault()

                    // 输出当前应用的 Locale 信息
                    val language = currentLocale.language // 获取语言
                    val country = currentLocale.country // 获取国家/地区
                    val displayName = currentLocale.displayName // 获取 Locale 显示名称

                    LogUtil.d("Locale: $currentLocale")
                    LogUtil.d("language: $language")
                    LogUtil.d("Locale: $country")
                    LogUtil.d("Locale: $displayName")
                },
                GuideItemEntity("当前应用的Locale"){
                    // 获取当前应用的 Locale
                    val currentLocale = resources.configuration.locale

                    // 输出当前应用的 Locale 信息
                    val language = currentLocale.language // 获取语言
                    val country = currentLocale.country // 获取国家/地区
                    val displayName = currentLocale.displayName // 获取 Locale 显示名称

                    LogUtil.d("Locale: $currentLocale")
                    LogUtil.d("language: $language")
                    LogUtil.d("Locale: $country")
                    LogUtil.d("Locale: $displayName")
                },
                GuideItemEntity("打印特定的字符串"){
                    val specificStr = getString(R.string.specific_str)
                    LogUtil.d("特定的字符串: $specificStr")
                },

            )
        )
    }
}