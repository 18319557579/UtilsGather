package com.example.uioperate.easy_recyclerview

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R
import com.example.uioperate.picture_selection.PictureSelectionActivity
import com.example.uioperate.touch_event.two_scrollview.TwoScrollViewActivity
import com.example.uioperate.touch_event.two_simple_scrollview.TwoSimpleScrollviewActivity
import com.example.uioperate.touch_event.two_simple_scrollview_optimize_1_1.TwoSimpleScrollviewOptimize_1_1Activity
import com.example.uioperate.touch_event.two_simple_scrollview_optimize_1_2.TwoSimpleScrollviewOptimize_1_2Activity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.list.rados.fast_list.bind

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
                GuideItemEntity("") {

                },
            )
        )
    }
}