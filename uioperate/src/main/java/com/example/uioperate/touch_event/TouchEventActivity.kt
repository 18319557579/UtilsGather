package com.example.uioperate.touch_event

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.uioperate.touch_event.two_scrollview.TwoScrollViewActivity
import com.example.uioperate.touch_event.two_simple_scrollview.TwoSimpleScrollviewActivity
import com.example.uioperate.touch_event.two_simple_scrollview_optimize_1_1.TwoSimpleScrollviewOptimize_1_1Activity
import com.example.uioperate.touch_event.two_simple_scrollview_optimize_1_2.TwoSimpleScrollviewOptimize_1_2Activity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings

class TouchEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_event)

        GuideSettings.set(
            findViewById(R.id.lv_launcher),
            arrayOf(
                GuideItemEntity("两个ScrollView嵌套") {
                    startActivity(Intent(this@TouchEventActivity, TwoScrollViewActivity::class.java))
                },
                GuideItemEntity("SimpleNestedScrollView嵌套ScrollView") {
                    startActivity(Intent(this@TouchEventActivity, TwoSimpleScrollviewActivity::class.java))
                },
                GuideItemEntity("SimpleNestedScrollView嵌套ScrollView，优化1.1") {
                    startActivity(Intent(this@TouchEventActivity, TwoSimpleScrollviewOptimize_1_1Activity::class.java))
                },
                GuideItemEntity("SimpleNestedScrollView嵌套ScrollView，优化1.2") {
                    startActivity(Intent(this@TouchEventActivity, TwoSimpleScrollviewOptimize_1_2Activity::class.java))
                },
            )
        )
    }
}