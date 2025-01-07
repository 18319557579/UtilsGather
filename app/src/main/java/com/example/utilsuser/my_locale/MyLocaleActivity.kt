package com.example.utilsuser.my_locale


import android.content.res.Configuration
import android.os.Bundle
import android.widget.ListView
import com.example.utilsgather.context.ApplicationGlobal
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
                GuideItemEntity("系统的Locale"){
                    val currentLocale = Locale.getDefault()

                    // 输出当前应用的 Locale 信息
                    val language = currentLocale.language // 获取语言
                    val country = currentLocale.country // 获取国家/地区
                    val displayName = currentLocale.displayName // 获取 Locale 显示名称

                    LogUtil.d("Locale: $currentLocale")
                    LogUtil.d("language: $language")
                    LogUtil.d("country: $country")
                    LogUtil.d("displayName: $displayName")
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
                    LogUtil.d("country: $country")
                    LogUtil.d("displayName: $displayName")
                },
                GuideItemEntity("打印特定的字符串-当前Activity层面"){
                    val specificStr = getString(R.string.specific_str)
                    LogUtil.d("特定的字符串: $specificStr")
                },
                GuideItemEntity("打印特定的字符串-Application层面"){
                    val specificStr = ApplicationGlobal.getInstance().getString(R.string.specific_str)
                    LogUtil.d("特定的字符串: $specificStr")
                },
                GuideItemEntity("switch zh"){
                    setLocale("zh")
//                    setLocale(Locale.CHINA)
                },
                GuideItemEntity("switch en"){
                    setLocale("en")
//                    setLocale(Locale.US)
                },
                GuideItemEntity("switch pt"){
                    setLocale("pt")
//                    setLocale(Locale.P)
                },
            )
        )
    }

    // 设置应用的 Locale
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale) // 更新 Locale 为默认语言

        // 更新配置文件
        val config: Configuration = Configuration()
        config.locale = locale

        // 更新资源，刷新 UI
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    // 设置应用的 Locale
    private fun setLocale(locale: Locale) {
        Locale.setDefault(locale) // 更新 Locale 为默认语言

        // 更新配置文件
        val config: Configuration = Configuration()
        config.locale = locale

        // 更新资源，刷新 UI
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}