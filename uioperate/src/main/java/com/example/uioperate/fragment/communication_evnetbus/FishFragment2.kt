package com.example.uioperate.fragment.communication_evnetbus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.uioperate.R
import com.example.utilsgather.lifecycle_callback.LifecycleLogFragment
import com.example.utilsgather.logcat.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FishFragment2 : LifecycleLogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewCalled()
        return inflater.inflate(R.layout.fragment_fish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_call).setOnClickListener {
            EventBus.getDefault().post(FishCallZoo("我要吃东西"))
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onMessageEvent(event: MessageEvent) {
        if (event.type != MessageEvent.EventType.ZOO_TO_FISH)
            return
        LogUtil.d("动物园通知鱼表演了，详情: $event, 所在线程: ${Thread.currentThread()}")
    }

    //以下三个方法接收同样的事件类型，所以都会回调，以此可以更细粒度地控制线程、优先级
    @Subscribe(threadMode = ThreadMode.ASYNC, priority = 3)
    fun onMessageEvent(event: MonkeyCallFish) {
        LogUtil.d("通知了鱼1，详情: $event, 所在线程: ${Thread.currentThread()}")
    }
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    fun onMessageEvent2(event: MonkeyCallFish) {
        LogUtil.d("通知了鱼2，详情: $event, 所在线程: ${Thread.currentThread()}")
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND, priority = 3)
    fun onMessageEvent3(event: MonkeyCallFish) {
        LogUtil.d("通知了鱼3，详情: $event, 所在线程: ${Thread.currentThread()}")
    }
}