package com.example.uioperate.fragment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.uioperate.fragment.communication.ZooActivity
import com.example.uioperate.fragment.communication_evnetbus.Zoo2Activity
import com.example.uioperate.fragment.communication_viewmodel.Zoo3Activity
import com.example.uioperate.fragment.function_cache.FunctionCacheActivity
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
                },
                GuideItemEntity("动态加载Fragment") {
                    startActivity(Intent(this, DynamicLoadActivity::class.java))
                },
                GuideItemEntity("测试Fragment的通信, 使用接口通信") {
                    startActivity(Intent(this, ZooActivity::class.java))
                },
                GuideItemEntity("测试Fragment的通信，使用 function cache") {
                    startActivity(Intent(this, FunctionCacheActivity::class.java))
                },
                GuideItemEntity("测试Fragment的通信，使用 EventBus") {
                    startActivity(Intent(this, Zoo2Activity::class.java))
                },
                GuideItemEntity("测试Fragment的通信，使用 ViewModel") {
                    startActivity(Intent(this, Zoo3Activity::class.java))
                }
            )
        )
    }
}