package com.example.uioperate.custom_marquee_textview

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uioperate.R

class MarqueeTextViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marquee_text_view)

        val mtvThree2 = findViewById<MarqueeTextViewThree>(R.id.mtv_three2)
        findViewById<Button>(R.id.btn_set_new_content).setOnClickListener {
            mtvThree2.setText("Kotlin 实现文本gjqy横向滚动，跑马灯效果。127...33333")
        }
    }
}