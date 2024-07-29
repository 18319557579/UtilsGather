package com.example.uioperate.fragment

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uioperate.R
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings

class FragmentRouterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_router)

        GuideSettings.set(
            findViewById(R.id.lv_launcher),
            arrayOf(
                GuideItemEntity("静态加载Fragment") {
                    startActivity(Intent(this, StaticLoadActivity::class.java))
                }
            )
        )
    }
}