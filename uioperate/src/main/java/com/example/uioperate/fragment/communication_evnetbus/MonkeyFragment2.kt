package com.example.uioperate.fragment.communication_evnetbus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.uioperate.R
import com.example.uioperate.fragment.communication.MonkeyCallFish
import com.example.utilsgather.lifecycle_callback.LifecycleLogFragment
import com.example.utilsgather.logcat.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MonkeyFragment2 : LifecycleLogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewCalled()
        return inflater.inflate(R.layout.fragment_monkey, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_call).setOnClickListener {
            EventBus.getDefault().post(MonkeyCall("我要喝水啦"))
            EventBus.getDefault().post(MonkeyCall("我要撒尿啦"))
        }
        view.findViewById<Button>(R.id.btn_call_fish).setOnClickListener {

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.type != MessageEvent.EventType.ZOO_TO_MONKEY)
            return
        LogUtil.d("动物园通知猴子表演了，详情: $event, 所在线程: ${Thread.currentThread()}")
    }
}