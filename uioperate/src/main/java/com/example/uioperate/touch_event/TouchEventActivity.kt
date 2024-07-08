package com.example.uioperate.touch_event

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.uioperate.touch_event.two_scrollview.TwoScrollViewActivity
import com.example.uioperate.touch_event.two_simple_scrollview.TwoSimpleScrollviewActivity
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
            )
        )
    }
}