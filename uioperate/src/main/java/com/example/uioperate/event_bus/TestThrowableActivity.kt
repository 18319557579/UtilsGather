package com.example.uioperate.event_bus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.util.AsyncExecutor
import org.greenrobot.eventbus.util.ThrowableFailureEvent


class TestThrowableActivity: AppCompatActivity() {
    lateinit var mAsyncExecutor: AsyncExecutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_recycler_view)

        mAsyncExecutor = AsyncExecutor.create()

        GuideSettings.set(
            findViewById(R.id.rv_content),
            arrayOf(
                GuideItemEntity("主线程发送 NormalStickyEvent") {
                    mAsyncExecutor.execute {
                        EventBus.getDefault().postSticky(NormalStickyEvent())

                        var ttt: String? = null
                        ttt!!.length

                        EventBus.getDefault().postSticky(NormalStickyEvent())
                    }
                },
            )
        )
    }

    @Subscribe
    fun handleEvent(event: NormalStickyEvent) {
        LogUtil.d("接收器 当前线程: ${Thread.currentThread()}")
    }

    @Subscribe
    fun handleFailureEvent(event: ThrowableFailureEvent?) {
        // do something
        LogUtil.d("接收器 接收到了错误: ${event?.throwable}")
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