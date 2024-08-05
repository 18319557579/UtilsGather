package com.example.uioperate.easy_recyclerview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings

class EasyRecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_recycler_view)

        GuideSettings.set(
            findViewById(R.id.rv_content),
            arrayOf(
                GuideItemEntity("只有一种类型") {
                    startActivity(Intent(this, OneTypeActivity::class.java))
                },
                GuideItemEntity("两种类型") {
                    startActivity(Intent(this, TwoTypeActivity::class.java))
                },
                GuideItemEntity("ViewPager2使用fast-list") {
                    startActivity(Intent(this, ViewPager2Activity::class.java))
                },
                GuideItemEntity("优化后，只有一种类型") {
                    startActivity(Intent(this, MyOneTypeActivity::class.java))
                },
            )
        )
    }
}