package com.example.uioperate.event_bus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.concurrent.thread

class TestCancelActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_recycler_view)

        GuideSettings.set(
            findViewById(R.id.rv_content),
            arrayOf(
                GuideItemEntity("主线程发送 NormalStickyEvent") {
                    LogUtil.d("------------------------------------------------------------------------")
                    LogUtil.d("发送方 当前线程: ${Thread.currentThread()}")
                    EventBus.getDefault().post(NormalStickyEvent())
                },

            )
        )
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1)
    fun handleEvent(event: NormalStickyEvent) {
        LogUtil.d("POSTING 1 接收方 当前线程: ${Thread.currentThread()}")
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    fun handleEvent2(event: NormalStickyEvent) {
        LogUtil.d("POSTING 2 接收方 当前线程: ${Thread.currentThread()}")
        LogUtil.d("将事件进行取消")

        // 取消只能在POSTING线程模式
        EventBus.getDefault().cancelEventDelivery(event)
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 3)
    fun handleEvent3(event: NormalStickyEvent) {
        LogUtil.d("POSTING 3 接收方 当前线程: ${Thread.currentThread()}")
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}