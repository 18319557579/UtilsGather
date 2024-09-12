package com.example.uioperate.base_adapter

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uioperate.R
import com.example.uioperate.base_adapter.multi_layout.MultiLayoutActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings

class BaseAdapterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_adapter)

        GuideSettings.set(
            findViewById(R.id.lv_launcher), arrayOf(
                GuideItemEntity("最简单的使用") {
                    startActivity(Intent(this@BaseAdapterActivity, SimpleActivity::class.java))
                },
                GuideItemEntity("多布局的RecyclerView") {
                    startActivity(Intent(this, MultiLayoutActivity::class.java))
                },
            )
        )
    }
}