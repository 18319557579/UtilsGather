package com.example.uioperate.event_bus

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.uioperate.fragment.communication_evnetbus.MessageEvent
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class StickyTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_recycler_view)

        GuideSettings.set(
            findViewById(R.id.rv_content),
            arrayOf(
                GuideItemEntity("手动检查是否有 TestStickyEvent 事件") {
                    val stickyEvent = EventBus.getDefault().getStickyEvent(TestStickyEvent::class.java)
                    val hasEvent = stickyEvent != null
                    Toast.makeText(this, "是否有SimpleEvent粘性事件: $hasEvent", Toast.LENGTH_SHORT).show()
                },
                GuideItemEntity("手动检查是否有 TestStickyEvent 事件，并手动删除") {
                    val stickyEvent = EventBus.getDefault().getStickyEvent(TestStickyEvent::class.java)
                    val hasEvent = stickyEvent != null
                    Toast.makeText(this, "是否有SimpleEvent粘性事件: $hasEvent", Toast.LENGTH_SHORT).show()

                    // Better check that an event was actually posted before
                    if (stickyEvent != null) {
                        // "Consume" the sticky event
                        EventBus.getDefault().removeStickyEvent(stickyEvent)
                        // Now do something with it
                    }
                },
                GuideItemEntity("手动检查是否有 TestStickyEvent 事件，自动删除（有效防止忘记删除）") {
                    val stickyEvent = EventBus.getDefault().removeStickyEvent(TestStickyEvent::class.java)
                    val hasEvent = stickyEvent != null
                    Toast.makeText(this, "是否有SimpleEvent粘性事件: $hasEvent", Toast.LENGTH_SHORT).show()
                },
            )
        )
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(sticky = true)
    fun handleEvent(event: SimpleEvent) {
        val className = this.javaClass.simpleName
        val message = "Sticky Event #handleEvent: called for " + event.javaClass.simpleName
        Toast.makeText(this, className + message, Toast.LENGTH_SHORT).show()
        Log.d(className, message)

        // 因为粘性事件可能会继续传递下去
        // prevent event from re-delivering, like when leaving and coming back to app
        EventBus.getDefault().removeStickyEvent(event)
    }

    @Subscribe(sticky = true)
    fun handleEvent(event: SimpleOtherEvent) {
        val className = this.javaClass.simpleName
        val message = "#handleEvent: called for " + event.javaClass.simpleName
        Toast.makeText(this, className + message, Toast.LENGTH_SHORT).show()
        Log.d(className, message)
    }
}