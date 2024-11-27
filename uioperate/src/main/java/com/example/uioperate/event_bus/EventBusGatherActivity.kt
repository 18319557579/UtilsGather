package com.example.uioperate.event_bus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.uioperate.easy_recyclerview.MyOneTypeActivity
import com.example.uioperate.easy_recyclerview.OneTypeActivity
import com.example.uioperate.easy_recyclerview.TwoTypeActivity
import com.example.uioperate.easy_recyclerview.ViewPager2Activity
import com.example.uioperate.easy_recyclerview.self_optimize.OptimizeActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import org.greenrobot.eventbus.EventBus

class EventBusGatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_recycler_view)

        GuideSettings.set(
            findViewById(R.id.rv_content),
            arrayOf(
                GuideItemEntity("测试粘性消息SimpleEvent，先发送消息，再跳转Activity") {
                    EventBus.getDefault().postSticky(SimpleEvent())

                    startActivity(Intent(this, StickyTestActivity::class.java))
                },
                GuideItemEntity("------Begin 用于测试不删除粘性事件的效果------") {
                },
                GuideItemEntity("纯发送SimpleOtherEvent，") {
                    EventBus.getDefault().postSticky(SimpleOtherEvent())
                },
                GuideItemEntity("跳转Activity") {
                    startActivity(Intent(this, StickyTestActivity::class.java))
                },
                GuideItemEntity("------End 用于测试不删除粘性事件的效果------") {
                },

                GuideItemEntity("测试粘性消息TestStickyEvent，先发送消息，再跳转Activity。后面手动检查") {
                    EventBus.getDefault().postSticky(TestStickyEvent())

                    startActivity(Intent(this, StickyTestActivity::class.java))
                },
            )
        )
    }
}