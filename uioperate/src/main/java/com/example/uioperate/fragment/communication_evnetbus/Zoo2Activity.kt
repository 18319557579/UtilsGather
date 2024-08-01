package com.example.uioperate.fragment.communication_evnetbus

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uioperate.R
import com.example.uioperate.fragment.communication.MonkeyWork
import com.example.utilsgather.logcat.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class Zoo2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoo2)

        findViewById<Button>(R.id.btn_start_acrobatics).setOnClickListener {
            EventBus.getDefault().post(MessageEvent(MessageEvent.EventType.ZOO_TO_MONKEY, "爬树"))
            EventBus.getDefault().post(MessageEvent(MessageEvent.EventType.ZOO_TO_MONKEY, "跳绳"))
            EventBus.getDefault().post(MessageEvent(MessageEvent.EventType.ZOO_TO_MONKEY, "钻洞"))

            EventBus.getDefault().post("跳跳跳")
        }

        findViewById<Button>(R.id.btn_start_aquashow).setOnClickListener {
            EventBus.getDefault().post(MessageEvent(MessageEvent.EventType.ZOO_TO_FISH, "顶球"))
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
        if (event.type != MessageEvent.EventType.ZOO_TO_ZOO)
            return
        LogUtil.d("动物园收到的通知，详情: $event, 所在线程: ${Thread.currentThread()}")
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(event: MonkeyCall) {
        LogUtil.d("收到猴子的通知，详情: $event, 所在线程: ${Thread.currentThread()}")
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onMessageEvent(event: FishCall) {
        LogUtil.d("收到鱼的通知，详情: $event, 所在线程: ${Thread.currentThread()}")
    }
}