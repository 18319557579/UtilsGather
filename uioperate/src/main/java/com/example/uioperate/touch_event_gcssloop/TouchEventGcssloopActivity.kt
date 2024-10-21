package com.example.uioperate.touch_event_gcssloop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.uioperate.touch_event_gcssloop.boss_call_1.Situation1Activity
import com.example.uioperate.touch_event_gcssloop.boss_call_2.Situation2Activity
import com.example.uioperate.touch_event_gcssloop.boss_call_3.Situation3Activity
import com.example.uioperate.touch_event_gcssloop.test_action_outside.ActionOutsideActivity
import com.example.uioperate.touch_event_gcssloop.test_gesture.TestGestureActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings

class TouchEventGcssloopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_event_gcssloop)

        GuideSettings.set(
            findViewById(R.id.lv_launcher),
            arrayOf(
                GuideItemEntity("点击 View1 区域但没有任何 View 消费事件") {
                    startActivity(Intent(this@TouchEventGcssloopActivity, Situation1Activity::class.java))
                },
                GuideItemEntity("点击 View1 区域且事件被 View1 消费") {
                    startActivity(Intent(this@TouchEventGcssloopActivity, Situation2Activity::class.java))
                },
                GuideItemEntity("点击 View1 区域但事件被 ViewGroupA 拦截") {
                    startActivity(Intent(this@TouchEventGcssloopActivity, Situation3Activity::class.java))
                },
                GuideItemEntity("测试手势事件") {
                    startActivity(Intent(this@TouchEventGcssloopActivity, TestGestureActivity::class.java))
                },
                GuideItemEntity("测试ACTION_OUTSIDE事件") {
                    startActivity(Intent(this@TouchEventGcssloopActivity, ActionOutsideActivity::class.java))
                },
            )
        )
    }
}