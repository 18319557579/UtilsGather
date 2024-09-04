package com.example.uioperate.custom_juejin_newki.range_view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uioperate.R
import com.example.utilsgather.logcat.LogUtil

class RangeViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_range_view)

        findViewById<RangeView>(R.id.range_view_1).setRangeValueListener { leftValue, rightValue ->
            LogUtil.d("最小值: $leftValue, 最大值: $rightValue")
        }
        findViewById<RangeView>(R.id.range_view_2).setRangeValueListener { leftValue, rightValue ->
            LogUtil.d("最小值: $leftValue, 最大值: $rightValue")
        }
        findViewById<RangeView>(R.id.range_view_3).setRangeValueListener { leftValue, rightValue ->
            LogUtil.d("最小值: $leftValue, 最大值: $rightValue")
        }
    }
}