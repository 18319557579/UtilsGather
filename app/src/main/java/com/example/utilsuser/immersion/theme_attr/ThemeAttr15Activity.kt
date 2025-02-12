package com.example.utilsuser.immersion.theme_attr

import android.os.Bundle
import android.widget.ListView
import com.example.utilsgather.lifecycle_callback.LifecycleLogActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsuser.R


/**
 * 配合style使用的Activity，但效果还是不佳
 */
class ThemeAttr15Activity : LifecycleLogActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combination_immersion2)

        supportActionBar?.hide()

        val listView = findViewById<ListView>(R.id.lv_launcher)
        GuideSettings.set(
            listView, arrayOf<GuideItemEntity>(
                GuideItemEntity("blank") {

                },

            )
        )
    }
}