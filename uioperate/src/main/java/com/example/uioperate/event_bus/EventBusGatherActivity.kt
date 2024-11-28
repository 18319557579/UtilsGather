package com.example.uioperate.event_bus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.uioperate.easy_recyclerview.MyOneTypeActivity
import com.example.uioperate.easy_recyclerview.OneTypeActivity
import com.example.uioperate.easy_recyclerview.TwoTypeActivity
import com.example.uioperate.easy_recyclerview.ViewPager2Activity
import com.example.uioperate.easy_recyclerview.self_optimize.OptimizeActivity
import com.example.uioperate.fragment.communication_evnetbus.MessageEvent
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.concurrent.thread

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
                GuideItemEntity("纯发送SimpleOtherEvent") {
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

                GuideItemEntity("") {
                },

                GuideItemEntity("主线程发送 NormalStickyEvent") {
                    LogUtil.d("------------------------------------------------------------------------")
                    LogUtil.d("发送方 当前线程: ${Thread.currentThread()}")
                    EventBus.getDefault().post(NormalStickyEvent())
                },

                GuideItemEntity("子线程发送 NormalStickyEvent") {
                    LogUtil.d("------------------------------------------------------------------------")
                    thread {
                        LogUtil.d("发送方 当前线程: ${Thread.currentThread()}")
                        EventBus.getDefault().post(NormalStickyEvent())
                    }
                },

                GuideItemEntity("") {
                },
                GuideItemEntity("测试 POSTING 模式") {
                    startActivity(Intent(this, ModePostingActivity::class.java))
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

    // POSTING 是默认值
    // 同一线程中被调用，事件传递是同步完成的
    @Subscribe(threadMode = ThreadMode.POSTING)
    fun handleEvent(event: NormalStickyEvent) {
        LogUtil.d("POSTING 接收方 当前线程: ${Thread.currentThread()}")
    }

    //订阅者将在 Android 的主线程中被调用。如果发布线程是主线程，则将直接调用事件处理程序方法（同步调用）
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent_MAIN(event: NormalStickyEvent) {
        LogUtil.d("MAIN 接收方 当前线程: ${Thread.currentThread()}")
    }

    // 订阅者将在 Android 的主线程中被调用。这使事件处理具有更严格和更一致的顺序，事件始终被排队，前一个事件处理完，才会调用后一个事件处理程序
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun handleEvent_MAIN_ORDERED(event: NormalStickyEvent) {
        LogUtil.d("MAIN_ORDERED 接收方 当前线程: ${Thread.currentThread()}")
    }

    // 订阅者将在后台线程中被调用。如果发布线程不是主线程，事件处理程序方法将直接在发布线程中调用；如果发布线程是主线程，EventBus 将使用单个后台线程按顺序传递其所有事件。
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun handleEvent_BACKGROUND(event: NormalStickyEvent) {
        LogUtil.d("BACKGROUND 接收方 当前线程: ${Thread.currentThread()}")
    }

    // 事件处理程序方法在单独的线程中调用。这始终独立于发布线程和主线程。使用此模式，发布事件永远不会等待事件处理程序方法。
    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun handleEvent_ASYNC(event: NormalStickyEvent) {
        LogUtil.d("ASYNC 接收方 当前线程: ${Thread.currentThread()}")
    }

}