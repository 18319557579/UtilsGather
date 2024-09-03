package com.example.uioperate.custom_juejin_newki.star

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uioperate.R
import com.example.utilsgather.logcat.LogUtil

class StarScoreViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star_score_view)

        findViewById<LinearLayout>(R.id.ll_outer_container).setOnClickListener {
            LogUtil.d("外部容器的点击事件")
        }

        findViewById<StarScoreView>(R.id.star_view).setOnStarChangeListener { mark ->
            LogUtil.d("第一个控件的分数: $mark")
        }
        findViewById<StarScoreView>(R.id.star_view2).setOnStarChangeListener { mark ->
            LogUtil.d("第二个控件的分数: $mark")
        }
    }
}